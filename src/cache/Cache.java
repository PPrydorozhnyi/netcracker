package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author P.Pridorozhny
 */
public class Cache {


    private Map<Long, Cacheable> cacheMap;
    private static Cache instance;
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    private Cache() {
        cacheMap = new ConcurrentHashMap<>();
        readWriteLock = new ReentrantReadWriteLock(true);
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    public static Cache getCache() {
        if (instance == null)
            instance = new Cache();

        return instance;
    }

    public void put(Long key, Cacheable object) {
        writeLock.lock();

        cacheMap.put(key, object);

        writeLock.unlock();
    }

    public Cacheable get(Long key) {

        readLock.lock();

        Cacheable obj = cacheMap.get(key);

        readLock.unlock();

        return obj;

    }


}
