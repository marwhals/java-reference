package Concurrency;

import java.util.Random;
/*
- Deadlock example - one task can't make progress because another task will not release the lock on a shared resource

The wait, notify and notifyAll methods
- Belongs to the object class and are used to monitor lock situations to prevent threads blocking indefinitely
- Because they are on object, any instance of any class can execute these methods from within a synchrnoised method/ statement

 */
class MessageRepository {

    private String message;
    private boolean hasMessage = false;

    public synchronized String read() {

        while (!hasMessage) {
            try {
                wait(); //deadlock fix -- thread will sleep until it recieves a notify or notifyAll from some other thread
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = false;
        notifyAll();// deadlock fix
        return message;
    }

    public synchronized void write(String message) {

        while (hasMessage) {
            try {
                wait();// deadlock fix
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        hasMessage = true;
        notifyAll(); //deadlock fix
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

// This code will deadlock
public class ConsumerProducer {
    public static void main(String[] args) {
        System.out.println("Starting deadlocking code.........");

        MessageRepository messageRepository = new MessageRepository();

        Thread reader = new Thread(new MessageReader(messageRepository));
        Thread writer = new Thread(new MessageWriter(messageRepository));

        reader.start();
        writer.start();
    }
}
