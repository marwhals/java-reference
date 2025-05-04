package performanceTuning;

public class JITDemo {

    public static void main(String[] args) {
        JITDemo demo = new JITDemo();
        for (int i = 0; i < 10_000_000; i++) {
            demo.compute(i);
        }
        System.out.println("Done!");
    }

    public int compute(int value) {
        return (value * 31) / 7;
    }
}
