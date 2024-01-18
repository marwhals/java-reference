package Concurrency;

import java.util.Random;

class MessageRepository {

    private String message;
    private boolean hasMessage = false;

    public synchronized String read() {

        while (!hasMessage) {

        }
        hasMessage = false;
        return message;
    }

    public synchronized void write(String message) {

        while (hasMessage) {

        }
        hasMessage = true;
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
