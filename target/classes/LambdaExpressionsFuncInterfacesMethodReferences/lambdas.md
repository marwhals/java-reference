# Lambdas

- Can be thought of as a shorthand for an anonymous class that implements a functional interface - an interface that
  implements a single abstract method.
    - This makes code more concise and easier to understand
- Allows for the passing of blocks of code as parameters, offering a clean and flexible way to write clean and more
  functional code with little effort
    - Can simplify tasks
    - Method references build on this concept providing an even more concise way to refer to existing methods using
      lambda expression syntax

# The lambda expression

```java
int compare(T o1, T o2);

(o1 ,o2)->o1.

lastName().

compareTo(o2.lastName());

```

- Java infers the method. This requires something called a *functional interface*

# The syntax of a lambda expression

- A lambda expression consists of a formal parameter list, usually but not always declared in parentheses, the arrow and
  either ran expression or a code block after the arrow

# Functional interfaces what are they

- A functional interface is an interface that has exactly one abstract method
- Also known as SAM which is short for Single Abstract Method
- A functional interface is the target type for a lambda expression

# Understanding Lambdas

- In Java many classes use funtional interfaces in their method signatures, which allows you to pass lambdas as
  arguments to them.
    - This can reduce the amount of code that is written 😃

# The Consumer Interface

- The consumer interface is in the `java.util.function` package.
- Has one abstract method that takes a single argument and doesn't return anything
- `void accept(T t)`

|                 Lambda Expression                  | Description                                            |
|:--------------------------------------------------:|:-------------------------------------------------------|
|     `element -> System.out.println(element);`      | A single parameter without a type can omit parentheses |
|    `(element) -> System.out.println(element);`     | Parentheses are optional                               |
| `(String element) -> System.out.println(element);` | Parentheses required if a reference type is specified  |                       |
|  `(var element) -> System.out.println(element);`   | A reference type can be `var`                          |

# Relevance to Streams

- Many streams take functional interfaces as parameters, meaning you can code them with lambda expressions

# Lambda expressions with multiple parameters

|         Lambda Expression          | Description                                                                |
|:----------------------------------:|:---------------------------------------------------------------------------|
|         `(a, b) -> a + b;`         | Parentheses are always required, explicit types are not.                   |
| `(Integer a, Integer b) -> a + b;` | Explicitly types must be used for all parameters, not just one.            |
|     `(var a, var b) -> a + b;`     | If `var` is used for one parameter then it must be used for all parameters |

# Lambda expressions that return values

|           Lambda Expression            | Description                                                                                      |
|:--------------------------------------:|:-------------------------------------------------------------------------------------------------|
|           `(a, b) -> a + b;`           | When not using curly braces, the return keyword is unnecessary and will through a compiler error |
| `(a ,b) -> {var c = a + b; return c;}` | If you use a statement block a return is required.                                               |

# `java.util.function`

- Java provides a library of functional interface in the `java.util.function package`

## The four categories of Functional Interfaces

- There are 40 in the package and they can be categorised into four types

| Interface Category | Basic Method Signature | Purpose                                     |
  |:------------------:|:-----------------------|---------------------------------------------|
|      Consumer      | `void accept(T t)`     | execute code without returning data         |
|      Function      | `R apply(T t)`         | return a result of an operation or function |
|     Predicate      | `boolean test(T t)`    | test if a condition is true or false        |
|      Supplier      | `T get()`              | return an instance of something             |

## The Consumer interface

- The Consumer interface takes one argument of any type. `void accept(T t)`
- The BiConsumer interface takes two arguments, of type different types. `void accept(T t, U u)`

## A Consumer Lambda Expression Example

Consumer method `void accept(T t)`

```java
s ->System.out.

println(s);
```

## The predicate interface

- Take one or two arguments and always returns a boolean value
    - Used to test a condition and if the condition is true, to perform an operation

```java
boolean test(T t); // Predicate

boolean test(T t, U u); //BiPredicate

s ->s.

equalsIgnoreCase("Hello"); //Example

```

## The Function interface

- In addition to Function and BiFunction there is also UnaryOperator and BinaryOperator
    - Can think of UnaryOperator as a Function interface, but where the argument type is the same as the result type
    - BinaryOperator can be thought of as the same

  |  Interface Category   | Basic Method Signature |
                                                  |:---------------------:|:-----------------------|
  |   `Function<T, R>`    | `R apply(T t)`         |
  | `BiFunction<T, U, R>` | `R apply(T t, U u)`    |
  |  `UnaryOperator<T>`   | `T apply(T t)`         |
  |  `BinaryOperator<T>`  | `T apply(T t1 T t2)`   |

## The Supplier Interface `T get()`

- LIke a factory method
    - It will produce an instance of some object
        - Doesn't have to be a new or distinct result returned

## Valid Lambda Declarations for different number of arguments

```java

()->statement;

s ->statement;
(s)->statement;
(
var s)->statement;
(
String s)->statement;

/**
 - When using var, all arguments must use var.
 - When specifying explicit types, all arguments must specify explicit types
 */

(s,t)->statement;
(
        var s, var
t)->statement;
(
        String s, List
t)->statement;

```

## What is a method reference?

- Provides a compact more easier to read lambda expression for methods that are already defined on a class

```java

s ->System.out.

println(s);

System.out::println;

```

- A method reference abstract the lambda expression even further, eliminating the need to declare formal parameters
- Don't have to pass arguments to the method in question.
    - Method reference has double colons, between the qualifying type and the method name

