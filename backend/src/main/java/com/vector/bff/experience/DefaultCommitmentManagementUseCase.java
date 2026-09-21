package com.vector.bff.experience;

import com.vector.bff.canonical.Commitment;
import com.vector.bff.persistence.CanonicalRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DefaultCommitmentManagementUseCase implements CommitmentManagementUseCase {
    private final ExperienceProjectionSource source;
    private final CanonicalRepository repository;
    private final CommitmentLifecycleStore lifecycle;
    private final List<Commitment> created = new ArrayList<>();

    public DefaultCommitmentManagementUseCase(ExperienceProjectionSource source, CanonicalRepository repository) {
        this(source, repository, new SqliteCommitmentLifecycleStore("jdbc:sqlite::memory:"));
    }
    public DefaultCommitmentManagementUseCase(ExperienceProjectionSource source, CanonicalRepository repository, CommitmentLifecycleStore lifecycle) {
        this.source=Objects.requireNonNull(source); this.repository=Objects.requireNonNull(repository); this.lifecycle=Objects.requireNonNull(lifecycle);
    }

    @Override public CommitmentManagementProjection list(String areaDomainId,String serviceId,LocalDate asOf,int limit) {
        if(asOf==null) throw new IllegalArgumentException("asOf is required");
        var prepared=source.load(new ProjectionRequest(new AnalysisContext(null,areaDomainId,serviceId,null,null),limit));
        var values=new ArrayList<ManagementCommitmentProjection>();
        prepared.commitments().stream().filter(i->areaDomainId==null||areaDomainId.equals(areaFor(i,prepared)))
            .filter(i->serviceId==null||serviceId.equals(serviceFor(i,prepared)))
            .map(i->new ManagementCommitmentProjection(i.commitmentId(),i.declaration(),areaFor(i,prepared),null,null,i.statusContext(),false,i.riskFindingId(),serviceFor(i,prepared),null,"VECTOR-native or source provenance retained")).forEach(values::add);
        created.stream().filter(i->areaDomainId==null||areaDomainId.equals(i.accountableAreaDomainId()))
            .filter(i->serviceId==null||serviceId.equals(i.serviceId())).map(i->toProjection(i,asOf)).forEach(values::add);
        var bounded=values.size()<=limit?values:values.subList(0,Math.max(0,limit));
        long active=values.stream().filter(i->!"COMPLETED".equalsIgnoreCase(i.executionStatus())&&!"CANCELLED".equalsIgnoreCase(i.executionStatus())).count();
        long inProgress=values.stream().filter(i->"IN_PROGRESS".equalsIgnoreCase(i.executionStatus())).count();
        long completed=values.stream().filter(i->"COMPLETED".equalsIgnoreCase(i.executionStatus())).count();
        long overdue=values.stream().filter(ManagementCommitmentProjection::overdue).count();
        long renegotiated=created.stream().filter(i->lifecycle.history(i.metadata().canonicalId()).stream().anyMatch(e->"RENEGOTIATED".equals(e.eventType()))).count();
        long outcomePending=created.stream().filter(i->"COMPLETED".equalsIgnoreCase(i.executionStatus())).count();
        long denominator=created.stream().filter(i->i.dueDate()!=null&&!i.dueDate().isAfter(asOf)&&!"CANCELLED".equalsIgnoreCase(i.executionStatus())).count();
        long numerator=created.stream().filter(i->fulfilledByAgreedDate(i,asOf)).count();
        Double rate=denominator==0?null:(double)numerator/(double)denominator;
        return new CommitmentManagementProjection(asOf,areaDomainId,serviceId,List.copyOf(bounded),active,inProgress,completed,overdue,renegotiated,outcomePending,numerator,denominator,rate,prepared.quality());
    }

    @Override public ManagementCommitmentProjection create(CommitmentCreateRequest r) {
        require(r.commitmentId(),"commitmentId");require(r.declaration(),"declaration");require(r.accountableAreaDomainId(),"accountableAreaDomainId");
        var item=new Commitment(newLocalMetadata(r.commitmentId()),r.declaration(),r.accountableAreaDomainId(),r.responsibleParty(),r.dueDate(),r.executionStatus(),r.intendedResult(),r.serviceId(),r.configurationItemId(),r.riskFindingId());
        repository.save(item);created.add(item);
        lifecycle.append(new CommitmentLifecycleEvent(eventId(item.metadata().canonicalId(),"CREATED",Instant.now()),item.metadata().canonicalId(),"CREATED",null,item.executionStatus(),null,item.dueDate(),"Initial commitment",Instant.now(),true));
        return toProjection(item,LocalDate.now(ZoneOffset.UTC));
    }

    @Override public ManagementCommitmentProjection updateLifecycle(String id,CommitmentLifecycleUpdateRequest r) {
        require(r.executionStatus(),"executionStatus");require(r.reason(),"reason");var at=r.occurredAt()==null?Instant.now():r.occurredAt();
        var old=findCreated(id);var next=new Commitment(old.metadata(),old.declaration(),old.accountableAreaDomainId(),old.responsibleParty(),old.dueDate(),r.executionStatus(),old.intendedResult(),old.serviceId(),old.configurationItemId(),old.riskFindingId());
        replace(old,next);repository.save(next);lifecycle.append(new CommitmentLifecycleEvent(eventId(id,"STATUS",at),id,"STATUS_CHANGED",old.executionStatus(),next.executionStatus(),old.dueDate(),old.dueDate(),r.reason(),at,old.dueDate()==null||!at.atZone(ZoneOffset.UTC).toLocalDate().isAfter(old.dueDate())));
        return toProjection(next,at.atZone(ZoneOffset.UTC).toLocalDate());
    }

    @Override public ManagementCommitmentProjection renegotiate(String id,CommitmentRenegotiationRequest r) {
        if(r.newDueDate()==null) throw new IllegalArgumentException("newDueDate is required");require(r.reason(),"reason");var at=r.occurredAt()==null?Instant.now():r.occurredAt();
        var old=findCreated(id);if(old.dueDate()==null) throw new IllegalArgumentException("current dueDate is required for renegotiation");
        var day=at.atZone(ZoneOffset.UTC).toLocalDate();boolean before=!day.isAfter(old.dueDate());
        var next=new Commitment(old.metadata(),old.declaration(),old.accountableAreaDomainId(),old.responsibleParty(),r.newDueDate(),old.executionStatus(),old.intendedResult(),old.serviceId(),old.configurationItemId(),old.riskFindingId());
        replace(old,next);repository.save(next);lifecycle.append(new CommitmentLifecycleEvent(eventId(id,"RENEGOTIATED",at),id,"RENEGOTIATED",old.executionStatus(),old.executionStatus(),old.dueDate(),r.newDueDate(),r.reason(),at,before));
        return toProjection(next,day);
    }

    @Override public List<CommitmentLifecycleEvent> history(String id){return lifecycle.history(id);}

    private boolean fulfilledByAgreedDate(Commitment i,LocalDate asOf){
        if(i.dueDate()==null||i.dueDate().isAfter(asOf)||!"COMPLETED".equalsIgnoreCase(i.executionStatus())) return false;
        return lifecycle.history(i.metadata().canonicalId()).stream().filter(e->"STATUS_CHANGED".equals(e.eventType())&&"COMPLETED".equalsIgnoreCase(e.toStatus()))
            .map(e->e.occurredAt().atZone(ZoneOffset.UTC).toLocalDate()).anyMatch(d->!d.isAfter(i.dueDate()));
    }
    private Commitment findCreated(String id){return created.stream().filter(i->id.equals(i.metadata().canonicalId())).findFirst().orElseThrow(()->new IllegalArgumentException("local commitment not found"));}
    private void replace(Commitment old,Commitment next){created.set(created.indexOf(old),next);}
    private ManagementCommitmentProjection toProjection(Commitment i,LocalDate asOf){return new ManagementCommitmentProjection(i.metadata().canonicalId(),i.declaration(),i.accountableAreaDomainId(),i.responsibleParty(),i.dueDate(),i.executionStatus(),i.overdueOn(asOf),i.riskFindingId(),i.serviceId(),i.configurationItemId(),String.join(",",i.metadata().provenance().sourceReferenceIds()));}
    private static String areaFor(CommitmentProjection i,PreparedExperienceContext p){return p.areas().isEmpty()?null:p.areas().get(0).areaDomainId();}
    private static String serviceFor(CommitmentProjection i,PreparedExperienceContext p){return p.riskFindings().stream().filter(r->i.riskFindingId().equals(r.riskFindingId())).map(RiskFindingProjection::serviceId).findFirst().orElse(null);}
    private static void require(String v,String n){if(v==null||v.isBlank())throw new IllegalArgumentException(n+" is required");}
    private static String eventId(String id,String type,Instant at){return id+":"+type+":"+at.toEpochMilli();}
    private static com.vector.bff.canonical.CanonicalMetadata newLocalMetadata(String id){return new com.vector.bff.canonical.CanonicalMetadata(id,com.vector.bff.canonical.IdentityResolutionState.CONFIRMED,com.vector.bff.canonical.TemporalSemantics.empty(),com.vector.bff.canonical.Provenance.nativeOrUnspecified(),new com.vector.bff.canonical.SourceAuthority("VECTOR-native",true));}
}
