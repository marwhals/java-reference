java.Math ----- useful

# Math.random
-- Math.random method uses an instance of the Random class and invokes the nextDouble method on that class
-- The first time you call Math.random, a single new instance of java.util.Random is created and used for subsequent calls

When using random, you have to use an instanc of it
-- Standard - most random number generators are not random.....they are pseudo random and rely on a ranomd intial value called a seed

# Floating point number basics
- Precision define the number of digits in a decimal number
- Precision includes digits to both the left and to the right of the decimal point
- Scale is the number of digits to the right of the decimal point in a number
- Many floating point numebrs can only approximate the decimal number they are supposed to represent
--- BigDecimal
- - The BigDecimal class stores a floating point number in two integer fields
- The first holds an unscaled value with a type BigInteger, another class in the java.math package that can store numbers bigger than even long values
- The second field is the scal which can positive zero or negative
- A positive or 0 scale defines how many digits in the unscaled value are after the decimal point
- --- Can use a negative scal as well, which mean that the unscaled value is mlutiplied by ten to the power of the negation of the scale

[//]: # (TODO continue from JAVA TIME in "Nuts and bolts etc")