package com.laoxin.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

@Slf4j
@ConditionalOnClass(RedissonClient.class)
@Component
public class RedissionLockUtil {

    private static ThreadLocal<Stack<RLock>> currentThreadLockStacks = new ThreadLocal<Stack<RLock>>(){
        protected synchronized Stack<RLock> initialValue() {
            return new Stack();
        }
    };

    private static final int MAX_LOCK_SIZE = 1000;

    private static RedissonClient redissonClient;

    @Autowired
    public RedissionLockUtil(RedissonClient redissonClient ){
        RedissionLockUtil.redissonClient = redissonClient;
    }


    /***
     * 业务锁定解锁要遵循栈的先进后出原则
     * @author jinquanbao
     */
    public static boolean lock(String lockKey,long waitSeconds,long leaseSeconds){
        boolean result = false;
        Stack<RLock> lockStack = currentThreadLockStacks.get();
        //避免无限递归造成内存溢出
        if(lockStack.size() > MAX_LOCK_SIZE){
            log.warn("lockStack size over max limit size, lockKey={},currentThreadId ={} ",lockKey,Thread.currentThread().getId());
            return result;
        }
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if(lock.tryLock(waitSeconds,leaseSeconds, TimeUnit.SECONDS)){
                lockStack.push(lock);
                currentThreadLockStacks.set(lockStack);
                result = true;
            }
        } catch (InterruptedException e) {
            log.error("Redission tryLock InterruptedException {}",e.getMessage());
            Thread.currentThread().interrupt();
        }
        return result;
    }

    /***
     * 解锁
     * @author jinquanbao
     */
    public static void unLock(String lockKey){
        Stack<RLock> lockStack = currentThreadLockStacks.get();
        if(CollectionUtils.isEmpty(lockStack)){
            return;
        }
        RLock lock = lockStack.pop();
        if(lock == null){
            log.warn("lockStack pop lock is null, lockKey = {}, currentThreadId ={} ",lockKey,Thread.currentThread().getId());
            return;
        }
        if(!lock.isHeldByCurrentThread()){
            log.warn("lock is not HeldByCurrentThread, lockKey={}, currentThreadId ={} ",lockKey,Thread.currentThread().getId());
            return;
        }
        if(!lock.isLocked()){
            log.warn("lock is unlocked, lockKey={},currentThreadId ={} ",lockKey,Thread.currentThread().getId());
            return;
        }
        try {
            //释放锁
            lock.unlock();
            log.debug("lock release success, lockKey={},currentThreadId ={} ",lockKey,Thread.currentThread().getId());
        }catch (Exception e){
            log.error("lock release Exception :",e);
        }
        if (lockStack.isEmpty()) {
            //清除本地线程缓存
            currentThreadLockStacks.remove();
        }
    }


}
