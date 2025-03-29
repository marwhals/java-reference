### The Collections Framework

### Java Collections

- A collection is simply an object that represents a group of related objects
- In general, the objects in a collection share some common relationship or purpose
- In Computer Science, different types of collections have specific names and expected behaviours. These include arrays,
  lists, vectors, sets, queues, tables, dictionaries and maps (key/ value pairs)
- Strictly speaking, Arrays and Array utilities are not considered a part of this framework
- All collection objects implement the Collection interface, except for maps.
    - The collection interface is the foundation of the collection hierarchy in Java

## The Collection Interface

- As with most in software hierarchies, it provides an abstract representation of the behaviours needed to manage a
  group
  of objects.
- This interface is often used when you want to pass collections around and manipulate them with maximum flexibility and
  generality
    - Remember, an interface allows is to define objects by what they can do, rather than by their specific structure or
      implementation
    - Examining the methods in the Collection interface, you'll see the fundamental operations that any collection
      regardless of its specific type or structure, must support.
    - In java the term element refers to a member of the collection being managed
    - A list can be either indexed as an ArrayList or not like LinkedList is implemented to support all of these methods
      as well.
    - Derived interfaces may have specific ways to add, remove, get and sort elements for their specific type of
      collection, in addition to those defined on the Collection interface itself.
    - Map does not extend collection, but it is still a part of the framework

# The List

- A List is an ordered collection
- These can be sequenced in memory like an ArrayList, or maintain links to the next and previous values, as a LinkedList

# The Queue

- A Queue is a collection designed for holding elements prior to processing. In other words, the processing order
  matters, so the first and last positions or the head or tail are prioritised
- These are often implemented as a FIFO, but can be implemented as a LIFO/ Stack
    - A Deque supports both

# The Set

- A Set is a collection conceptually based off of a mathematical set
- It contains no duplicate elements and isn't naturally sequenced or ordered
    - Java has three implementations which are the HashSet, the TreeSet and the LinkedHashSet
    - These are distinguished by the underlying way they store the elements in the set
- A SortedSet is a set that provides total ordering of the elements

# The Map

- A Map is a collection that stores key and value pairs
- The keys are a set, and the values are a separate collection where the key keeps a reference to a value
- Keys need to unique, but values don't
- Elements in a tree are stored in a key value Node, also called an "Entry"

# Polymorphic Algorithms

- "A piece of reusable functionality"

### What is the Collection Class

- It's important to understand that this Collection class in not the Collections Framework
- The framework contains many interfaces and implemented classes, as well as helper classes for which this Collections
  class is just one examples
- At one time, Java had interfaces but no support for static or default methods on them, so useful methods were packaged
  in these helper classes
- Some of these methods have since been implemented on the interfaces themselves, but there's still some functionality
  on the Collections class that may be useful

### Understanding the importance of the hash code

- HashSet and HashMap are based on the hash codes of objects
    - Sets are designed to ensure uniqueness by not allowing duplicates, adding an element rquries first checking it it
      already exists in the set
    - For large sets, this check can become costly potentially taking $O(n)$ time, which is linear in relation to the
      size of the set.
        - One way to reduce this cost is through a technique called hashing
        - By using hashing, we can distribute elements into different buckets. If we divide the elements into two
          buckets
          and each element can reliably determine its bucket the lookup time can be roughly halved
        - Similarly, if we use four buckets, the elements are distributed even further which could reduce the number of
          comparisons needed in each bucket improving lookup efficiency
- A hashed collection will optimally create a limited set of buckets to provide an even distribution of the object
  across the buckets in a full set
- A hash code can be any valid integer....so it could be 4.2 billion valid numbers
    - If you collection only contains 100,000 elements, you don't want back it with a storage mechanism of 4 billion
      possible placeholders
    - Additionally, iterating through 100,000 elements sequentially to find a match or duplicate would be inefficient
    - Instead, a hashing mechanism take an integer hash code and a capacity declaration that specifies the number
      buckets
      over which to distribute the objects
    - It then maps the wide range of possible hash codes into a more manageable range of bucket identifiers
    - Hashed implementations often combine the hash code with other techniques to create an efficient bucketing system
      that aims to evenly distribute objects, thereby optimising performance

# Hashing in Java

