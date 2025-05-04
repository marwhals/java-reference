# Java Performance tuning notes taken from https://www.udemy.com/course/java-application-performance-and-memory-management

These notes are to provide a starting point for more advanced topics relating to the JVM

---

# Just In Time compilation

### What is ByteCode

### JVM Just In Time Compilation

- Compiler performs optimisations
    - Popular code is ran native machine code (runs faster) - code that can be understood by the OS
    - Some code is run as interpreted bytecode
    - Implication -> Code will run faster the longer it is left run
        - Virtual machine can profile the code
        - Process of compiling is ran in a separate thread
            - Thread running the code that is interpreting the byte code and executing the byte code won't be affected
              by the thread doing the JiT compiling
            - The process of JiT compiling doesn't stop the applications running
            - While compilation is taking place, the JVM will continue to use an interpreted version, but once that
              compilation is complete and the native machine code version is available, the virtual machine will
              seamlessly switch to use the compiled version instead of the raw bytecode.
            - If application is heavy on CPU processing using all available CPU resources a potential performance hit
              might by seen if JiT is taking place.
                - This would only be noticed in high power, critical applications. May still be worth it for the
                  performance benefits of native code despite the dip in processing
                    - Impact of JiT is that consideration needs to be given to when the performance of an application
                      occurs
                        - Result may differ when code is initially ran vs when it has been running for a while.
                - Need to consider the performance of the code before and after it has been natively compiled
- Any piece of bytecode can be compiled to native code

### `-XX:+PrintCompilation` used to find which methods are being compiled

## JIT Compilation Explained (`-XX:+PrintCompilation`)

This project demonstrates how Java's Just-In-Time (JIT) compiler works using a simple method that's called repeatedly to
trigger optimization.

### JIT Compilation Output Columns

| **Column**      | **Example**                          | **Description**                                                                                                                                      |
|-----------------|--------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| Timestamp       | `45`                                 | Time (ms) since JVM start when the method was compiled.                                                                                              |
| Compilation ID  | `2`                                  | Unique ID for the compiled method.                                                                                                                   |
| Tier            | `3`                                  | Compilation level: `0`=interpreter, `1–3`=C1, `4`=C2 (optimized tier).                                                                               |
| Method Name     | `performanceTuning.JITDemo::compute` | Full class and method being compiled.                                                                                                                |
| Bytecode Length | `(17 bytes)`                         | Length of the method's bytecode.                                                                                                                     |
| Suffix / Note   | `made not entrant`, `%`, `!`, `n`    | Extra info: <br> `%`: OSR (On-Stack Replacement)<br> `!`: Deoptimization<br> `n`: Native method<br> `made not entrant`: JIT invalidated this version |

---

### Running with JIT Flags

```bash
javac */JITDemo.java
java -XX:+PrintCompilation *.JITDemo
```

### C1 and C2 compilers, logging the compilation activity

- C1 compiler does the first three levels of compilation
- C2 does more advanced compilation
- Virtual machine decides which level of compilation to apply to a particular block of code based on how often it is
  being run and how complex or time consuming it is.
    - Referred to as profiling the code
        - C2 code is even more optimised than C1 code
- Only frequently used code is optimised
    - If the code doesn't perform anything complex than there may be no performance benefit
    - JVM can also place code in the *code cache*
- `java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation ....`

### Tuning the code cache size

- C2 compiled code may be placed in the code cache for improved performance.....cache is limited size...swapping can
  occur
    - If cache is full, application console may produce this error
      `VM warning CodeCache is full. Compiler has been disabled`
    - Can use `-XX:+PrintCodeCache` to find out about size of the code cache
- In more complex applications, if the size of the used memory is getting close to the size of the code cache then a
  tweaked code cache might be helpful
    - Maximum size of code cache is dependent on the version of Java you are using
- Changing the code cache size
    - `InitialCodeCacheSize` - size when application starts
    - `ReservedCodeCacheSize`- maximum size of the code cache, how much it grow to if needed
    - `CodeCacheExpansionSize` - dictates how quickly the code cache should grow as it becomes populated
    - Example: `java -XX:ReservedCodeCacheSize=32g/k/m`

### Remotely monitoring the code cache with **JConsole**

- Can cause extra overhead and use extra memory of the code cache

---

# Selecting the JVM

## When downloading a JVM there is a choice of 32 Bit and 64 Bit JVM

| 32 Bit                        | 64 Bit                                                               |
|-------------------------------|----------------------------------------------------------------------|
| Might be faster if heap < 3GB | Might be faster if using longs and doubles                           |
| Max heap size = 4GB           | Required if heap is > than 4GB                                       |
|                               | Max heap size is OS dependent                                        |
| Client compiler only          | Client and server compilers                                          |
|                               | Use client compiler only`-client`. Ignored on some operating systems |
|                               | Use 32bit server compiler only`-server`.                             |
|                               | Use 64bit server compiler only`-d64`.                                |

- 32GB doesn't include the C1 and C2 compiler only C compiler
- Consider C1 and C2 as Client vs Server in terms of what the application is going to do.
- For short lived applications that have low memory requirements 32 bit JVM may perform better

