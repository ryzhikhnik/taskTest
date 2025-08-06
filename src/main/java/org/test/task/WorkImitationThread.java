package org.test.task;

import org.test.task.model.ThreadInfo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static org.test.task.utils.RandomUtil.getRandomSleepTime;
import static org.test.task.utils.RandomUtil.getTwoRandomLockIndex;

public class WorkImitationThread implements Runnable {
    private final ThreadInfo threadInfo;
    private final List<Lock> locks;

    public WorkImitationThread(ThreadInfo threadInfo, List<Lock> locks) {
        this.threadInfo = threadInfo;
        this.locks = locks;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " started work");

            long start = System.currentTimeMillis();
            try {
                int[] twoRandomLockIndex = getTwoRandomLockIndex(locks.size());
                System.out.println(Thread.currentThread().getName() + " take locks with index " + Arrays.toString(twoRandomLockIndex));

                Lock lock1 = locks.get(twoRandomLockIndex[0]);
                Lock lock2 = locks.get(twoRandomLockIndex[1]);

                if (tryLock(lock1, lock2)) {
                    long sleepTime = getRandomSleepTime(threadInfo.getIntervalFrom(), threadInfo.getIntervalTo());
                    System.out.println(Thread.currentThread().getName() + " will sleep for " + sleepTime / 1000 + " sec");
                    Thread.sleep(sleepTime);

                    unlock(lock1, lock2);
                } else {
                    System.out.println(Thread.currentThread().getName() + " didn't take objects lock: one of they is busy");
                }

                long startDelay = getStartDelay(start);
                System.out.println(Thread.currentThread().getName() + " will wait " + startDelay / 1000d + " for next iteration");

                Thread.sleep(startDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private long getStartDelay(long start) {
        long millisLeft = threadInfo.getPeriodMillis() - (System.currentTimeMillis() - start);
        return millisLeft > 0 ? millisLeft : 0;
    }

    private boolean tryLock(Lock lock1, Lock lock2) {
        if (lock1.tryLock()) {
            if (lock2.tryLock()) {
                return true;
            } else {
                lock1.unlock();
            }
        }
        return false;
    }

    private void unlock(Lock lock1, Lock lock2) {
        lock1.unlock();
        lock2.unlock();
    }
}