- There are two types of equals.... hashCode and equals
    - `==` means two variable have the same reference to a single object in memory
    - Because both references are pointing to the same object, then this is a "good equality test"
    - Objects can be considered equal in other instances as well, if their attribute values are equal. The String class
      overrides this method, so that it compares all the characters in each String to confirm that two String are equal

- Can also create your own hashCode method
    1) It should very fast to computer
    2) It should produce a consistent result each time it's called. For example, you wouldn't want to use a random
       number generator or a data time-based algorithm that would return a different value each time the method is
       called
    3) Objects that are considered equal should produce the same hashCode
    4) Values used in the calculation should not be mutable

# Creating the hashCode method

- When implementing the hashCode method, its common to use a small prime number as a multiplier. Prime numbers are
  preferred because they help spread out hash codes more evenly, reducing the chance that different objects will end up
  with the same hash code, which is important in data strcutures like HashMap or HashSet
- Using a prime number as a multiplier is especially useful if your data might naturally cluster or follow patterns,
  which could otherwise lead to uneven distribution of hash codes.
  - Its bes to avoid small primes like 2,3,5 or 7 because many numbers are divisible by them which could lead to less
    effective distribution and slower performance in hash-based collections
  - **If you are using your own collections in hashed collections you'll want to override both the equals and the
    hashCode methods**

### The Set

- A Set is not implicitly ordered.
- A Set contains no duplicates
- A Set may contain a single null elements
- Sets can be useful because operations on them are very fast

# Set Methods

- The set interface define the basic methods, add, remove and clear to maintain the items in the set.
- You can also check if a specific item is in the set using the contains method.
- Interestingly enough, there's no way to retrieve an item from a set
  - You can check if something exists, using contains, and you can iterate over all the elements in the set but
    attempting to get the 10th element for example from a set isn't possible with a single method

# The HashSet class

- The best-performing implementation of the Set interface is the HashSet class
- The class uses hashing mechanisms to store the items
- This means the hash code method is used to support even distributions of object in the set
  - Oracle describes this class as offering constant time performance for the basic operations (add, remove, contains
    and size)
  - This assumes the has function disperses the elements properly among the buckets
  - Constant time has big  $O$ class of $O(1)$
    - HashSet actually uses a HashMap in its own implementation

# Set Operations

- When trying to understand data in multiple sets, may want to get the data that is in all the sets that's in every set
  or
  the data where there's no overlap
    - The collection interfaces bulk operations (addAll, retainAll, removeAll and containAll) can be used to perform
      these set operations
- Set operations $A$  ${Union}$  $B$
    - Java doesn't have a union method on Collection, but the addAll bulk function when used on a Set can be used to
      create a union of multiple sets.
    - The intersection of two or more Sets will return only the elements the Sets will have in common
        - Set is also symmetric
        - A difference subtracts elements in common from one Set and another leaving only the distinct elements form the
          first Set as the result.
        - Can think of set symmetric the elements from all sets that don't intersect

### Ordered Sets

- If you need an ordered set, you'll want to consider either the LinkedHashSet or the TreeSet
- A LinkedHashSet maintain the insertion order of the elements
- The TreeSet is a sorted collection, sorted by the natural order of the elements, or by specifying the required sort
  during the creation of the set

# The LinkedHashSet

- The LinkedHashSet extends the HashSet class
- It maintains relationships between elements with the use of a doubly linked list between entries
- The iteration order is therefore the same as the insertion order of the elements meaning the order is predictable
- All the methods for the LinkedHashSet are the same as those for the HashSet
- Like HashSet, it provides constant-time performance, O(1) for the add, contains and remove operations

# TreeSet

- A TreeSet's class uses a data structure that's a derivative of what's called a binary search tree or BTree for short,
  which based on the concept and efficiencies of the binary search
    - binarySearch method on this kind of List is very fast if the elements are sorted
    - The search iteratively tests the mid-range of a group of elements to be searched, to quickly find its element in a
      collection
    - As elements are added to a TreeSet, they're organised in the form of a tree where the top of the tree represents
      the mid-point of the elements
        - Further binary divisions become nodes under that
        - The left node and its children are elements that are less than the parent node
        - The right node and its children are elements greater than the parent node
    - Instead of looking through all the elements in the collection to locate a match, this allows the tree to be
      quickly traversed, each node being a simple decision point
    - The main point is the tree remains balanced as elements are added
    - $O(1)$ is constant time, meaning the time or cost of an operation doesn't change regardless of how many elements
      are
      processed
    - $O(N)$ is linear time, meaning it grows in line with the way the collection grows
    - Another notation is $O(log(n))$, which mean the cost falls between constant and linear time. *Often related to
      some kind of "divide and conquer" algorithm strategy*
        - The TreeSet promises $O(log(n))$, which mean the cost falls somewhere in between constant linear time
        - The TreeSet promises $O(log(n))$ for the add, remove and contains operations compared to the HashSet which has
          constant time $O(1)$ for those same operations

