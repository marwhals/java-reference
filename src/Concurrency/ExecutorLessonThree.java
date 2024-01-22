package Concurrency;

import Concurrency.Misc.ThreadColor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.concurrent.*;

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

   Advantages of using an executor service
   - The job of managing threads is simplified
   - Focus on tasks which need to be run and not thread management

   Creating threads is expensive
   - Creating threads, destroying threads and then creating them again can be expensive
   - A thread pool mitigates the cost, by keeping a set of threads around, in a pool for current and future work
   - Threads once they complete one task can then be reassgined to another task withouth the expense of destroying the thread and creating a new one

   Mechanics of a thread pool
   - A thread pool consists of three components
   -- Worker threads are available in a pool to execute tasks. They're pre created and kept alive through the lifetime of the application
   -- Submitted tasks are placed in a first-in first-out queue. Threads pop from the queue and execute them so they're executred in the order they're submitted
   -- The thread pool manager allocates tasks to threads and ensures proper thread pool synchronisation.

   Variations of the thread pool
   - FixedThreadPool - Has a fixed number of threads
   - CachedThreadPool - Creates new threads as needed so its a variable size pool
   - ScheduledThreadPool - Can schedule tasks to run at a specific time or repeatedly at regular intervals
   - WorkStealingPool - Uses a work stealing algorithm to distributre tasks among the threads in the pool
   - ForkJoinPool - Specialised WorkStealingPool for executing ForkJoinTasks

   Difference between Runnable and Callable --- submit method vs execute method
   - Callable returns a value
   - Submit can run a callable task in addition to a runnable task - also returns a future

   Java Futures
   - Represents a result of an async computation
   - Generic type, place holder for a result
   - Has methods which cancel a task, retrieve a result or check if a computation was completed or cancelled
   - Mthod returs the result, but you can only call this get method when the computation is complete otherwise the call will block until it does complete
   - THe overloaded version of the get method allows you to specify a wait time rather than blocking

 */

class ColorThreadFactoryThree implements ThreadFactory {

    private String threadName;

    private int colorValue = 1;

    public ColorThreadFactoryThree(ThreadColor color) {
        this.threadName = color.name();
    }

    public ColorThreadFactoryThree() {
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r);
        String name = threadName;
        if (name == null) {
            name = ThreadColor.values()[colorValue].name();
        }

        if (++colorValue > (ThreadColor.values().length - 1)) {
            colorValue = 1;
        }
        thread.setName(name);
        return thread;
    }
}

public class ExecutorLessonThree {

    public static void main(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> ExecutorLessonThree.sum(1, 10, 1, "red"),
                () -> ExecutorLessonThree.sum(10, 100, 10, "blue"),
                () -> ExecutorLessonThree.sum(2, 20, 2, "green")
        );
        try {
            var results = multiExecutor.invokeAny(taskList);
//            for (var result : results) {
            System.out.println(results);
//            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            multiExecutor.shutdown();
        }
    }

    public static void cachedmain(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        try {
            var redValue = multiExecutor.submit(
                    () -> ExecutorLessonThree.sum(1, 10, 1, "red"));
            var blueValue = multiExecutor.submit(
                    () -> ExecutorLessonThree.sum(10, 100, 10, "blue"));
            var greenValue = multiExecutor.submit(
                    () -> ExecutorLessonThree.sum(2, 20, 2, "green"));


            try {
                System.out.println(redValue.get(500, TimeUnit.SECONDS));
                System.out.println(blueValue.get(500, TimeUnit.SECONDS));
                System.out.println(greenValue.get(500, TimeUnit.SECONDS));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            multiExecutor.shutdown();
        }
    }

    public static void fixedmain(String[] args) {

        int count = 6;
        var multiExecutor = Executors.newFixedThreadPool(
                3, new ColorThreadFactoryThree()
        );

        for (int i = 0; i < count; i++) {
            multiExecutor.execute(ExecutorLessonThree::countDown);
        }
        multiExecutor.shutdown();
    }

    public static void singlemain(String[] args) {

        var blueExecutor = Executors.newSingleThreadExecutor(
                new ColorThreadFactory(ThreadColor.ANSI_BLUE)
        );
        blueExecutor.execute(ExecutorLessonThree::countDown);
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
            yellowExecutor.execute(ExecutorLessonThree::countDown);
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
                redExecutor.execute(ExecutorLessonThree::countDown);
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
                ExecutorLessonThree::countDown, ThreadColor.ANSI_BLUE.name());

        Thread yellow = new Thread(
                ExecutorLessonThree::countDown, ThreadColor.ANSI_YELLOW.name());

        Thread red = new Thread(
                ExecutorLessonThree::countDown, ThreadColor.ANSI_RED.name());

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

    private static int sum(int start, int end, int delta, String colorString) {

        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf("ANSI_" +
                    colorString.toUpperCase());
        } catch (IllegalArgumentException ignore) {
            // User may pass a bad color name, Will just ignore this error.
        }

        String color = threadColor.color();
        int sum = 0;
        for (int i = start; i <= end; i += delta) {
            sum += i;
        }
        System.out.println(color + Thread.currentThread().getName() + ", "
                + colorString + "  " + sum);
        return sum;
    }

}
