package IOAndFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/* Handling exceptions
- LBYL - check for errors before you perform an operation
- EAFP - assumes an operation will usually succeed and then handles any errors if they occur

Consider tradeoffs in efficiency, readability and debugging

Recognising checked exceptions
- A checked expcetion is not a runtime exception
- An unchecked exception is an instance of runtime exception or one of its subclasses

NOTE - try with resources is better than using the finally clause for closing resources
finally can be used to perform logging or updating a UI
--- May be used to hide errors which make debugging harder
--- Can make code harder to maintain and read.
 */

public class FileExceptions {
    public static void main(String[] args) {

        String filename = "testing.csv";

        testFile(filename);

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("I can't run unless this file exists");
            System.out.println("Quitting Application, go figure it out");
            return;
        }
        System.out.println("I'm good to go.");
    }

    private static void testFile(String filename) {
        /*
        - Handling checked exceptions
        - Try catch block OR
        - Change method signature and include a throws clause
         */

        Path path = Paths.get(filename);
        try {
            List<String> lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Maybe I'd log something either way...");
        }
        System.out.println("File exists and able to use as a resource");
    }
}
