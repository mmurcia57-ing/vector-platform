package com.vector.bff.graph;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Durable local graph read-model adapter. This is projection storage only:
 * canonical persistence and Source Authority remain outside this store.
 */
public final class SqliteGraphProjectionStore implements GraphProjectionStore, AutoCloseable {
    private static final String SEP = "\u001f";
    private final java.sql.Connection connection;

    public SqliteGraphProjectionStore(String jdbcUrl) {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            try (var statement = connection.createStatement()) {
                statement.executeUpdate("create table if not exists graph_event(event_id text primary key)");
                statement.executeUpdate("create table if not exists graph_node(type text not null,id text not null,primary key(type,id))");
                statement.executeUpdate("create table if not exists graph_relationship(source_type text,source_id text,predicate text,target_type text,target_id text,evidence text,sources text,primary key(source_type,source_id,predicate,target_type,target_id))");
            }
        } catch (SQLException error) { throw new IllegalStateException("cannot initialize graph projection", error); }
    }

    @Override public synchronized void apply(GraphProjectionEvent event) {
        try {
            connection.setAutoCommit(false);
            try (var seen = connection.prepareStatement("insert or ignore into graph_event(event_id) values(?)")) {
                seen.setString(1,event.eventId());
                if (seen.executeUpdate() == 0) { connection.rollback(); connection.setAutoCommit(true); return; }
            }
            try (var node = connection.prepareStatement("insert or ignore into graph_node(type,id) values(?,?)")) {
                for (var value : event.nodes()) { node.setString(1,value.canonicalType()); node.setString(2,value.canonicalId()); node.addBatch(); }
                node.executeBatch();
            }
            try (var rel = connection.prepareStatement("insert or replace into graph_relationship(source_type,source_id,predicate,target_type,target_id,evidence,sources) values(?,?,?,?,?,?,?)")) {
                for (var value : event.relationships()) {
                    rel.setString(1,value.source().canonicalType()); rel.setString(2,value.source().canonicalId()); rel.setString(3,value.predicate());
                    rel.setString(4,value.target().canonicalType()); rel.setString(5,value.target().canonicalId());
                    rel.setString(6,String.join(SEP,value.evidenceIds())); rel.setString(7,String.join(SEP,value.sourceReferenceIds())); rel.addBatch();
                }
                rel.executeBatch();
            }
            connection.commit(); connection.setAutoCommit(true);
        } catch (SQLException error) { try { connection.rollback(); connection.setAutoCommit(true); } catch (SQLException ignored) { } throw new IllegalStateException("graph projection failed",error); }
    }

    @Override public synchronized Set<GraphNode> nodes() {
        var values=new LinkedHashSet<GraphNode>();
        try(var statement=connection.createStatement();var rows=statement.executeQuery("select type,id from graph_node order by type,id")){
            while(rows.next()) values.add(new GraphNode(rows.getString(1),rows.getString(2)));
            return Set.copyOf(values);
        } catch(SQLException error){throw new IllegalStateException("graph node read failed",error);}
    }

    @Override public synchronized Set<GraphRelationship> relationships() {
        var values=new LinkedHashSet<GraphRelationship>();
        try(var statement=connection.createStatement();var rows=statement.executeQuery("select source_type,source_id,predicate,target_type,target_id,evidence,sources from graph_relationship")){
            while(rows.next()) values.add(new GraphRelationship(new GraphNode(rows.getString(1),rows.getString(2)),rows.getString(3),
                new GraphNode(rows.getString(4),rows.getString(5)),split(rows.getString(6)),split(rows.getString(7))));
            return Set.copyOf(values);
        } catch(SQLException error){throw new IllegalStateException("graph relationship read failed",error);}
    }

    private static List<String> split(String value){return value==null||value.isEmpty()?List.of():List.of(value.split(SEP,-1));}
    @Override public void close(){try{connection.close();}catch(SQLException error){throw new IllegalStateException("graph close failed",error);}}
}
