# Communicating outside the JVM using resources

- Resources - Files, network connections, database connections, streams or sockets.
- Used to exchange information with outside systems
- Exception handling is important in this sceanario

# What happens when using file access classes

- Java will delegate to the operating system to open a file from the Operating Systems File System
- Will perform the following steps
-
    - Check if the file exists
-
    - If the file exists check if the user or process has the proper permissions for the type of access being requested
-
    - If this is true then file metadata is retrieved, and a file descriptor is allocated. The descriptor is a handle to
      the opened file.
-
    - An entry is made in the file table or file control block table of the operating system to track the opened file
-
    - The file may be locked
-
    - The file may be buffered by the OS, i.e memory is allocated to cache all or part of the file contents to optimise
      read and write operations.

# Closing and opening files

- Opening files in Java looks like instantiating any other object
- Calling open is not required, possible to forget that the file is open
- Closing the file hanle will free up memory used to store any file related data and allow other processes to access the
  file
- Most Java classes which interact with files, will also implement and autocloseable interface

# Java packages for file

- java.io is the original package
- java.nio is the non-blocking IO
- java NIO2 is "New IO"

# IO Exceptions

- Type of checked exception specific to external resources/ IO
- Represents and anticipated problem that may occur
- There are subtypes which for common error
- Checked exceptions are handled two ways. Try Catch blocks or changing the method signature.
- Two coding styles "LBYL" (check before the operation is performed) and "EAFP" (Assume it works and handle any errors
  if they ahppen)
- LBYL - More efficient if errors are rare, more verbose if errors are common
- EAFP - Can be more concise and easier to read, harder to debug if error are unexpected
- An unchecked exception is a subclass of runtime exception

# Finally clause

- Used with try statement which always requires a catch or finally clause
- The final clause is always the last block after the catch
- Always executes regardless of what happens in the other blocks
- Finally, blocks do not have access to the variables to in the try or catch blocks
- Used for resource cleanup
- try with resources syntax is a better approach than the finally clause for closing resources
- Can be used for logging or updating the interface
- Can be difficult to read and understand code that uses this clause
- Can hide errors which make debugging more difficult
- Harder to maintain if code which is not used for cleanup tasks is in the finally clause

# Legacy IO

- FileReader class implements AutoCloseable interface, through its parent reader
- This class opens a file resource implicitly
- When creating an instance of a file, the actual file is not being opened
- Instead, a file handler is being used that allows for OS-like operations

# File Handle vs Resource

- A file handle is a reference to a file that is used by the operating system to keep track of the file
- It is an abstract representation of the file and it does not need any actual data from the file
- A file resource is the actual data from the file
- It is stored on disk and can be accessed by the operating system, and by applications

# File System concepts ------ etc

# Path interface

- An interface and not a class
- Paths consist only of static methods that return a path instance, unlike the file instance.

# IO - Using file

- When you're using the file class you get an instance and then you execute a method on that instance
- The behaviour is on the instance itself

# NIO2 using files and path instead of "File"

- Get an instance of path
- Use factory methods on Path
- Static methods are called on the file class to make changed on the path instance passed as an argument.

# File system functionalities and methods in a table

| Functionality                   | File instance methods           | File static methods, with Path argument                    |
|---------------------------------|---------------------------------|------------------------------------------------------------|
| Create file                     | `createNewFile()`               | `createFile(Path p)`                                       |
| Delete directory or file        | `delete()`                      | `delete(Path p)` <br> `deleteIfExists(Path p)`             |
| Check path type                 | `isDirectory()` <br> `isFile()` | `isDirectory(Path p)` <br> `isRegularFile(Path p)`         |
| Get byte size of file           | `length()`                      | `size(Path p)`                                             |
| List directory contents         | `listFiles()`                   | `list(Path p)`                                             |
| Create directory or directories | `mkdir()` <br> `mkdirs()`       | `createDirectory(Path p)` <br> `createDirectories(Path p)` |
| Rename                          | `renameTo(File dest)`           | `move(Path src, Path dest)`                                |

# NIO.2 file operations

- Support asynchronous I/O operations
- File locking, including more granular locking. I.e lock a region rather than the whole file
- File metadata retrieval
- Symbolic link manipulation
- File system notifications. I.e changes occurring on a path, can be made watchable to registered services

# NIO2 file operations performance

- Generally perform better - NIO2 types are non-blocking, asynchronous access to resources by multiple threads is
  supported
- Memory is managed more efficiently, reading and writing files directly to and from memory into buffers through a "
  FileChannel"
- You can also read from or write to multiple buffers in a single operation

# Path methods

Files.getAttribute method returns this data as an object, casting may be required
Some of the file information can be obtained via specific methods which are in the final column

