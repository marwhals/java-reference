package Concurrency;

/**
 * Interacting with a running thread
 *
 * Thread States on Thread.State
 * - New - Thread has noy yet started
 * - Runnable - A thread executing on the JVM
 * - Blocked - A thread blocked waiting for a monitor lock
 * - Waiting - A thread indefinitely waiting for another thread to perform an action
 * - Time_Waiting - A thread waiting for another thread for a specified time
 * - Terminated - A thread that has exited
 */

public class InteractingWithRunningThreads {

    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        System.out.println(name + ": main thread running");
        try {
            System.out.println("Main thread paused for one second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + ": should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". \n");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("\nWhoops!! " + tname + " interrupted.");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("\n" + tname + " completed.");
        });

        Thread installThread = new Thread(() -> {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + ": running install thread");
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(250);
                    System.out.println("Installation Step " + (i + 1) +
                            " is completed.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "InstallThread");

        Thread threadMonitor = new Thread(() -> {
            String tname = Thread.currentThread().getName();
            System.out.println(tname + ": running threadMonitor thread");
            long now = System.currentTimeMillis();

            while (thread.isAlive()) {
                try {
                    Thread.sleep(1000);

                    if (System.currentTimeMillis() - now > 8000) {// Alter value to cause interrupt
                        thread.interrupt();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(thread.getName() + " starting");
        thread.start();
        threadMonitor.start();

        try {
            thread.join();
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!thread.isInterrupted()) {
            installThread.start();
        } else {
            System.out.println("Previous thread was interrupted, " +
                    installThread.getName() + " can't run.");
        }
    }
}