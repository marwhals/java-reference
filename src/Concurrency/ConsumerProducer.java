package Concurrency;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
- Deadlock example - one task can't make progress because another task will not release the lock on a shared resource

The wait, notify and notifyAll methods
- Belongs to the object class and are used to monitor lock situations to prevent threads blocking indefinitely
- Because they are on object, any instance of any class can execute these methods from within a synchrnoised method/ statement

Explicit locks
- The hold count of a lock counts the number of times that a single thread, the owner of the lock, has acquired the lock
-- When a thread acquires a lock for the first time, the locks hold count is set to one
-- If a lock is re-entrant, and a thread, reacquires the same lock, the lock's hold count will get incremented
-- When a thread releases a lock, the lock's hold count is decremented
-- A lock is only released when it's hold count becomes zero
NOTE: include a call to the unlock method in a finally clause of any code that will acquire a lock

Advantages of explicit locks - Lock implementations
- Explicit control over when to acquire and release locks, making it easier to avoid deadlocks and manage concurrency issues
- Timeouts allow you to acquire a lock without blocking indefinitely
- Along with timeouts, Interruptible Locking lets you handle interruptions during acquisition better
- Improved debugging methods let you query the number of waiting threads and check if a thread holds a lock

 */
class MessageRepository {

    private String message;
    private boolean hasMessage = false;
    private final Lock lock = new ReentrantLock();

    public String read() {

        if (lock.tryLock()) {
            try {
                while (!hasMessage) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                hasMessage = false;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("** read blocked" + lock);
            hasMessage = false;
        }
        return message;
    }

    public void write(String message) {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    while (hasMessage) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    hasMessage = true;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("** write blocked " + lock);
                hasMessage = true;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.message = message;
    }
}

class MessageWriter implements Runnable {

    private MessageRepository outgoingMessage;

    private final String text = """
            Line 1 of text,
            Line 2 of text,
            Line 3 of text,
            Line 4 of text""";

    public MessageWriter(MessageRepository outgoingMessage) {
        this.outgoingMessage = outgoingMessage;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Starting Message Writer code.........");

        Random random = new Random();
        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            outgoingMessage.write(lines[i]);
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        outgoingMessage.write("Finished");
    }
}

class MessageReader implements Runnable {

    private MessageRepository incomingMessage;

    public MessageReader(MessageRepository incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Starting Message Reader code.........");
        Random random = new Random();
        String latestMessage = "";

        do {
            try {
                Thread.sleep(random.nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latestMessage = incomingMessage.read();
            System.out.println(latestMessage);
        } while (!latestMessage.equals("Finished"));
    }
}

// This code will deadlock unless using locks correctly or synchornized blocks/statemetn
public class ConsumerProducer {
    public static void main(String[] args) {
        System.out.println("Starting deadlocking code.........");

        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository), "Reader");
        Thread writer = new Thread(new MessageWriter(messageRepository), "Writer");

        writer.setUncaughtExceptionHandler((thread, exc) -> {
            System.out.println("Writer had an exception: " + exc);
            if (reader.isAlive()) {
                System.out.println("Going to interrupt the reader");
                reader.interrupt();
            }
        });

        reader.setUncaughtExceptionHandler((thread, exc) -> {
            System.out.println("Reader had an exception: " + exc);
            if (writer.isAlive()) {
                System.out.println("Going to interrupt the writer");
                writer.interrupt();
            }
        });

        reader.start();
        writer.start();
    }
}
