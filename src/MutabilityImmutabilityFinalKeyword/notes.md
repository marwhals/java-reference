### Mutable vs Immutable

- Object has state----- etc data store in instance fields
- State can change after an object is created, either intentionally or unintentionally
- When state remains constant through the lifetime of the object and code is prevented from changing the sate this
  object is called an immutable object
- A mutable object is an object whose internal state remains constant
- A mutable object is an object whose internal state does not remain constant
- ----Not really a tradeoff --- more situational

# Immutable Objects - Advantages

- Isn't subject to unwanted, unplanned and unintended modifications known as side effects
- An immutable class is inherently thread-safe because no threads can at all change it once it has been constructed
- This allows for the use of more efficient collections and operations which *don't* have to manage synchronisation of
  access to this object

# Immutable Objects - Disadvantages

- Object can't be modified after it's been created
- This means that when a new value is needed, you're probably going to need to make a copy of the object with the new
  value
- If constant altering of text values is required it is probably more efficient to use a mutable object like a
  StringBuilder instance rather than generating a lot of new String objects

# Classes must be designed to produce immutable objects

NOTE: Understand that POJOs and Java Beans, which many code generation tools create are not by design immutable and
therefor this architecture may not be the preferred design for you class.
NOTE: A useful tool for building immutable classes is the final access modifier

### Deep Dive into Final Modifier, Methods, Fields, Classes, Variables

# The final modifier

NOTE: When you use the final modifier, you prevent any further modifications to that component

- A final method means that it can't be overridden by a subclass
- A final field means an object's field can't be reassigned or given a different value after its initialisation
- A final static field is a class field that can't be reassigned or given a different value after the class's
  initialisation process
- ---- A field declared on an Interface is always public, static and final
- A final class can't be override meaning no class can use it in the extends clause
- A final variable in a block of code means that once its assigned a value any other code can't change it
- A final method parameter means that you can't assign a different value to that parameter in the method code block

# Using the final modifier on methods

- Can be used on methods
- Using final with methods only makes sense in the context of wanting to restrict what you subclasses can override or
  hide
- Using final on an instance method means subclasses can't override it
- Using final on a class (static) methods means subclasses can't hide it

### Final Modifier - Static Methods, Hiding vs Overriding, Final Variables

- Best practice - always use the type reference when executing a static method
- If you're hiding a static method on a parent class, it is important to understand what the implication are for doing
  this
- Sticking to using a qualifier, the type reference to execute the specific static method will avoid the confusion

# Using final for variable

- Important to understand that when using final, it doesn't mean the variable is immutable at that point
- It means you can't assign or reassign a new instance or variable or expression to it after the initialisation
- If you use final for a local variable in a code block, you can only initiate it full or assign it a value just once
- Any other assignments will result in a compiler error
- If final is used for method parameters, this means you cannot assign any values to the method parameters in the code

### Side Effects of Mutability - Defensive Coding Techniques

# Controlling Change

- Java provides mechanisms to control changes and extensibility of your code......
- Can prevent
- -- Changes to data in Instance field,...the state of the object, by not allowing clients or subclasses to have access
  to these fields
- -- Changes to methods, by no allowing code to override or hide existing functionality
- -- Your classes being extended
- -- Instantiation of you classes

### Designing Immutable Classes - Private Final Fields, Defensive Copies

An immutable object doesn't change state once it's created
An immutable object is a secure object meaning that calling code can't maliciously or mistakenly alter it
NOTE: **An immutable object simplifies concurrency design**

# Strategies for Declaring a Class to produce immutable objects

- Make instance field private and final
- Do not define any setter methods
- Create defensive copies in any getters
- Usae constructor or factory method to set data, making copies of mutable reference data
- Mark the class final or make all constructors private

NOTE: ** Even if the class is mutable....some of these techniques can be used to alter mutability

### Shallow vs Deep Copies: Protecting Data Integrity in Java Applications

# Defensive copies as input

- When passing mutable types to an immutable object, a defensive copy shoudl be made
- The defensive copy should be assigned to the instance field

# Defensive copies as output

- When you retrieve data, you should first make a defensive copy and pass the defensive copy back to the calling code

# A shallow copy vs a Deep opy

- A shallow copy only make a copy of the structure and not a copy of the elements in the structure
- A deep copy makes a copy of both the structure and copies of each element in that structure

# Shallow copy

- When using copy methods on interfaces and helper classes the copy that's made will probably be a shallow copy
- A shallow copy of an array means a new array structure is created with the same number of indexed positions
- Each indexed position is assigned the same value that was in the previous array at the same position
- ***********A copy of the referenced element isn't made************

# Deep Copy

- Deep copies usually have to be manually implemented if you need it
- Each element is copied and would exist separately
- Deep copies may need to be applied to arrays and collections as well as composite classes to ensure mutability.

# Shallow vs Deep copy of a composite object

