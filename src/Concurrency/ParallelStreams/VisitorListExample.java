package Concurrency.ParallelStreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

/*
Concurrent collections
- LinkedList, ArrayList, TreeSet and HashSet are not thread-safe
- They can be made thread safe with the synchronised wrapper which is useful for legacy code
- NOTE: It is recommended to use concurrent collections for new code

CopyOnWriteArrayList
- Whenever this list is modified by adding, updating or removing relements an new copy of the underlying array is created
- The modifications is performed on the new copy, allowing read operations to use the original unmodified array
- This ensures that reader threads aren't blocked by writers
- Since changes are made to a separate copy of an array there are not any synchronisation issues between the reading and writing threads
- This can be costly in some use casses but can be more efficient if the number of read/traversal operations vastly outnumber mutations

 */

public class VisitorListExample {

    private static final CopyOnWriteArrayList<Person> masterList;

    static {
        masterList = Stream.generate(Person::new)
                .distinct()
                .limit(2500)
                .collect(CopyOnWriteArrayList::new,
                        CopyOnWriteArrayList::add,
                        CopyOnWriteArrayList::addAll);
    }

    private static final ArrayBlockingQueue<Person> newVisitors =
            new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {

        Runnable producer = () -> {
            String threadName = Thread.currentThread().getName();
            Person visitor = new Person();
            System.out.println(threadName + "  Queueing  " + visitor);
            boolean queued = false;

            try {
                queued = newVisitors.offer(visitor, 5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception!");
            }
            if (queued) {
//                System.out.println(newVisitors);
            } else {
                System.out.println("Queue is Full, cannot add " + visitor);
                System.out.println("Draining Queue and writing data to file");
                List<Person> tempList = new ArrayList<>();
                newVisitors.drainTo(tempList);
                List<String> lines = new ArrayList<>();
                tempList.forEach((customer) -> lines.add(customer.toString()));
                lines.add(visitor.toString());

                try {
                    Files.write(Path.of("src/Concurrency/ParallelStreams/DrainedQueue.txt"), lines,
                            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable consumer = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " Polling queue " + newVisitors.size());
            Person visitor = null;
            try {
                visitor = newVisitors.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (visitor != null) {
                System.out.println(threadName + " " + visitor);
                if (!masterList.contains(visitor)) {
                    masterList.add(visitor);
                    System.out.println("--> New Visitor gets Coupon!: " + visitor);
                }
            }
            System.out.println(threadName + " done " + newVisitors.size());
        };

        ScheduledExecutorService producerExecutor =
                Executors.newSingleThreadScheduledExecutor();
        producerExecutor.scheduleWithFixedDelay(producer, 0, 3,
                TimeUnit.SECONDS);

        ScheduledExecutorService consumerPool =
                Executors.newScheduledThreadPool(3);
        for (int i = 0; i < 3; i++) {
            consumerPool.scheduleAtFixedRate(consumer, 6, 1,
                    TimeUnit.SECONDS);
        }

        while (true) {
            try {
                if (!producerExecutor.awaitTermination(20, TimeUnit.SECONDS))
                    break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        producerExecutor.shutdown();

        while (true) {
            try {
                if (!consumerPool.awaitTermination(3, TimeUnit.SECONDS))
                    break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        consumerPool.shutdown();

    }
}
