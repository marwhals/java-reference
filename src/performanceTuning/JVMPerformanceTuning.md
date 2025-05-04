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
- Virtual machine decides which level of compilation to apply to a particular block of code based on how often it is being run and how complex or time consuming it is.
  - Referred to as profiling the code
    - C2 code is even more optimised than C1 code
- Only frequently used code is optimised
  - If the code doesn't perform anything complex than there may be no performance benefit
  - JVM can also place code in the *code cache*
- `java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation ....`

### Tuning the code cache size
- C2 compiled code may be placed in the code cache for improved performance.....cache is limited size...swapping can occur
  - If cache is full, application console may produce this error `VM warning CodeCache is full. Compiler has been disabled`
  - Can use `-XX:+PrintCodeCache` to find out about size of the code cache
- In more complex applications, if the size of the used memory is getting close to the size of the code cache then a tweaked code cache might be helpful
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

---

# How memory works - the stack and the heap



---

# Passing objects between methods

---

# Escaping References

---

# The Metaspace and Internal JVM memory optimisations

---

# Tuning the JVM's memory settings

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