package Concurrency;

import java.util.concurrent.TimeUnit;

/*
    Memory Consistency Errors
        - The OS may read from heap variables and make a copy of the value in each thread's own storage cache.
        - Each thread has its own small and fast memory storage (cache) that holds a copy of a shared resource's value.
        - Threads can modify a shared variable but this change may not be immediately reflected or visible to other threads
        --- It is first updated in the threads local cache first
        --- The OS may not flush the thread's changes to the heap until the thread has finished executing

    The 'volatile' keyword
    - Used as a modifier for class variables
    - An 'indicator' that this variable's value may be changed by multiple threads.
    - This modifier ensures that the variable is always read/written from main memory vs a threads own cache
    - This allows for memory consistency.

    When to use volatile
    - When a variable is used to track the state of a shared resource e.g a counter or a flag
    - When a variable is used to communicate between threads

    When not to use volatile
    - When a variable is used by a single thread
    - When a variable is used to store a large amount of data
 */

public class CachedData {

    private volatile boolean flag = false;

    public void toggleFlag() {
        flag = !flag;
    }

    public boolean isReady() {
        return flag;
    }

    public static void main(String[] args) {

        CachedData example = new CachedData();

        Thread writerThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            example.toggleFlag();
            System.out.println("A. Flag set to " + example.isReady());
        });

        Thread readerThread = new Thread(() -> {
            while (!example.isReady()) {
                // Busy-wait until flag becomes true
            }
            System.out.println("B. Flag is " + example.isReady());
        });

        writerThread.start();
        readerThread.start();
    }
}