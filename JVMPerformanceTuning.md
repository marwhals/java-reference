# Java Performance tuning notes taken from https://www.udemy.com/course/java-application-performance-and-memory-management

These notes are to provide a starting point for more advanced topics relating to the JVM

---

# Just In Time compilation

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