| String Literal to pass to the `Files.getAttribute` method | Type       | Alternate method            |
|-----------------------------------------------------------|------------|-----------------------------|
| `"lastModifiedTime"`                                      | `FileTime` | `Files.getLastModifiedTime` |
| `"lastAccessTime"`                                        | `FileTime` |                             |
| `"creationTime"`                                          | `FileTime` |                             |
| `"size"`                                                  | `Long`     | `Files.size`                |
| `"isRegularFile"`                                         | `Boolean`  | `Files.isRegularFile`       |
| `"isDirectory"`                                           | `Boolean`  | `Files.isDirectory`         |
| `"isSymbolicLink"`                                        | `Boolean`  | `Files.isSymbolicLink`      |
| `"isOther"`                                               | `Boolean`  |                             |

# Walking file trees

- Walks file tree depth first.
- Can accumulate information about the children up to the parent
- Java provides entry points in the walk to execute operations through a FileVisitor interface
- These Events are
- --- Before visiting a directory
- --- After visiting a directory
- --- When visiting a file
- --- A failure to visit a file

# Java 1.0 IO Review

- Disk reads, reading something from a hard disk
- Expensive in terms of time and resource usage.
- File Buffer defn: Computer memory for holding temp data while it is being read from a file
- --- Allows for the efficiency of data transfer and processing
- --- Reduces the number of direct interactions, or disk reads, against the actual storage device
- Input Streams defn: An InputStream is an abstract class, representing an input stream of bytes
- --- Represents a source of data, and a common interface for reading that data
- --- Input Streams can return a byte stream or a character stream
- --- For files the implementation is the FileInputStream
- ------ Class is used for files containing binary data
- ------ Using the read method on FileInputStream is very inefficient. If it needs to be used it should be wrapped in a
  BufferedInputStream
- Readers defn: Readers read characters, InputStreamReader is a bridge from byte streams to character streams
- --- If you want to read a character stream, it's recommended to use a FileReader. FileReader is doing buffered
  reading.
- --- A BufferedReader will also do buffered reading, using a much larger buffer size than the FileReader.
- ---- Can modify the size of the buffer on BufferedReader. Default size is large enough usually, BufferedReader also
  provides convenience methods for reading lines of text.

# Character Sets

- Defn: A collection of symbols, letters, numbers, punctuation marks and other characters
- Each character is assigned a unique numerical code called a "code point". This allows computers to store, transmit and
  interpret text
- Two common character sets are ASCII and Unicode-- ASCII being the oldest and most used and Unicode being designed to
  support all the world's writing systems

# Character Encodings

- Character encoding is the process of assigning numbers to various characters called "glyphs"
- A glyph can be an alphabetical character in any language...punctuation, emojis etc
- Different ways to represent glyphs with a numeric value

# ASCII Encodings

## Character Encoding Comparison

| Encoding   | Size   | Includes Latin Alphabet | Notes                               |
|------------|--------|-------------------------|-------------------------------------|
| US-ASCII   | 7 bits | No                      | Smaller range of characters         |
| ISO-8859-1 | 8 bits | Yes                     | More Widely Supported than US-ASCII |

# Unicode Encodings

## UTF Encoding Comparison

| Encoding | Size                    | Benefits                                                                                                                          |
|----------|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| UTF-8    | Variable (1 to 4 bytes) | Most popular encoding on the internet. <br> Includes ISO-8859-1, and more. <br> Can represent characters from all writing systems |
| UTF-16   | 2 bytes                 | Widely Supported                                                                                                                  |
| UTF-32   | 4 bytes                 | More efficient and straightforward to process, but uses more storage space. <br> Rarely used.                                     |

# Character set considerations

- UTF-8 is better for most applications
- --- More efficient, widely supported and can represent a wider range of characters
- If using ASCII characters only ISO-8859-1 may be a better choice for efficiency reasons
- Java has common encodings specified on an enum, called StandardCharsets
- Useful doc link https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/charset/StandardCharsets.html

# Support on Files for reading data from a file

- All these method read the "entire" contents of a file into memory
- These methods support files up to about 2GB in size

## File Reading Methods

| Method Signature                                          | Description                                                  | Closes file?          |
|-----------------------------------------------------------|--------------------------------------------------------------|-----------------------|
| `byte[] readAllBytes(Path path) throws IOException`       | Reads entire contents of any file into a byte array.         | Yes                   |
| `String readString(Path path) throws IOException`         | Reads entire contents of a text file into a string           | Yes                   |
| `List<String> readAllLines(Path path) throws IOException` | Reads entire contents of a text file, into a list of string. | Yes                   |
| `Stream<String> lines(Path path) throws IOException`      | Reads entire contents of a text file                         | On Terminal Operation |

# Writing data to a file

- Typical use cases - Storing user data, logging to a log file, storing config, exporting data for exchange, offline
  usage in a file cache, generating file products
- Writing concepts are similar to reading concepts
- Ideas of buffered data becomes important, as does multiple writes to a single file from multiple threads

# Opening options

- Available options are found on an enum in the `java.nio.file` package called `StandardOpenOption`

## Default options for `Files.write` methods

