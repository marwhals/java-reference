### Streams

- Streams are a mechanism fo describing a while series of processes, before actually executing them
- Streams and collections are designed for different purposes
  - A collection is used to store and manage a series of elements in Java, providing direct access to the Collection
    elements
  - Can use collections to manipulate or query a set of data
  - There is nothing you can do with a steam that, you couldn't do with a Collection
    - However, a stream was designed to manage the processing of elements
    - Streams don't actually store elements, instead these elements are computed on demand, from a data providing
      source
  - Streams are lazy like lambda expressions variables
  - When calling some methods on a stream, execution may no immediately occur
  - Instead, you would need to invoke a special operations on the stream, like you would by calling a lambdas function
    method
    - This "special operation" is called a terminal operation
- Why use streams?
  - The make the code to process data uniform, concise and repeatable....similar to SQL
  - When, working with large data collections, parallel streams will provide a performance advantage

# Pipelines

- The source of the stream is where the data elements are coming from
- All pipelines start wia stream
  - There are other kinds of source and ways to create new streams including infinite streams
- Stream Pipelines end in a terminal operations, which produces a result or side effect
- Everything else between the source and the terminal operation is an intermediate operations
  - An intermediate operation is not required
  - Can have a pipeline that has just a source and terminal operation....these can be quite commons
- Every intermediate operation processes elements on the stream and returns a stream as a result
- Execution of the intermediate operations is dependent, first on a terminal operation being specified and second
  on an optimisation occurring
  - Stream pipeline is kind of a workflow suggestion
    - Before th eprocess begins, the stream implementation will perform an evaluation, to optimised the means to the
      end
    - It will determine the best way to get the elements needed and the most efficient way to process to give the
      result that you have asked for
    - The result will be consistent each time but the process to get there is not guaranteed to be
    - Optimisation my change the order of the intermediate operations, it may combine operations or skip them
      altogether
      - It is very important to avoid side effects in your intermediate operations
- Once you invoke a terminal operation on a stream, it can be thought of as the pipeline being opened and the flow
  beginning
  - It will continue until all processes have been performed and a result produced
  - At that point, the valve is shut and the pipeline closed
  - You can't turn it back on, or reuse it for a new source
    - *If you want to do the same sort of thing with a different variable for one of the intermediate operations,
      you'd need to set up a new pipeline*

Stream types may change as the pipline process progresses
  - An intermediate operation can usually be recognised by its signature, because it returns a stream
  - This doesn't mean that the element type of the Stream can't change
  - In practice, you'll be regularly transforming your stream element to a different type

??? Two can produce infinite streams, the Stream.generate method as well as Stream.iterate, which doesn't include a
Predicate parameter

| Method                                                             | Finite | Infinite |
|--------------------------------------------------------------------|--------|----------|
| Collection.stream()                                                | X      |          |
| Arrays.stream(T[])                                                 | X      |          |
| Stream.of(T...)                                                    | X      |          |
| Stream.iterate(T seed, UnaryOperator<T> f)                         |        | X        |
| Stream.iterate(T seed, Predicate<? super T> p, UnaryOperator<T> f) | X      |          |
| Stream.generate(Supplier<? extends T> s)                           |        | X        |
| \*\*IntStream.range(int startInclusive, int endExclusive)**        | X      |          |
| \*\*IntStream.rangeClosed(int startInclusive, int endExclusive)**  | X      |          |

** range and rangeClosed also avaiable on LongStream, with a long time produced for both

# Intermediate Operations

| Return Type | Operation                                                                                                                                   | Description                                                                                                                                                                                                                                                              |
|-------------|---------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `Stream<T>` | `distinct()`                                                                                                                                | Removes duplicate values from the Stream.                                                                                                                                                                                                                                |
| `Stream<T>` | `filter(Predicate<? super T> predicate)`  <br> `takeWhile(Predicate<? super T> predicate)` <br> `dropWhile(Predicate<? super T> predicate)` | These methods allow you to reduce the elements in the output stream. Elements that match the filter's Predicate are kept in the outgoing stream, for the filter and takeWhile operations. Elements will be dropped until or while the dropWhile's predicate is not true. |
| `Stream<T>` | `limit(long maxSize)`                                                                                                                       | This reduces your stream to the size specified in the argument.                                                                                                                                                                                                          |
| `Stream<T>` | `skip(long n)`                                                                                                                              | This method skips elements, meaning they won't be part of the resulting stream.                                                                                                                                                                                          |

