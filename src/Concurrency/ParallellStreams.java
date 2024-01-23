package Concurrency;

import java.util.Arrays;
import java.util.Random;

/*
Parallel streams - allow you to perform operations on collection in parallel thus potentially speeding up data processing

Advantages
- Improved performance on multi-core CPUs
- Simplified code for concurrent processing
- Automatic workload distribution among available threads

Disadvantages
- Parallel streams introduce some overhead, such as the need to create and manage multiple threads.
---- This can be significant for small arrays
- Parallel streams need to synchronise their operations to ensure that the result are correct. This synchronization can also add overhead, especialy for small arrays

* Java optimizations may not even use a parallel stream
 */

public class ParallellStreams {
    public static void main(String[] args) {

        int numbersLength = 100_000_000;
        long[] numbers = new Random().longs(numbersLength,
                1, numbersLength).toArray();

        long delta = 0;
        int iterations = 25;

        //racing streams
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            double averageSerial = Arrays.stream(numbers)
                    .average()
                    .orElseThrow();
            long elapsedSerial = System.nanoTime() - start;

            start = System.nanoTime();
            double averageParallel = Arrays.stream(numbers)
                    .parallel() //steam is parallel now
                    .average()
                    .orElseThrow();
            long elapsedParallel = System.nanoTime() - start;
            delta += (elapsedSerial - elapsedParallel);
        }

        //Benchmark testing -----
        System.out.printf("Parallel is [%d] nanos or [%.2f] ms faster on average %n",
                delta / iterations, delta / 1000000.0 / iterations);
    }
}