# The TreeSet interface hierarchy

[//]: # (- %TODO)

# The TreeSet relies on Comparable or Comparator methods

- Elements which implement Comparable (said to have natural order sort, like String, and numbers) can be elements of a
  TreeSet
- If your elements don't implement Comparable, you must pass a Comparator to the constructor.

# NavigableSet methods to get the closest matches

All methods shown below take an element as an argument and return an element in the Set, the clostes mathch to the
element passed**
| Element passed as the argument | `floor(E)` (`<=`)                                | `lower(E)` (
`<`)                                  | `ceiling(E)` (`>=`)                               | `higher(E)` (
`>`)                           |
|--------------------------------|----------------------------- |-------------------------------------------- |----------------------------- |--------------------------------------------|
| **In Set**                     | Matched Element | Next Element `<` Element or `null` if none found | Matched
Element | Next Element `>` Element or `null` if none found |
| **Not in Set**                 | Next Element `<` Element or `null` if none found | Next Element `<` Element or `null`
if none found | Next Element `>` Element or `null` if none found | Next Element `>` Element or `null` if none found |

# Getting subsets from a TreeSet

All three methods, headSet, tailSet and subSet return a subset of elements backed by the original set

## Sub Set Methods

| **Sub Set Methods**                                                              | **Inclusive**                                | **Description**                                                                                              |
|----------------------------------------------------------------------------------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| `headSet(E toElement)`                                                           | `toElement` is exclusive if not specified    | Returns all elements less than the passed `toElement` (unless inclusive is specifically included).           |
| `headSet(E toElement, boolean inclusive)`                                        |                                              |                                                                                                              |
| `tailSet(E fromElement)`                                                         | `fromElement` is inclusive if not specified  | Returns all elements greater than or equal to the `fromElement` (unless inclusive is specifically included). |
| `tailSet(E toElement, boolean inclusive)`                                        |                                              |                                                                                                              |
| `subSet(E fromElement, E toElement)`                                             | `fromElement` is inclusive if not specified, | Returns elements greater than or equal to `fromElement` and less than `toElement`.                           |
| `subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)` | `toElement` is exclusive if not specified    |                                                                                                              |

# When would you use a TreeSet?

- The TreeSet provides several advantages due to its built-in functionality, especially compared to other Set
  implementations. However, it comes with a higher performance cost
- Consider using a TreeSet when:
    - You have a moderate number of elements
    - You need a collection that is automatically sorted and remains sorted as elements are added or removed
    - You want to ensure that no duplicate elements are stored

In such cases, TreeSet is a strong alternative to ArrayList, especially when you need both sorting and uniqueness

### The map interface

A map in the collection framework is another datastructures
Although it is still grouping of elements its different because elements are stored with keyed references

[//]: # (TODO add little table here)

The Map has K for it's key type and V for the value type
As with any generic classes, the only restriction on these types is that they must be reference types and not primitives

# Map characteristics

- A Java map can't contain duplicate keys
- Each key can only map to a single value

# Map Implementations

Three classes that implement tha map interface - HashMap, LinkedHashMap and the TreeMap

- The HashMap is unordered
- The LinkedHashMap is ordered by insertion order
- The TreeMap is a sorted map

# What is a view

- The view "or view collection in Java" doesn't store elements but depends on a backed collection that stores the data
  elements

# What is the purpose of view collections

- Views purpose is to hide the details of the underlying data structures to make it easier for clients to use the data
- Allow for the manipulation of the collections without knowing the exact details about the storage of the data
    - In other words we don't have to keep learning new methods to manipulate the data
    - As long as we can get a collection view of the data, we can use many of the collection methods to simplify our
      work
- There's a static nested interface on the Map interface and that its name is Entry
- Concrete classes implement both the Map interface and the Map.Entry interface
- The HashMap implements Map and has a static nested class node that implements the Map.Entry interface
- The HashMap maintains an array of these nodes in a field called table, whose size is managed by Java and whose indices
  are determined by hashing functions
    - For these reasons a HashMap is not ordered

# The Map's view collections

- Focus now on the three view collections we can get from the map.
    - These are the keySet,
    - the entrySet
    - and values
- We know a map has keys and these can't contain duplicates
- These keys can be retrieved as a Set view by calling the keySet method on any map object
- Each key value pair is stored as an instance of an Entry and the combination of thekey and value will be unique
  because the key is unique
- A set view of these entries or nodes in the case of the HashMap can be retrieved from the method on entrySet
- Finally, values are stored and referenced by the key and values can have duplicates meaning multiple keys could
  reference a single value
- you can get a collection view of these from the values method on a map instance

# Functionality available to set returned from keySet()

The set returned from the keySet method is backed the Map
This means changes to the Map are reflected in the Set and vice versa

- The set supports element removal which removes the corresponding mapping from the Map
- You can use the methods remove, removeAll, retainAll and clear
- It does not support the add or addAll operations

# Ordered and Sorted Map implementations

- The Map interface has the LinkedHashMap and TreeMap classes
- The LinkedHashMap is a key value entry collection whose keys are ordered by the insertion order
- The TreeMap is sorted by its keys, so a key needs to implement Comparable, or be intialised with specified Comparator

# TreeMap's View Collections

| **View Collection Methods**                                        | **Notes**                                                                                                                                                               |
|--------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `entrySet()`, `keySet()`, `values()`                               | Provides views of mappings, keys, and values. These are views available to any map, and not just the `TreeMap`. I include them here to remind you, **These are views.** |
| `descendingKeySet()`                                               | Provides reversed order key set or map, reversed by the key values.                                                                                                     |
| `descendingKeyMap()`                                               |                                                                                                                                                                         |
| `headMap(K key)`                                                   | Provides views of either the first or last parts of the map, divided by the key passed.                                                                                 |
| `headMap(K key, boolean inclusive)`                                | **The head map is by default EXCLUSIVE** of all elements higher or equal to the key.                                                                                    |
| `tailMap(K key)`                                                   | **The tail map is by default INCLUSIVE** of all elements higher or equal to the key.                                                                                    |
| `tailMap(K key, boolean inclusive)`                                |                                                                                                                                                                         |
| `subMap(K fromKey, K toKey)`                                       | Provides a view of a contiguous section of the map, higher or equal to the `fromKey` and lower than the `toKey`, so the **toKey is EXCLUSIVE.**                         |
| `subMap(K fromKey, boolean inclusive, K toKey, boolean inclusive)` | The overloaded version allows you to determine the inclusivity you want for both keys.                                                                                  |

# EnumSet and EnumMap

- Can use any List, Set or Map with an enum constant
- The EnumSet and EnumMap each have a special implementation that different from the HashSet or HashMap
- These implementations make these two types extremely compact and efficient
- There's no special list implementation for enum types

# The EnumSet

- The EnumSetr is a specialised Set implementation for use with enum values
- All of the elements in an EnumSet must come from a single enum type
- The EnumSet is abstract meaning we can't instantiate it directly
- It comes with many factory methods to create instances
- In general this Set has much better performance that using an HashSet with an enum type
- Bulk operations (such as containsAll and retainAll) should run very quickly in constant time O(1) if they're run on an
  EnumSet and their argument is an EnumSet

- The EnumMap is a specialised Map implementation for use with enum type keys
- The keys must all come from the same enum type, and they're ordered naturally by the ordinal value of the enum
  constants
- This map has the same functionality as a HashMap with O(1) for basic operations
- The enum key type is specified during construction of the EnumMap either explicitly by passing the keys types class or
  implicitly pass ????another???? EnumSet
- In general this map has better performance than using a HashMap with an enum type

# Two types of EnumSet implementations

- EnumSets are represented internally as bit vectors, which are just a series of ones and zeroes
- A one indicates that the enum constant (with an ordinal value that is equal to the index of the bit is in the set)
- A zero indicates the enum constant is not in the set
- Using a bit vector allows all Set operations to use a bit match which makes it very fast
- A RegularEnumSet uses a single long as its bit vector, which means it can contain a maximum of 64 bits representing 64
  enum values
- A JumboEnumSet gets returned if you have more than 64 enums