## Turning of tiered compilation
`-XX:-TieredCompilation`

## Tuning native compilation within the Virtual Machine
- Can find out the value of all flags using `java -XX:+PrintFlagsFinal`
- get java process id `jps`
- `jinfo -flag CICompilerCount`
- `jinfo -flag CompileThreshold`
- `-XX:CICompilerCount=n` For larger applications increasing the number of threads doing the compiling may make a difference
- `-XX:CompileThreshold=n` - the number of times a method needs to run before being natively compiled

## Java Memory

Three parts: Stack, Heap and Metaspace

### The Stack
- Every thread has its own stack
- Managed by the JVM
- JVM knows when data on the stack can be destroyed
- Stack data can only be seen by the thread that owns that stack
- Can only store simple data types

### The heap
- Data on the heap can be accessed by multiple threads if programmed that way
- Used for objects
- Java Memory Rules
  - Objects are stored on the heap
  - Variables are a reference to the object
  - Local variables are stored on the stack

---

# How memory works - the stack and the heap

---

# Passing objects between methods

---

# Escaping References

---

# The Metaspace and Internal JVM memory optimisations

## The role of the Metaspace
- Used to store metadata
  - This information is used to primarily store information about classes, methods like which methods have been compiled into bytecode and which have been compiled into native code.
  - It is also where Static variables are stored
    - Can think of the meta space as having the same role as the stack for any object or any variable that we declare a static variable
    - Static primitives are stored entirely in the meta space and static objects are stored on the heap, but with the object pointer or reference held in the meta space
      - Variables in the meta space are permanently there and never reach a state where they can be garbage collected
    - All classes and all threads have access to within a Java program have access to the meta space. Hence the same for static variables
    - Public and private refer to data than can be seen outside of a class

## The PermGen
*Java 7 specific*

## Are objects always created on the heap?
- Java heap scope is managed by brackets/code blocks
- All objects go on the heap in Java
- An object that is not shared...i.e only exists in that code block will actually be placed on the Stack via JVM optimisations
- 
## The String Pool
- String are immutable, same strings will have the same references despite being seen as two different objects
- This won't work for calculated Strings
  - Virtual machine can detect these things, string deduplication. See the underlying value and remove one of them.

## Interning Strings
- Placing strings in the string pool `String abc = someOtherObjectInstance.toString().intern()`
  - Only calculated strings and very long string will not be placed in the string pool, otherwise it is usually automatic

---

# Tuning the JVM's memory settings

## How the string pool is implemented
- Implemented using a HashMap
  - 16 Buckets. String is hashed and then placed in one of these buckets
    - Two identical objects will always have the same hashcode

## Understanding the size and density of the string pool
- Can use `-XX:+PrintStringTableStatistics`
- Important to be away for the density of the String pool for larger applications

## Tuning the size of the string pool
- `-XX:StringTableSize=(use a prime here)` -use a prime number for efficiency
  - If the average bucket size is 40+ it may be worth changing this

## Tuning the size of the heap
- Must be big enough to store the string pool
- When dealing without of memory errors don't just increase the max heap size....actual issue is probably a memory leak
  - May be worth right sizing the heap when given prior knowledge

## Shortcut syntax for heap tuning flags
-XX:+PrintStringTableStatistics
-XX:StringTableSize=n
-XX:MaxHeapSize=n -Xmx
-XX:InitialHeapSize=n -Xms
-XX:+UnlockDiagnosticVMOptions
-XX:+PrintFlagsFinal


---

# Introducing Garbage Collection

---

# Monitoring the Heap

---

# Analysing a heap dump

---

# Generational Garbage Collection

---

# Garbage Collector tuning and selection

---

# Using a profiler to analyse application performance

---

# Assessing Performance

---

# Benchmarking with JMH

---

# Performance and Benchmarking Exercise

---

# How Lists Work

---

# How Maps Work

---

# Other Coding Choices

---

# GraalVM

- Alternative to the standard JVM which should provide better performance and more.
    - Only for Linux and Mac
- Some features are already available in the standard JDK

## About the GraalVM

- Faster than the standard JVM for running java byte code
- Provides an alternative Java compiler and this might produce more performant byte code
- ‼ Can use GraalVM to natively compile Java code to software that will run natively on a given computer
    - Means you don't need to install a JVM to run the code -> result: code will run faster
    - run `native-image` command to compile
- **Out of date** Using the Graal Compiler with OpenJDK11 on Linux
    - `XX:+UnlockExperimentalVMOptions`
    - `XX:+EnableJVMCI`
    - `XX:+UseJVMCICompiler`

---

# Using other JVM Languages

### Kotlin vs Java - Kotlin doesn't have any primitives

- Object types have to be used
    - These will become byte code anyway
    - Var vs Val - Just a design time constraint
    - Variables can never be `NULL`

*Can use javap file to see the differences in byte code*

*See Oracle Docs*

### Can disassemble bytecode back into Java

Example tool - CFR

---

# Summary