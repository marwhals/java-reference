# Arrays

- Can have arrays for any primitive type, like ints, doubles, booleans or any other primitive.
- Can also have arrays for any class.
- Indexed at 0

## Declaring an Array

- First specify the type of the elements you want in the array.
- Then you include square brackets in the declaration, which is the key for Java to identify the variable as an array.
- The square brackets can follow the type
- Can also put the square brackets after the variable name
  %Insert example
- First approach is much more common
- Note that you don't specify a size in the array declaration itself

## Instantiating an Array

- One way to instantiate the array is with the new keyword, similar to when creating instances of classes

## Instantiating an Array

%Insert Example

- Square brackets are required when using the new keyword and a size is specified between them. So, in this example
  there will be 10 elements in the array.
- An array instantiation doesn't have a set of parentheses, meaning we can't pass data to a constrcutor for an array.
- Using parentheses with an array instantiation give yoiu a compiler error

## An Array is NOT Resizable

- The size of an array, once created, is fixed
- In this case, integerArray will have 10 elements
  %Insert example
- You can't add or delete elements.

## An array initializer

- Make the job instantiating an array easier
- `int[] firstFivePositives = new int[]{1,2,3,4,5};`
- Because these values are specified, the length of the array can be determined by Java so the size of the array doesn't
  need to specified in square brackets

## The array initializer as an anonymous array

- Java allow us to drop the new int[] with brackets from the expression
- This is known as an anonymous array
- `int[] firstFivePositives = {1,2,3,4,5};`
- An anonymous array initializer can only be used in a declaration statement

## What is an array

- An array is a special class in Java, like any class it ultimately inherits from java.lang.Object

### Array initialization and default element values

- When an array initializer is not used, all array elements get initiliased to the default value for that type
- For primitive types- zero for numeric types, boolean the default value will be false, class types will be `NULL`

### The Enhanced For Loop, the For Each Loop

- Loop was designed to walk through all the elements in an array or other collection types
- It processes one element at a time, from the first element to the last.
- Enhanced for loop

 ```java
    for(declaration :collection){
        // statements
        }
 ```

- Basic for loop

 ```java
    for(init;expression;increment){
        // statements
        }
 ```

- The enhanced for loop only has two components, versus three defined in the parentheses after the for keyword
- Enhanced for Loop
    - The first part is a declaration expression which includes the type and a variable name.
    - Usually a local variable with the same type as an element in the array
    - And the second component is the array or some other collection variable

### java.util.Array

- Java's array type is basic and has very little built in functionality
- Has single property or field, named length
- It inherits java.util.Objects functionality
- Java provides a helper class named java.util.Array, providing the common functionlaity you would want for many
  operations
- These are static methods on Array, they are class methods, not instance methods.

### Printing elemts in an array using Arrays.toString()

- The toString method in this helper class prints out all the array elements, comma delimited and within square brakets

### Why use arrays?

- Used to manage many items of the same type
- Some common behaviours for arrays include sorting, initialising values, copying the array and finding an element

## Matching elements in an array

- Searching sequentially - Linear Search, stepping through the elements one after another, if the elements are sorted
  then this type of search is unnecessarily inefficient
- Using intervals to Search - Split each section up, testing the values at the boudaries and based on that split again
  into smaller sections narrowing the number of elements to test each time
    - This type of searching is called interval searching
        - One of the most common ones is binary search. Intervals are continuosly split into two

## Array.binarySearch

- The static method, binarySearch is in the Arrays class
- We can use this method to test if a value is already in our array, but there are important things to remember
    - First the array has to be sorted
    - Second, if there are duplicate values in the array there no guarantee on which on it will match on
    - Finally, elements must be comparable. Trying to compare instances of different types will lead to errors and
      invalid result
- *notes on method match*
    - The position oif a match, if found
    - It returns a -1 when no match was found
    - It's important to remember that a positive number may not be the position of the first match
    - If you array has duplicate values and you need to find the first element, other methods should be used.

## Refernce Types vs Value Types

- When an object is assigned to a variable, that variable becomes a reference to that object.
    - This is also try of arrays
        - Additionally, every array element is also a reference
          *This needs to be considered when two variable are references to the same array object*

## Variable arguments (varargs)

```java
    public static void main(String[] args) {

}
```

**alternative**

```java
public static void main(String... args) {
    // This method will take zero or more String arguments and create an array to process these arguments
}
```

There can only be one variable argument in a method and it must be the last argument

# Java's nested Arrays

- An array element can actually itself be an array. *nested arrays*
- A two-dimensional array can be through of as a table or matrix of values with rows and columns.
    - An array initializer can be used for this
    - Two square brackets for the declaration

```java                                                           
int[][] array = {
        {1, 2, 3},
        {11, 21, 31},
        {12, 22, 32},
        {13, 23, 33}
};
```                                                               

```java
int[][] array = {{1, 2, 3}, {451, 62, 453}, {651, 223, 343}, {14, 25, 33}};
```

Two-dimensional Array doesn't have to be a uniform matrix
```java
int[][] array = {
        {1,2},
        {13,245,123},
        {1,2,3,4,5,6,7,8},
};
```
### Declaring the size of a 2D array
```java
int[][] array = new int[3][3];

int[][] array = new int[3][]; //Outer array specified only

int[] number_array[];
```

## Accessing elements in a muli-dimendional arrays
```java
array[0] = 50; // set this elmeent to 50

array[0][0] = 60; // first elmeent in first array to 50

array[1][1] = 10; // set to 10
```

### Multi dimensional array
```java
Object[] multiArray = new object[3];
multiArray[0] = new Dog[3];
multiArray[1] = new Dog[3][];
multiArray[2] = new Dog[3][][];
```