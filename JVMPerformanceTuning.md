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