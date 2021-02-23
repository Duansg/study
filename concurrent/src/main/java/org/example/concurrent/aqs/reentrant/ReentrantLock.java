package org.example.concurrent.aqs.reentrant;

import org.example.concurrent.aqs.AbstractAqs;

import java.util.concurrent.CountDownLatch;

/**
 * ReentrantLock
 *
 * @author duansg
 * @version 1.0
 * @date 2021/2/23 下午5:02
 */
public class ReentrantLock extends AbstractAqs {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                testLock();
            },"thread" + i);
            thread1.start();
        }
        for (int i = 0; i < 10; i++) {
            countDownLatch.countDown();
        }
        countDownLatch.await();
    }

    private static void testLock() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boolean unlock = lock.unlock();
            System.out.println(System.currentTimeMillis() +"："+Thread.currentThread().getName() + ":unlock:" + unlock);
        }
    }

    public boolean lock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        if (super.cas(0, 1, thread)){
            long start = System.currentTimeMillis();
            System.out.println(start +"："+Thread.currentThread().getName() + ":lock:" + true);
            super.setThread(thread);
        }else {
            super.setWaitQueue(thread);
        }
        return true;
    }

    public boolean unlock(){
        Thread thread = Thread.currentThread();
        if (super.dcas(thread)){

        }
        return true;
    }

}
