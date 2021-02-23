package org.example.concurrent.aqs;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Aqs
 *
 * @author duansg
 * @version 1.0
 * @date 2021/2/23 下午4:07
 */
public abstract class AbstractAqs {

    private static final AtomicInteger state = new AtomicInteger(0);

    private static Thread lockThread = null;

    private static List<Thread> queue = new LinkedList<>();

    protected boolean cas(int expect, int update,Thread currentThread){
        if (null != lockThread && currentThread == lockThread){
            state.incrementAndGet();
            return true;
        }
        return state.compareAndSet(expect,update);
    }

    protected void setThread(Thread currentThread){
        this.lockThread = currentThread;
    }

    protected boolean setWaitQueue(Thread currentThread) throws InterruptedException {
        queue.add(currentThread);
        while (true){
            if (null == lockThread && state.get() == 0){
                Thread thread = queue.get(0);
                if (null != thread && thread == currentThread){
                    if (cas(0,1, currentThread)){
                        setThread(currentThread);
                        queue.remove(0);
                        return true;
                    }
                }
            }
        }
    }

    protected boolean dcas(Thread currentThread) {
        if (null!= lockThread && lockThread == currentThread){
            state.decrementAndGet();
            if (state.get() == 0){
                lockThread = null;
            }
        }
        return false;
    }
}
