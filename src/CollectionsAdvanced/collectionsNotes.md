# Collections

### Java Array vs Java List

- An array is mutable but it cannot be resized
    - Java provides classes that allow for the addition and removal of items and resize a sequence of elments
        - These classes are said to implement a List's behaviour

### What is a list

- A list is an example of an **Interface**
- The list interface describes a set of method signatures that all List classes are expected to have

### The ArrayList

- The ArrayList is a class that maintiains an array in memory that's actually larger then what is needed in most cases
- It keeps track of the capacity or maximum size of the array in memory
- It also keeps track of the elements that have been assigned or set, which is the size of the ArrayList
- As elements are added to an ArrayList, its capacity may need to group. This occurs automatically behind the scnees
- This is why ArrayLists are considered resizable

### Arrays vs ArrayLists

| Feature                        | Array                        | ArrayList |
|--------------------------------|------------------------------|-----------|
| primitive types supported      | Yes                          | No        |
| indexed                        | Yes                          | Yes       |
| ordered by index               | Yes                          | Yes       |
| duplicates allowed             | Yes                          | Yes       |
| nulls allowed                  | Yes, for non-primitive types | Yes       |
| resizable                      | No                           | Yes       |
| mutable                        | Yes                          | Yes       |
| inherits from java.util.Object | Yes                          | Yes       |
| implements List interface      | No                           | Yes       |

```java
import java.util.ArrayList;

String[] array = new String[10]; // Array declaration, all NULL elements

ArrayList<String> arrayList = new ArrayList<>(); // ArrayList declaration, compiler will only allow strings to be added

String[] array = new String[]{"ads", "awe", "asdgf"};
String[] array = {"ads", "awe", "asdgf"};

ArrayList<String> arrayList = new ArrayList<>(List.of("asd", "asd", "asdsa"));

```

- Use specific type when declaring an ArrayList so the compiler can help
- The `List.of` method can be used to create a list with variable argument list of elments

### Element information

%ADD TABLE

- The number of elmeents is fixed when an array is created
- Can get the size of the array from the attribute length on the array instance
- Array elments are accessd with the use of square brackets and an index that ranges from 0 to on less than the number
  of elements
- The number of element in an ArrayLIst may vary and can be retrieved with a method on the instance named size()
- ArrayList elmenet sare access with get and set methods, also using an index ranging from 0 to one less than the number
  of elmenetns
- ArrayLists come with buliding support for printing out elmenetns includeing nested lists
    - Arrays don't, so you need to call Arrays.toString, passing the aray as an argument

### Multi dimenstional ArrayList

```java
import java.util.ArrayList;

ArrayList<ArrayList<String>> =multiDList =new ArrayList<>();
```

### Finding an elment in an Array or ArrayList

In Arrays, you can use binary search. This requires an array to be sorted and there is no guarantee for what is returned
when dealing with duplicates

- In array lists

```java
boolean contains(element)

boolean containtsAll(list of elements)

int indexOf(element) // returns -1 if nothing is found

int lastIndexOf(element) // returns -1 if nothign is found
```

### Sorting

```java
import java.util.ArrayList;

ArrayList<String> arrayList = new ArrayList<>(List.of("first", "second", "third"));

arrayList.

sort(Comparator.naturalOrder());
        arrayList.

sort(Comparator.reverseOrder());

```

- Can pass a comparator type argurment to ArrayLists sort method that specifies how a sort should be performend

### Array as an ArrayList

- The Arrays.asList method returns an ArrayList backed by an array

```java
String[] originalArray = new String[]{"first", "second", "third"}
```

- Conceptualy can be through of as putting an ArrayList wrapper around an existing array
- Any change made to the List is a change to the array that backs it
- This also means that an ArrayList created by this method is not resizable

## Creating special kinds of lists

- Using Array.asList - Returned List is NOT resizable, but is mutable

```java 
import java.lang.reflect.Array;
import java.util.Arrays;

var newList = Array.asList("sunday", "monday", "tuesday");

String[] days = new String[]{"Sunday", "Monday", "Tuesday"};
List<String> newList = Arrays.asList(days);

```

