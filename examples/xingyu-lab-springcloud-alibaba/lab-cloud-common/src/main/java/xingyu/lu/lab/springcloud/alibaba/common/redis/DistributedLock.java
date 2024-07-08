package xingyu.lu.lab.springcloud.alibaba.common.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Redisson lock
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-06-17  14:56
 */
@Component
public class DistributedLock {

    /**
     * @description: 上锁后自动释放锁时间 DEFAULT_LEASE_TIME 毫秒
     **/
    public static final int DEFAULT_LEASE_TIME = 800;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取锁
     *
     * @param lockKey 锁实例key
     * @return 锁信息
     */
    public RLock getRLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

//    /**
//     * 加锁
//     * 不写释放时间以启用watchdog
//     *
//     * @param lockKey 锁实例key
//     * @return 锁信息
//     */
//    public Boolean tryLock(String lockKey) {
//        RLock rLock = getRLock(lockKey);
//        boolean tryLock = false;
//        tryLock = rLock.tryLock();
//        return tryLock;
//    }

    /**
     * 加锁
     *
     * @param lockKey   锁实例key
     * @param leaseTime 上锁后自动释放锁时间
     * @return true=成功；false=失败
     */
    public Boolean tryLock(String lockKey, long leaseTime) {
        return tryLock(lockKey, 0, leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     *
     * @param lockKey   锁实例key
     * @param leaseTime 上锁后自动释放锁时间
     * @param unit      时间颗粒度
     * @return true=加锁成功；false=加锁失败
     */
    public Boolean tryLock(String lockKey, long leaseTime, TimeUnit unit) {
        return tryLock(lockKey, 0, leaseTime, unit);
    }

    /**
     * 加锁
     *
     * @param lockKey   锁实例key
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @param unit      时间颗粒度
     * @return true=加锁成功；false=加锁失败
     */
    public Boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock rLock = getRLock(lockKey);
        boolean tryLock = false;
        try {
            tryLock = rLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return tryLock;
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁实例key
     */
    public void unlock(String lockKey) {
        RLock lock = getRLock(lockKey);
        lock.unlock();
    }

    /**
     * 释放锁
     *
     * @param lock 锁信息
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
