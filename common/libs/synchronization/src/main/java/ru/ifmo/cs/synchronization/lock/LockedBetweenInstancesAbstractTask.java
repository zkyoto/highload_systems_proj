package ru.ifmo.cs.synchronization.lock;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class LockedBetweenInstancesAbstractTask {
    private final HazelcastInstance hazelcastInstance;
    private final String lockSlug;

    public void executeWithLockBetweenInstances() {
        FencedLock lock = hazelcastInstance.getCPSubsystem().getLock(lockSlug);
        if (lock.tryLock()) {
            try {
                execute();
            } finally {
                lock.unlock();
            }
        }
    }

    abstract protected void execute();
}