- Using List.of -> Returned List is *Immutable*

```java
var listOne = List.of("Sunday", "Monday", "Tuesday");

String[] days = new String[]{"Sunday", "Monday", "Tuesday"};
List<String> listOne = List.of(days);
```

- Both are static factory methods on types
- Both support variable arguments, so you can pass a set of arguments of on type, or you can pass the array

### Creating Arrays from ArrayLists

```java
import java.util.ArrayList;

ArrayList<String> stringLists = new ArrayList<>(List.of("Jan", "Feb", "Mar"));
String[] stringArray = stringLists.toArray(new String[0]);
```

- If the length of the array you pass has more elements than the list, extra elmeents will be filed with the default
  values for that type
- If they length of the array you pass has less elements than the list, the method will strill return an array will the
  saem number of elmentns in it as the list

## Array of primitive values

- When an array of primitve types is allocated, space is allocated for all of its elmeent contiguoyusly.
- For reference types (Objects), the elments of an array are references and not the values
    - Objects **addresses** are stored contigiously in a Java array
    - This is a cheap, fast lookup an ddoesn't change what size the ArrayList is
    - To remove an elmenet, the referenced addresses havce to be re-indexed or shifted to remive an empty space
    - When adding an elmenmtn, the array that backs the ArrayLIst might be too small and might need to be reallocated
        - Either of these operations can be an expensive or time-conusming process if the number of elmenetn is large

### ArrayList Capacity

- An ArrayList is created with an initial capacity depending on how many elements we create the list with, or if you
  specify a capacity when creating the list
    - If they nuymber of elments exceeds the current capacity, Java needs to reallocate memory to fit all the elmente,
      this can be a costlry operation, especially if your ArrayList contains a lost of items
    - Java language doesn;'t really specify exactly how it determins the new capacity or promise that it will continueu
      to increase the capcity in the saem way in furture versions

### Big O Notations

... Add if required
Adding to an ArrayList $O(1)^*$ worst cases is $O(n)$
Goes to $O(1)$ after the capcity is been increaed

### ArrayList operations - Big O

| Operation                   | Worst Case | Best Case |
|:----------------------------|:----------:|----------:|
| `add(element E)`            |  $O(1)^*$  |           |
| `add(int index, element E)` |   $O(n)$   |  $O(1)^*$ |
| `contains(element E)`       |   $O(n)$   |    $O(1)$ |
| `get(int index)`            |   $O(1)$   |           |
| `indexOf(element E)`        |   $O(n)$   |    $O(1)$ |
| `remove(int index)`         |   $O(n)$   |    $O(1)$ |
| `remove(element E)`         |   $O(n)$   |           |
| `set(int index element E)`  |   $O(1)$   |           |

Note: *Indexed operations are $O(1)$* since its a simple calculation
Only gets more constly with indexed add or remove methods, if the ArrayList needs to be re-indexed or re-sized

### LinkedList

- Not indexed at all
- There is no array storign the addresses in a neat ordered way like in ArrayList
- Instead each elment thats added to a linkedin list forms a chain and the chain has links to the previous elmenet and
  the next elmeent
- Note: There is a doubly linkedin list with a head pointer and tail pointer

Big $O$

- Getting an elment from the list or setting a value of elment isn't simple math anymore with a LInkedList
    - To find an elmeent you would need to start at the head of the tail and check if the elmeent matches or keep track
      of the number of elmenmetns traversed. The index is not stored as a part of the list.
    - This is very expensive in terms of time and memory compleixty
    - On the other hand it is much easier to add and remove elements on this kind cf data structure
        - No new arrays need to be ccrearted, elements don't need to be shifted
        - Cheap in terms of computational copmlexity.

