# Testing and Debugging in Java
- Breakpoints etch in IntelliJ

## IntelliJ debugger
- IntelliJ debugger stuff

## IntelliJ Debugger Basics and Techniques

### Setting Breakpoints

- Click in the gutter next to the line number where you want the program to pause.
- Conditional breakpoints: Right-click the breakpoint, and add a condition to stop only when specific criteria are met.

### Debugging Techniques

1. **Stepping Through Code:**
    - *Step Over (F8):* Executes the current line, stepping over function calls.
    - *Step Into (F7):* Steps into methods to see what is happening inside.
    - *Step Out (Shift+F8):* Steps out of the current method and resumes execution at the calling method.

2. **Using Watches and Evaluations:**
    - Add variables or expressions to the "Watches" panel to monitor their values.
    - Use the "Evaluate Expression" tool to test and debug expressions on the fly.

3. **Field Watchpoints:**
    - Add watchpoints on fields to track when their value changes.
    - Right-click a field in the code, and select `Add Watchpoint`.

### Smart Step Into

- Use *Smart Step Into (Shift+F7)* when multiple methods are called on the same line. It allows you to choose which
  method to step into.

### Set Value During Debugging

- While paused, right-click a variable in the "Variables" pane and use "Set Value" to modify it. This can help test
  hypothetical scenarios.

### Unit Testing and Debugging

- Debug JUnit tests directly by right-clicking the test and selecting "Debug."
- Use assertions to validate code behavior while stepping through tests.

### Tips for Advanced Debugging

- Use the "Frames" tab to navigate between threads and frames of execution.
- "Drop Frame" allows you to re-enter a method without restarting the entire debug session.
- 
## Assertions - Beyond the basics and Exception Handling

1) assertNotEquals() - can use this instead of assertEquals()
2) assertTrue() - verifies that the condition is true
3) assertFalse() - verifies that the condition is false
4) assertNull() - checks that the object is null
5) assertNotNull() - checks that the object is not null
6) assertArrayEquals() - checks if two arrays are equal (*content wise* not object reference wise)
7) assertThrows() - asserts that a specific exception is thrown
8) assertSame() - verifies that two objects refer to the same object
9) assertNotSame() - verifies that two objects do not refer to the same object
10) assertEquals() - checks whether two values are equal
11) assertThat() -allows you to define custom matching rules (e.g., using Hamcrest matchers) for improved readability and custom assertions. I.e match a range of values.

## Parameterised Unit Tests - Data Driven Testing in JUnit