package com.vector.bff.experience;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class SqliteCommitmentLifecycleStore implements CommitmentLifecycleStore {
    private final java.sql.Connection connection;
    public SqliteCommitmentLifecycleStore(String jdbcUrl) {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            try (var s = connection.createStatement()) {
                s.executeUpdate("create table if not exists commitment_lifecycle(event_id text primary key, commitment_id text not null, event_type text not null, from_status text, to_status text, prior_due_date text, new_due_date text, reason text, occurred_at text not null, before_due_date integer not null)");
            }
        } catch (SQLException e) { throw new IllegalStateException("cannot initialize commitment lifecycle", e); }
    }
    public synchronized void append(CommitmentLifecycleEvent e) {
        try (var s=connection.prepareStatement("insert into commitment_lifecycle values(?,?,?,?,?,?,?,?,?,?)")) {
            s.setString(1,e.eventId());s.setString(2,e.commitmentId());s.setString(3,e.eventType());s.setString(4,e.fromStatus());s.setString(5,e.toStatus());
            s.setString(6,e.priorDueDate()==null?null:e.priorDueDate().toString());s.setString(7,e.newDueDate()==null?null:e.newDueDate().toString());
            s.setString(8,e.reason());s.setString(9,e.occurredAt().toString());s.setBoolean(10,e.beforeDueDate());s.executeUpdate();
        } catch(SQLException x){throw new IllegalStateException("cannot append commitment lifecycle",x);}
    }
    public synchronized List<CommitmentLifecycleEvent> history(String id) {
        var out=new ArrayList<CommitmentLifecycleEvent>();
        try(var s=connection.prepareStatement("select * from commitment_lifecycle where commitment_id=? order by occurred_at,event_id")){
            s.setString(1,id);try(var r=s.executeQuery()){while(r.next())out.add(new CommitmentLifecycleEvent(r.getString("event_id"),id,r.getString("event_type"),r.getString("from_status"),r.getString("to_status"),date(r.getString("prior_due_date")),date(r.getString("new_due_date")),r.getString("reason"),Instant.parse(r.getString("occurred_at")),r.getBoolean("before_due_date")));}return List.copyOf(out);
        }catch(SQLException x){throw new IllegalStateException("cannot read commitment lifecycle",x);}
    }
    private static LocalDate date(String v){return v==null?null:LocalDate.parse(v);}
    public void close(){try{connection.close();}catch(SQLException e){throw new IllegalStateException("cannot close commitment lifecycle",e);}}
}