| Operation                   | Worst Case ArrayList | Best Case ArrayList | Worst Case Linked List | Best Case Linked List |
|:----------------------------|:--------------------:|--------------------:|-----------------------:|----------------------:|
| `add(element E)`            |       $O(1)^*$       |                     |                 $O(1)$ |                       |
| `add(int index, element E)` |        $O(n)$        |            $O(1)^*$ |                 $O(n)$ |                $O(1)$ |
| `contains(element E)`       |        $O(n)$        |              $O(1)$ |                 $O(n)$ |                $O(1)$ |
| `get(int index)`            |        $O(1)$        |                     |                 $O(n)$ |                $O(1)$ |
| `indexOf(element E)`        |        $O(n)$        |              $O(1)$ |                 $O(n)$ |                $O(1)$ |
| `remove(int index)`         |        $O(n)$        |              $O(1)$ |                 $O(n)$ |                $O(1)$ |
| `remove(element E)`         |        $O(n)$        |                     |                 $O(n)$ |                $O(1)$ |
| `set(int index element E)`  |        $O(1)$        |                     |                 $O(n)$ |                $O(1)$ |

### Comparison
- For a linkedlist, adding elmeents to the start or the end of the List will almoist always be more efficietn than an ArrayList
- When removing elments, a Linkedlist will be more efficient because it doesn't require re-indexing, but the elmeent still need to be found using the traversal mechanism, which is why it is $O(n)$ as the worst casses.
- Removeing elmeents from the start or end of the List will be more efficient for a LinkedList

- ArrayList is uaully the better default choice for a List, especially if the List is predominant,.ly for storing and reading data
- If you know the maximum number of possible items, then it's probably better to use an Arraylist but set its capacity
- An ArrayList index is an int type, so its capacity is limited by this which is ```Integer.MAX_VALUE``` on java
- Consider using a LinkedList if adding and processing or manipulating a large amount of elments and the maximum elments isn't known by may be greated than the java `Integer.MAX_VALUE`
- A LinkedList can be more efficient when items are being processed predominantly from either the head or tail of the list

## LinkedLists
- An ArrayList is implemented on top of an Array, but a LinkedList is a doubly linked list
  - Both implement the List Interface
  - LinkedList also implements the Queue and Stack methods as well
    - Can be used as a FIFO/ Queue datastructures or stack/ LIFO datastructures or a dequeue/ double ended queue

## Iterators
- Way to traverse lists, alternative to for loops
- Similar to a database cursor
- An iterator is an *object* that allows traversal over records in a collection

### How it works
- When you get an instance of an iterator, you can call the `next` method to get the next elmeent in the list.
- Can use the `hasNext` method to check if any elements remain to be processed

### Iterator vs ListIterator
- An Iterator is forward only and only supports the remove method
- A ListIterator allows you to navigate both forwards and backwards. Besides the `remove` method, it all supports the `add` and `set` methods.

- **The Iterators cursor positions are *between* the elements**

## Introduction to auto boxing
- Primitive data types generally represent the way data is stored on an operating system
  - Objects can take up aditional memory and require more processing power
- Primtives are not supported in collections
  - Java provides wrappers for these primitives
  - Can go from primitive to wrapper (boxing) or wrapper to primitive called (unboxing) with ease in Java

### Boxing?
- Each wrapper has a static overloaded factory method `valueOf` which takes a primitive as an argument and returns an instance of the wrapper class

[//]: # (????? TODO add this if needed)
- Rarely need to manually box primitives since Java supports something called **autoboxing**
```java
Integer boxedInt = 15;
```
- Java supports this syntax and it is actually th eprefered approach.
- Behind the scense java is doing the boxing. I.e an Integer *instance* is being created.
- Allowing for Java to do it is the besst way since it will be more efficient

```java
int unboxedInt = boxedInteger.intValue();
```
- Every warapper calss support a method to return the primitive value it contains. This is called unboxing. *It is unecessary to manually unbox*


## Enums
- Java implementation of enumerations
  - A special data type that contains predefined constants
    - A variable that can't be changed once set.
      - Similar to an array,except its elments are known, not changeable and each element can be referred to by a constant name instead of an index position

- An enum in its simplest form, is described like a class. Howevery the keyword enum replaces the keyword class
- Can name an enum with any valid identifier, but like a class, upper camel case is preferred.
  - Within an enum, you can decalre a list of constant identidiers, separated by commas. Via convention these are all uppercase labels
    - An enum is declared by the way you declare constants
    - Used to declare a limited set of constants and sometimes there is a natural orderign to the listing
