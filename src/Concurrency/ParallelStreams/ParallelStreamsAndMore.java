package Concurrency.ParallelStreams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Person(String firstName, String lastName, int age) {

    private final static String[] firsts =
            {"Able", "Bob", "Charlie", "Donna", "Eve", "Fred"};
    private final static String[] lasts =
            {"Norton", "OHara", "Petersen", "Quincy", "Richardson", "Smith"};

    private final static Random random = new Random();

    public Person() {
        this(firsts[random.nextInt(firsts.length)],
                lasts[random.nextInt(lasts.length)],
                random.nextInt(18, 100));
    }

    @Override
    public String toString() {
        return "%s, %s (%d)".formatted(lastName, firstName, age);
    }
}

public class ParallelStreamsAndMore {
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
                        .collect(Collectors.groupingBy(
                                Concurrency.Misc.Person::lastName,
                                Collectors.counting()
                        ));

        lastNameCounts.entrySet().forEach(System.out::println);

        long total = 0;
        for (long count : lastNameCounts.values()) {
            total += count;
        }
        System.out.println("Total = " + total);
    }

}
