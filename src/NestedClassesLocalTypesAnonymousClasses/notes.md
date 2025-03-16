### Nesting classes (or types) within another class (or type)

A class contain other types withint the calss body such as other calsses interfaces enums and records
-- These are called nested types or nested classes
-- You might want to use nested classes when your classes are tightly coupled meaning their functionlity is interwoven

## Nested classes
- The four different types of nested classes you can use IN java are:

[//]: # (TODO insert table here)

## Important restrictions that were removed in JDK16
-- Before JDK 16 - Only static nested classes were allowed to have static methods
-- As of JDK16 all four types of nested classes can have static members of any type including static methods

### Static Nested Class
The static nested class is a class enclosed in the structure of another class, declared as static
This means the class if accessed externally requires the outer class name as part of the qualifying name
This class has the advantage of being able to access private attributes on the outer class
The enclosing class can access any attributes on the static nested class including private attributes

### Inner Classes
- Inner classes are non-static classes, declared on an enclosing class at the member level
- Inner classes can have any of the four valid access modifiers
- An inner class has access to instance members, including private members of the enclosing class
- Instantiating an inner class from external code is a bit tricky 
- ---- As ok JDK16 static members of all types are supported on inner classes

- To create an instance of an inner class you first need to have an instance of the Enclosing Class
- From that instance you call new followed by the inner class name and the parentheses taking any constructor arguments

### Local Classes
- Local classes are inner classes but declared directly in a code block usually a method body
- Becasue of that they don't have acces mondieers and are only accesible in that method body while its executing
- Like an inner class they have access to all fields and method on the enclosing class
- They can also access local variable and method argument that final or effectively final

# Local class;s catured variables
- When you create an instance of a local class, referenced variable used in the calss from the encolising code are captured
- --- That mean sa copy is made of them and the copy is stored with the instance
- This is done because the isntance is stored in a different memeory area than the local variable in the method
- for this reason if a local class uses local variable or method arguments from the encolinsc code these must be final or effecitilvey final

# Final Variables and Effectively Final
- See example

# Effectively final
- In addition to explicitly final variable you can also use effectively final variable in your local class
- A local variable or a method argument are effectively final if a value is assigned to them and then enver changed after that
- --- Effectively final variable can be used in a local class

# Additional Local Types
- As of JDK 16, you can also create a local record interface and enum type in your method block
- These are all implicitly static types and therefor aren't inner classes or types but static nested types
- ---- The record was introduced in JDK 16
- ------ Prior to that release, there was no support for a local interface or enum in a method or block

# Anonymous Classes
An anonymous class is a local class that doesn't have a name
All the nested classes we've looked at so fat have been created with a class declaration
The anonymous class is never created with a class declaration, but its always instantiated as part of an expression
Anonymous classes are used a lot less since the introduction of Lambda expression in JDK 8
-- Sometimes may be used