## File Options

| Option            | Description                                                                                      |
|-------------------|--------------------------------------------------------------------------------------------------|
| CREATE            | This creates a new file if it does not exist.                                                    |
| TRUNCATE_EXISTING | If the file already exists, and it's opened for WRITE access, then its length is truncated to 0. |
| WRITE             | The file is opened for write access.                                                             |

# Writer Classes

| Writer         | Buffering                                        | Data Format                     | Features                                                             | Use Case                                                          |
|----------------|--------------------------------------------------|---------------------------------|----------------------------------------------------------------------|-------------------------------------------------------------------|
| BufferedWriter | Yes                                              | Characters                      | Supports line breaks with newline method.                            | Writing large amounts of text to a file                           |
| FileWriter     | Yes but much smaller buffer than BufferedWriter, | Characters                      | No separate method for line separators, would need to write manually | Writing small amounts of text to a file                           |
| PrintWriter    | No, but often used with a BufferedWriter.        | Characters, numbers and objects | Familiar methods, that have same behavior as System.out methods      | Writing text to a file, formatting output, and outputting objects |

# Flushing

---- For writing files, there is temporary storage, that gets filled up as writes are executed, on a Writer class
---- Physical writes to disk happen when the buffer is flushed
---- This is the process of taking the text stored in the buffer, and writing it to the output file, and clearing the
buffers cache
---- Frequency of flushing can be affected by a number of factors, including the size of the buffer, the speed of the
disk, and the amount of data that's being written to the file.
---- The buffer is always flushed when a file is closed
---- Buffer can be manually flushed by calling the flush method
---- Can manually flush a buffer by calling the flush method
---- This could be done in a scenario where more flushes are required like in a time sensitive scenario.
---- Any other thread or process that is reading the file won't be able to see the buffered text until the flush occurs.

# RandomAccessFile - Another way to access data from a file
- This class provides the ability to directly access and modify data at any specific location within the file
- Behaves like a large array of bytes stored in the file system
- File pointer - A kind of cursor or index into the implied array
- A RandomAccessFile both reads and writes binary data, using special methods which keep track of how many bytes will be read or written
- This class can be used for both read and write operations

## RandomAccesFile file pointer
- When a RandomAccessFile is opened its pointer is at 0. i.e the start of the file.
- To move the file pointer, you execute a method on the file, called seek, passing it a long value, the position in the file you wish to go to.
- To get the file pointer, you execute getFilePointer
- Depending on the type of read or write method you're using, the file pointer will move a certain number of bytes when these operations are complete
- See Java API for more documentation on this

## Use cases
- Avoid loading a large file into memory by loading a smaller data structure that will allow you to navigate the bigger file
- RandomAccessFile lets you fast forware or backward to a position in the file using the seek method
- -- Can only read in data that matters for you application
- -- To do this you need to understand how many records are in your file, what its record length is and how you want to identify each record to retrieve it

## Understanding the Random Access File's index
- RandomAccessFile needs an index, which houses a file pointer position to each record of interest
- Index could be implied in a file with fixed records....example..row number

- For variable length records the row id alone isn't enough
- -- Could store the length or the starting file pointer position

## RandomAccessFile index location
- In the case of fixed width it may not exist
- Otherwise it could be at the start of the data or at the end
- Could also be in a separate file altogether

# Serialisation

- DataOutputStream lets an application write primitive Java data types to an output stream in a portable way
- DataInputStream can then use a DataInputStream to read the data back in
- Serialisation defn: The process of translating a data structure or object into a format that can be stored on a file
- --- Only instances of Serializable classes can be serialised, meaning the class must implement the serialisable
  interface
- --- This interface has no methods, just to mark the class as serializable
- --- All subtypes of a serializable class are themselves serializable
- Deserialization defn: Writes the class of the object, the class signature, and the values of non-static fields
- --- These elements are used to restore the object, and its state, during the read operation
- --- This process is called reconstituting the data, or deserialization

## serialVersionUID
- serialVersionUID field is a runtime field, that the compiler will implicitly create if it's not declared for classes that are serializable.
- It's based on class details such as the number of fields, their types and declarations.
- When reading an object from a stream, the runtime checks the stored serialVersionUID.
- If there is no match the runtime will throw this invalid class exception.
- -- Versions can be incompatible between JVMs
- -- Different compilers will generate the serialVersionUID differently. Reccomendation is that it is included as a private static dield

## Incompatible changes
- Changing the declared type of primitive field
- Deleting a field
- Changing a non-static field to static, or a non-transient field to transient
- ---- Full list can be seen on the Java documentation specs

## Compatible changes
- Adding fields
- Adding writeObject and readObject methods
- Changing the access to a field
- Changing a field from static to non-static or transient
- --- See documentation for this

# Transient modifier
- The transient modifier is used to indicate that a field should not be serialised.
- Useful for variables that contain sensitive information or just variable that don't need to be persisted for other reasons.