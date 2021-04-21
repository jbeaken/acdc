package com.mzuri.playground.acdc.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock rLock = new ReentrantLock();
        Thread t1 = new Thread(new Counter("Thread-1", rLock));
        Thread t2 = new Thread(new Counter("Thread-2", rLock));
        System.out.println("starting threads ");
        t1.start();
        t2.start();
    }
}

// Shared class for threads
class SharedResource{
    static int count = 0;
}

class Counter implements Runnable {

    private String threadName;

    ReentrantLock lock;

    Counter(String threadName, ReentrantLock lock){
        this.threadName = threadName;
        this.lock = lock;
    }
    
    @Override
    public void run() {
        System.out.println("In Counter run method, thread " + threadName + " is waiting to get lock");

        // acquiring the lock
        lock.lock();

        try {
            System.out.println("Thread " + threadName + " has got lock");

            SharedResource.count++;

            System.out.println("Thread " + threadName + " Count " + SharedResource.count);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally{
            System.out.println("Thread " + threadName  + " releasing lock");
            // releasing the lock
            lock.unlock();
        }
    }
}