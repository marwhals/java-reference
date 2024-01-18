package Concurrency;


import java.util.concurrent.TimeUnit;

/**
 * Notes:
 * - Each thread has its own stack for local variables and method calls - threads can't access each others stack
 * - Every thread has access to process memory a.k.a the heap. Objects and data reside here and is ****shared****
 * - Heap memory can be read and modified by all threads. Changes by one thread are visible to other threads
 *
 *  Time Slicing
 *  - Allows multiple threads or processes to share a single CPU when running
 *  - CPU time is sliced and shared out to the threads
 *  - Each thread gets a slice in order to make execution progress
 *  - Thread management system doesn't care if the thread completes or not
 *  - When time is up the thread must yield to another thread
 *  - Issues can occur when threads are accessing the same data in the heap
 *
 *  The java memeory mondel JMM
 *  - JMM is a spec defining rules and behaviours for threads to control shared access to data and operations
 *  - Atomicity of Operations - few operations are atomic
 *  - Synchronization - the process of controlling threads' access to shared resources
 */

public class MultithreadingAndMemory {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch(TimeUnit.SECONDS);
        Thread green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(() -> stopWatch.countDown(7),
                ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());
        green.start();
        purple.start();
        red.start();
    }
}

class StopWatch {

    private TimeUnit timeUnit;
    private int i;

    public StopWatch(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void countDown() {
        countDown(5);
    }

    public void countDown(int unitCount) {

        String threadName = Thread.currentThread().getName();

        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException ignore) {
            // User may pass a bad color name, Will just ignore this error.
        }
        String color = threadColor.color();
        for (i = unitCount; i > 0; i--) { // shared state in the for loop
            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }
    }
}