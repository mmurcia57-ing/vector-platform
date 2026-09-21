package com.vector.bff.experience;

import java.util.List;

public interface CommitmentLifecycleStore extends AutoCloseable {
    void append(CommitmentLifecycleEvent event);
    List<CommitmentLifecycleEvent> history(String commitmentId);
    default void close() { }
}