### What methods can be used in method references

- They are based on the context of the lambda expression
    - This means the method reference is dependent on the targeted interface's method
    - You can reference a static method on a class
    - Can reference an instance method from either an instance external to the expression or an instance passed as on of
      the arguments
    - Or you can reference a constructor by using `new` as the method
        - *Method references are very useful for increasing the readability of your code*

### Deferred Method Invocation

**Very important to realise that when creating variable that or lambda expressions or method references; they are not
invoked at that point 😃**

# Understanding more complex method references

- A type reference refers to a class name, an interface name, an enum name or a record name.
- Static methods are called usually using Type References but can also be called by instance in code
- **This not true for method references**
    - Static methods, in method references and lambda expressions must be invoked using a reference type only
- There are two ways to call an instance method
    - The first is when you refer to the method with an instance derived from the enclosing code
    - This instance is declared outside the method reference
        - `System.out::println` method reference is an example of this
        - This is called a *bounded receiver*. An instance derived from enclosing code, used in the lambda expression on
          which the method will be invoked.
    - The second way
        - The instance used to invoke the method will be the first argument passed to the lambda expression or method
          reference when executed
        - This is know in some places as the Unbounded Receiver
        - It gets dynamically bound to the first argument, which is passed to the lambda expression when the method is
          executed.
            - **Can be confused with a static method reference using a reference type**

```java

Integer::sum //Type reference that invokes a static method

String::concat // Instance method on a specific String object. Needs to be called on a specific instance
// The above is valid when you use a method reference in the context of an unbounded receiver 🤔

```

- The unbounded receiver means that the first argument becomes the instance used on which the method getss invoked.
- Any method reference that uses `String::concat` must be in the context of a two-parameter functional method
    - The first parameter is the String instance on which the concat method gets invoked and the second argument is the
      String argument passed to the concat method/

## Four types of Method References

| Type                                                                     | Syntax                                               | Method Reference Example | Corresponding Lambda Expression |
|:-------------------------------------------------------------------------|:-----------------------------------------------------|:-------------------------|:--------------------------------|
| Static method                                                            | `ClassName::staticMethodName(p1,p2,....pn)`          | `Integer::sum         `  | `(p1, p2) -> p1 + p2`           |
| Instance method of a particular (Bounded) object                         | `ContainingObject::instanceMethodName(p1,p2,....pn)` | `System.out::println`    | `p1 -> System.out.println(p1)`  |
| Instance method of an arbitrary (Unbounded) object (as determined by p1) | `ContainignType[=p1]::instanceMethodName(p2,....pn)` | `String::concat `        | `(p1, p2) -> p1.concat(p2)`     |
| Constructor                                                              | `ClassName::new`                                     | `Class::new`             | `() -> new Class()`             |

## Method Reference Examples (No arguments, one argument and two arguments) TODO see Oracle documentation

|                               | No Args    | One Argument    | One Argument        | One Argument             | Two Arguments  | Two Arguments      | Two Arguments               |
|:------------------------------|:-----------|:----------------|:--------------------|:-------------------------|----------------|--------------------|-----------------------------|
| Types of Method References    | Supplier   | Predicate       | Consumer            | Function / UnaryOperator | BiPredicate    | BiConsumer         | BiFunction / BinaryOperator |
| Reference Type (Static)       |            |                 |                     |                          |                |                    | Integer::sum                |
| Reference Type (Constructor)  | Class::new |                 | n/a                 | Class::new               |                |                    | Employee::new               |
| Bounded Receiver (Instance)   |            |                 | System.out::println |                          |                | System.out::printf | new Random()::nextInt       |
| Unbounded Receiver (Instance) | n/a        | String::isEmpty | List::clear         | String::length           | String::equals | List::add          | String::concat              |

## Convenience Methods on function interfaces in java.util.function package

- For `andThen`, `compose` on the *Function* category🤔 of interfaces, any interim functions are not required to have the
  same type of arguments.
    - Instead, one function's output becomes the next function's input and the next functions input.It is not
      constrained to
      any specific types, *except the last function executed in the chain* .
- The *Consumer's* `andThen` method is different because it never returns a result, so you use this when you're chaining
  methods independent of one another.
- The *Predicate* methods always return a boolean, which will combine the output of the two expressions to obtain a
  final boolean result.

| Category of Interface | Convenience method example     | Notes                                                        |
|:----------------------|:-------------------------------|:-------------------------------------------------------------|
| Function              | `function1.andThen(function2)` | Not implemented on IntFunction, DoubleFunction, LongFunction |
| Function              | `function2.compose(function1)` | Only implemented on Function & UnaryOperator                 |
| Consumer              | `consumer1.andThen(consumer2)` |                                                              |
| Predicate             | `predicate1.and(predicate2)`   |                                                              |
| Predicate             | `predicate1.or(predicate2)`    |                                                              |
| Predicate             | `predicate1.negate()`          |                                                              |

## Comparator's additional helper methods -- TODO see Oracle Documentation

| Type of Method | Method Signature                                  |
|:---------------|:--------------------------------------------------|
| `static`       | `Comparator comparing(Funtion keyExtractor)`      |
| `static`       | `Comparator naturalOrder()`                       |
| `static`       | `Comparator reverseOrder()`                       |
| `default`      | `Comparator thenComparing(Comparator other)`      |
| `default`      | `Comparator thenComparing(Function keyExtractor)` |
| `default`      | `Comparator reversed()`                           |


