package performanceTuning;

/**
 * Edit accordingly
 * bash command:  java -Xlog:gc*:file=performanceTuning/gc.log:time,uptime,level,tags performanceTuning/GarbageCollectorDemo
 */

public class GarbageCollectorDemo {


    public static void main(String[] args) {
        GarbageCollectorDemo demo = new GarbageCollectorDemo();
        demo.createObjects();
        System.out.println("Garbage collection demo complete!");
    }

    public void createObjects() {
        for (int i = 0; i < 10_000_000; i++) {
            String temp = new String("Object " + i);
        }
    }
}
