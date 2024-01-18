package Concurrency;

import static Concurrency.ThreadColor.*;

public class Threads {
    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE.color()+"Hello from the main thread.");

        Thread anotherThread = new AnotherThread();
        anotherThread.start();

        new Thread() {
            public void run() {
                System.out.println(ANSI_GREEN.color() + "Hello from the anonymous class thread");
            }
        }.start();

        System.out.println(ANSI_PURPLE.color()+"Hello again from the main thread.");

    }
}