Java API designers designed the Stream to allow for the processing of data in a declarative way. Similar to SQL in a
database.
  - This lets you say what should happen, and not actually how it will happen
    - Filter operation represent your where clause and sorted would be your order by clause and so
    - There are aggregate functions commonly used on queries as well, such as max, min, count and so on.

# Terminal operations

- Some are designed to find matches, most of which are targets for a Predicate lambda expression
- Some are designed to transform stream data into a collection, some other reference type
- Others aggregate information, to count elements or find a minimum or maximum value and don't take arguments
- The primitive streams have average and sum as well and a summaryStatistics operation which give you count, min,
  max, average and sum in one result

| Matching and Searching | Transformations and Type Reductions | Statistical (Numeric) Reductions | Processing       |
|------------------------|-------------------------------------|----------------------------------|------------------|
| `allMatch`             | `collect`                           | `average²`                       | `forEach`        |
| `anyMatch`             | `reduce`                            | `count`                          | `forEachOrdered` |
| `findAny¹`             | `toArray`                           | `max¹`                           |                  |
| `findFirst¹`           | `toList`                            | `min¹`                           |                  |
| `noneMatch`            |                                     | `sum²`                           |                  |
|                        |                                     | `summaryStatistics²`             |                  |

- What is a reduction operation?
  - A special type of terminal operation
  - Stream elements are processed to produce a single output
  - This can be a primitive type, like a long in the case of the count operation
  - The result can be a reference type, like Optional or one of the statistical types
  - It can also be any type chose, such as an Array, a List or some other type

- Aggregation terminal operations
  - Uses terminal operations to return information about the aggregated data set

TODO Insert table here

# Matching elements in a stream based on a condition
- There are three terminal operations that let you get an overall sense of what your stream elements contine, based on some specified condition
- These all return a boolean, and take a Predicate as an argument
- Can be though of as ways to ask true or false questions about the data set, the stream or as a whole

------ Insert table here

# Additional Terminal (Reduction) Operations
Reduction operations combine the contents of a stream to return a value, or they can return a collection
----INSERT TABLE 

Collectors

---- Insert table on collectors

The collect method
  - The collect method has two overloaded versions
  - The first can be used by passing it the result of one of the many factory methods on the Collectors class
  - The second is more complex, but offers more flexibility
  
-----> INSERT TABLE

# The optional type
- A generic class whose purpose is to be a container for a value which may or may not be null
- Was created by Java designers to address the problem of NullPointerException, a very common error in Java
  - This type is primarily intended for use as a method return type under specific conditions
- Optional tries to solve the problem of when no result, or no data is a perfectly valid situation vs when no result may actually be an error
  - Optional is a way of indicating that a value may not be present and can there be safely ignored during processing
- Optional is just another generic class, so it can be declared like any other type with type arguments
  - You don't construct an optional
  ***INSERT TABLE of static factor methods for optional

# The downside of optional
- Wrapping elements in an Optional will consume more memory and has the possibility of slowing down execution.
- Wrapping elements in Optional adds complexity and reduces the readability of your code.
- Optional is not serializable
- **Using optional for fields or method parameters is not recommended**

# Terminal operations that return optional
---- INSERT TABLE of terminal operations all of which return optional

# The flatMap operation
- The flatMap intermediate operation performance one-to-may transformations on elements of a stream pipeline
- It's called flatMap because it flattens result from a hierarchical collection, into one of uniformly typed elements
- The difference is in the return type of the function
- For `map` you return a different instance of an object
  - In this case you are exchanging one type for another, for each element on the stream
  - For flatMap, you return a stream which mean you're exchanging one element for a stream of elements back
