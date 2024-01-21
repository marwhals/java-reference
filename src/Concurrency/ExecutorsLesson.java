package Concurrency;

import Concurrency.Misc.ThreadColor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

/* Executor service
    - These are the ExecutorService classes and they exist to manage the creation and execution of threads
    - When using a Thread class, you have basic control over that thread.
        - You can interrupt a thread and join it to another thread.
        - You can name the thread, try to prioritize it and join it to another thread.
        - You can name the thread, try to prioritize it and start each manually one at a time.
        - You can also pass it an UncaughtExceptionHandler to deal with exceptions that happen in a thread.
    Managing individual threads
    - Managing threads manually can be complex and error-prone
    - It can lead to complex issues like resource contention, thread creation overhead and scalability challenges.
    - For these reasons you'll want to use an ExecutorService even when working with a single thread.

    Benefits of Managing threads with an implementation of ExecutorService
    - Simplify thread management by abstracting execution to the level of tasks which need to run.
    - Use Thread Pools reducing the cost of creating new threads.
    - Efficient Scaling by utilizing multiple processor cores.
    - Built-in synchronization, reducing concurrency-related errors.
    - Graceful shutdown preventing resource leaks
    - Scheduled implementations exist to further help with management workflows.
 */

class ColorThreadFactory implements ThreadFactory {

    private String threadName;

    public ColorThreadFactory(ThreadColor color) {
        this.threadName = "Thread " + color.name();
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}

public class ExecutorsLesson {
    public static void main(String[] args) {

        var blueExecutor = Executors.newSingleThreadExecutor(
                new ColorThreadFactory(ThreadColor.ANSI_BLUE)
        );
        blueExecutor.execute(ExecutorsLesson::countDown);
        blueExecutor.shutdown();

        boolean isDone = false;

        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (isDone) {
            System.out.println("Blue finished, starting Yellow");
            var yellowExecutor = Executors.newSingleThreadExecutor(
                    new ColorThreadFactory(ThreadColor.ANSI_YELLOW)
            );
            yellowExecutor.execute(ExecutorsLesson::countDown);
            yellowExecutor.shutdown();

            try {
                isDone = yellowExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isDone) {
                System.out.println("Yellow finished, starting Red");
                var redExecutor = Executors.newSingleThreadExecutor(
                        new ColorThreadFactory(ThreadColor.ANSI_RED)
                );
                redExecutor.execute(ExecutorsLesson::countDown);
                redExecutor.shutdown();
                try {
                    isDone = redExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (isDone) {
                    System.out.println("All processes completed");
                }
            }
        }
    }

    public static void notmain(String[] args) {

        Thread blue = new Thread(
                ExecutorsLesson::countDown, ThreadColor.ANSI_BLUE.name());

        Thread yellow = new Thread(
                ExecutorsLesson::countDown, ThreadColor.ANSI_YELLOW.name());

        Thread red = new Thread(
                ExecutorsLesson::countDown, ThreadColor.ANSI_RED.name());

        blue.start();

        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        yellow.start();

        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        red.start();

        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void countDown() {
        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName.toUpperCase());
        } catch (IllegalArgumentException ignore) {
            // User may pass a bad color name, Will just ignore this error.
        }

        String color = threadColor.color();
        for (int i = 20; i >= 0; i--) {
            System.out.println(color + " " +
                    threadName.replace("ANSI_", "") + "  " + i);
        }
    }
}
