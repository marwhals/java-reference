package Concurrency;


import Concurrency.Misc.ThreadColor;

import java.util.concurrent.TimeUnit;

/**
 * Notes:
 * - Each thread has its own stack for local variables and method calls - threads can't access each others stack
 * - Every thread has access to process memory a.k.a the heap. Objects and data reside here and is ****shared****
 * - Heap memory can be read and modified by all threads. Changes by one thread are visible to other threads
 *
 * Time Slicing
 * - Allows multiple threads or processes to share a single CPU when running
 * - CPU time is sliced and shared out to the threads
 * - Each thread gets a slice in order to make execution progress
 * - Thread management system doesn't care if the thread completes or not
 * - When time is up the thread must yield to another thread
 * - Issues can occur when threads are accessing the same data in the heap
 *
 * The java memory model JMM
 * - JMM is a spec defining rules and behaviours for threads to control shared access to data and operations
 * - Atomicity of Operations - few operations are atomic
 * - Synchronization - the process of controlling threads' access to shared resources
 *
 * Interference
 * - only the smallest blocks of code may be atomic i.e single time slice
 * - Thread can be part way through a task, when its time slice expires it will suspend/pause and other threads will start executing
 * - Interleaving - threads stopping and starting in the same blocks as other threads
 * -- When multiple threads run concurrently, their instructions can overlap or interleave in time
 * -- Thread execution happens in arbitary order
 * -- The order in which threads execute cannot be guaranteed
 *
 * Atmoic actions
 * - actions that happen all at once or not at all
 * - Side effects of an atomic action are never visible until the action completes
 *
 * NOTE:
 * - Even primitves are no guaranteed to be atomic e.g assignment for longs and doubles are not atmoic in all JVMs
 *
 * Thread-Safe definition
 * - Code is thread safe if it isn't affected by the execution of concurrent threads
 * ---- Atomic operations and immutable objects are example of thread-safe code
 */

public class MultithreadingAndMemory {

    /*
     * Shared state code is commented out. Uses a single Stopwatch instance
     */

    //    public static void main(String[] args) {
//        StopWatch stopWatch = new StopWatch(TimeUnit.SECONDS);
//        Thread green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());
//        Thread purple = new Thread(() -> stopWatch.countDown(7),
//                ThreadColor.ANSI_PURPLE.name());
//        Thread red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());
//        green.start();
//        purple.start();
//        red.start();
//    }
    public static void main(String[] args) {

        StopWatch greenWatch = new StopWatch(TimeUnit.SECONDS);
        StopWatch purpleWatch = new StopWatch(TimeUnit.SECONDS);
        StopWatch redWatch = new StopWatch(TimeUnit.SECONDS);
        Thread green = new Thread(greenWatch::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(() -> purpleWatch.countDown(7),
                ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(redWatch::countDown, ThreadColor.ANSI_RED.name());
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