package ru.ifmo.cs.synchronization.lock;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class LockedBetweenInstancesAbstractTask {
    private final HazelcastInstance hazelcastInstance;
    private final String lockSlug;

    public void executeWithLockBetweenInstances() {
        IMap<Object, Object> locksMap = hazelcastInstance.getMap("LockedBetweenInstancesTasks");
        if (locksMap.tryLock(lockSlug)) {
            try {
                execute();
            } finally {
                locksMap.unlock(lockSlug);
            }
        }
    }

    abstract protected void execute();
}

