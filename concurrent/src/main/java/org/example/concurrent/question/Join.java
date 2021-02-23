package org.example.concurrent.question;

import java.util.concurrent.TimeUnit;

/**
 * TopK
 *
 * @author duansg
 * @version 1.0
 * @date 2021/2/22 下午11:51
 */
public class Join {
    /*
    现在有 T1、T2、T3 三个线程，你怎样保证 T2 在 T1 执行完后执行，T3 在 T2 执行完后执行？
     */
    public static void main(String[] args) throws InterruptedException {
        Thread T1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1");
        });
        Thread T2 = new Thread(() -> {
            try {
                T1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T2");
        });
        Thread T3 = new Thread(() -> {
            try {
                T2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T3");
        });
        T1.start();T2.start();T3.start();
    }
}
