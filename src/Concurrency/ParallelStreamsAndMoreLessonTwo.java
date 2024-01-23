package Concurrency;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
- Why the HashMap isn't thread-safe
--- Lacks synchronisation
--- There are no guarantees or memory consistency while iterating

Concurrent Classes vs Synchronized Wrapper Classes
- Both are thread safe and can be used in parallel streams or in a multithreaded application
-- Synchronized Collections are implemented using locks which protect the collection from concurrent access.
    This means a single lock is used to synchronized access to the entire map

-- Concurrent collections are more efficient than synchronized collections, because they can use fine grained locking
    or non-blocking algorithms to enable safe concurrent access without locking the entire data strucutre.

Concurrent collections recommended over synchronized collections in most scenarios

 */

public class ParallelStreamsAndMoreLessonTwo {
    public static void main(String[] args) {

        var persons = Stream.generate(Concurrency.Misc.Person::new)
                .limit(10)
                .sorted(Comparator.comparing(Concurrency.Misc.Person::lastName))
                .toArray();

        for (var person : persons) {
            System.out.println(person);
        }
        System.out.println("-----------------------------");

        Arrays.stream(persons)
                .limit(10)
                .parallel()
//                .sorted(Comparator.comparing(Person::lastName))
                .forEach(System.out::println);

        System.out.println("-----------------------------");

        int sum = IntStream.range(1, 101)
                .parallel()
                .reduce(0, Integer::sum);

        System.out.println("The sum of the numbers is: " + sum);

        String someText = """
                Line one of some text.
                Second line of some more text.
                The third line of some text.
                The final line of the text.
                """;

        System.out.println("-----------------------------");
        var words = new Scanner(someText).tokens().toList();
        words.forEach(System.out::println);
        System.out.println("-----------------------------");

        var joinWords = words
                .parallelStream()
                .collect(Collectors.joining(" "));

        System.out.println(joinWords);

        Map<String, Long> lastNameCounts =
                Stream.generate(Concurrency.Misc.Person::new)
                        .limit(10000)
                        .parallel() // usage requires thought
                        .collect(Collectors.groupingByConcurrent(//more efficient collectors method for parallel streams
                                Concurrency.Misc.Person::lastName,
                                Collectors.counting()
                        ));

        lastNameCounts.entrySet().forEach(System.out::println);

        long total = 0;
        for (long count : lastNameCounts.values()) {
            total += count;
        }
        System.out.println("Total = " + total);

        System.out.println(lastNameCounts.getClass().getName());

        System.out.println("-----------------------------");

        var lastCounts = Collections.synchronizedMap( //Blocking type
                new TreeMap<String, Long>());
        Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .forEach((person) -> lastCounts.merge(person.lastName(),
                        1L, Long::sum));

        System.out.println(lastCounts);

        total = 0;
        for (long count : lastCounts.values()) {
            total += count;
        }
        System.out.println("Total = " + total);

        System.out.println(lastCounts.getClass().getName());
        System.out.println("-----------------------------");

        var threadMap = new ConcurrentSkipListMap<String, Long>();
        persons = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .peek((p) -> { //peek() is used for debugging code -- not recommended to be used like this otherwise
                    var threadName = Thread.currentThread().getName()
                            .replace("ForkJoinPool.commonPool-worker-",
                                    "thread_");
                    threadMap.merge(threadName, 1L, Long::sum);
                })
                .toArray(Person[]::new);

        System.out.println("Total = " + persons.length);

        System.out.println(threadMap);

        total = 0;
        for (long count : threadMap.values()) {
            total += count;
        }
        System.out.println("ThreadCounts = " + total);

    }
}