- A class can be composed of other classes, meaning its fields are instance of classes
- When cloning or copying this type of object, you may also need to copy the class's more complex fields

### Immutable Collections, Unmodifiable Views: Limitations and Best Practices

## Unmodifieable collections are NOT immutable collections

- It is very important to understand that unmodifiable collections are NOT immutable collections
- They become immutable collections only if the elements in the collections themselves are fully immutable
- They are collections with limited functionality that can help us minimise mutability
- -- You can't remove, add or clean elements from an immutable collection
- -- You also can't replace or sort elements
- -- Mutator methods will throw an UnsuupportedOperationException
- -- You can't create this type of collection with nulls

## Unmodifiable Collections vs Unmodifiable Collection Views

- Three primary collection interface, List, Set or Map have methods to get an unmodifiable copy on the specific interface
- --In addition java.util.Collections class offers methods to get unmodifiable views as shown
- -- These methods allow us to get *closer to the ideal of immutability* if its needed

[//]: # (TOOD add table here )

### Java Constructors: No-args, Initializers, Final Fields and Inheritance

# The instance initialiser block

- An instance initializer block of code declared directly in a class body
- This code gets executed when an instance of the class is created
- Instance initializers are executed before any code in class constructors is executed
- YOu can also have multiple initializer blocks
- *** They will be executed in the order they are declared

# Static initialisers

- A static initializer is called the first time a class is referenced or constructed
- A class can have any number of initialisation blocks
- They can be declared anywhere in the class body
- They're called in the order they appear in the code
- You might use this to setup some environment data or log information that related to the class before it can be used
- NOTE this will only be executed once during the class's construction and not each instances construction

### Demystifying Record Constructors: Canonical, Custom and Compact Forms

# Types of record constructors

- The canonical or Long constructor is the implicitly generated constructor.
- --- Can declare your own which means the implicitly one won't be generated.
- ---- If you declare your own you must make sure field get assigned a value
- The customer constructor is just an overloaded constructor. It must explicitly call the canonical constructor as it's
  first statement
- The compact or short constructor is a special kind of constructor used only on records. It's a succinct way of
  explicitly declaring a canonical constructor.

# The compact constructor

- Declared with no parentheses, so no arguments
- It has access to all the arguments of the canonical constructor. Don't confuse the argument with the instance fields
- You can't do assignments to the instance fields in this constructor
- -- The implicit canonical constructors assignments occur after the execution of this code
- -- You can't have both compact constructor and an explicit canonical constructor

# The Java Class File Disassembler

- "javap"
- It lists class members by default just public and protected members in a class file
- -- Helps you see implicitly code in the compiled class file

### Enhancing Code Security: Final Classes and Constructor Access Modifiers

# Final Classes

- Using final keyword on a class means it can't be extended
- -- Used if the class definition is complete and no subclasses are desired or required
- -- Enums and Records are final classes
- -- Subclasses can take advantage of mutable fields on parent classes if the parent classes aren't implementing
  defensive code
- -- Easy way to prevent this is to make your class final

**Comparison of Class Modifiers**

| Operations                              | final class | abstract class | private constructors only | protected constructors only                           |
|-----------------------------------------|-------------|----------------|---------------------------|-------------------------------------------------------|
| Instantiate a new instance              | yes         | no             | no                        | yes, but only subclasses, and classes in same package |
| A subclass can be declared successfully | no          | yes            | no                        | yes                                                   |

- Private constructors will prevent both a new instance and a new subclass from being created
- Protected constructors will prevent an instance from being created outside of a subclass or the package
- The final and abstract modifiers are incompatible and wouldn't be used in the saem declaration
- --- If you don't want your class to be instantiated, you can either make it abstract or use a more restrctive access
  modifier on the class

### Restricting Class Extension in Java with the Sealed Classes and Interfaces
# Sealed Classes JDK 17
- This modifier can be used for both outer types and nested types
- When used a "permits" clause is required in most cases which lists the allowed subclasses
- -Subclasses can be nested classes, classes declared in the same file, classes in the same package or if using java modules, in the same module
- ------> All code since JDK 9 is part of what is called an unnamed default module
- A sealed class and its direct subclasses create a circular reference

- Using the sealed keyword require the parent class to declare its subclasses using a permits clause
- This means the parent class has to know about every direct subclass and these have to exist in the same package in this case
- In addition, the sealed keyword puts a requirement on all the subclasses that wer declared in the permits clause
- It requires each subclass to declare one of the three valid modifiers for a class extending a sealed class
- ------- These are final, sealed or non-sealed

- All subclasses declared in the permits clause must be declared as final, sealed or non-sealed
- Declaring a class final, means not other subclasses can extend that class
- A subclass declared with a sealed modifier must in turn use a ```permits ``` clause.
- ---- It subclasses in turn have to use on of the three valid modifiers

- A subclass can use the non-sealed modifier. It is basically unsealing itself for all its subclasses