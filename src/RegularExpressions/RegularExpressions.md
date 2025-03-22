# Regular Expressions Intoduction

- Useful for parsing log files, extracting or searching for specific types of information

# What is a regular expression
- Simply text
- May contain characters or character combinations that have special meaning
- These are called metacharacters
- These combinations are interpreted by a regular express pattern processor

---> most patterns can be googled and already exist

[//]: # (INSERT TABLE HERE)

Uses
- Verfify something is formatted correctly
- Find occurences of patterns of text
- Replace matching occurences of patterns in text
- Extract matching ocurrences from text
- Split your text by a pattern

# Ways to use Refular Expressions in Java
- Classes that take regualar expressions string or patterns as paramenters
- - String, Scanner, Formatter, DateTimeFormatter, Duration
- Special classes that allow you to implement your own
- -- Pattern, Matcher
# String's methods which use regular expressions
- They can all be used with a String literal that dosn;t have any of the special character sequences
- --- Can be very useful when you pass refular expressions pattern to them

[//]: # (INSERT TABLE here 

# The parts of a regular expression
A regular expression can be made up of combinations of the following
- Literals - These have no aditional meaning and are a one to one match. If you specify the literal "abc", the code will match on the first occurence of "abc", in you string
- Character Classes - Some of these are predefined, other syou can define yourself. The period is an example of a predefined character class
- Quantifiers - These methacharacters identify the number of ocurrences of a character class or literal, requried to make a match.
- Boundary Matchers, or achhors. These specify thes position in the text. For example at the start or at the end
- Groups. These identify and allow for the capturing of subexpressions

[//]: # (TODO insrert table here)

- Charactesd in square barackers may have a different meaningi

[//]: # (TODO INSERT TABLE)

Six different quantifiers that can be used in a regular expression
[//]: # (TODO INSERT TABLE)

There are three common boundary matchers or anchors

[//]: # (INSERT TABLE)

# What does it mean to compiler a refular expression
- The string containing the expression is passed to the compile methocd of a Pattern class, which retuns a pattern isntance
- This string, the regular expression, is said to be compiled into a Pattern by Java,s refular expression processior
- This compilation process consists of
- ---- Check the string for syntactical correctness
- ---- Building an internal representation consisting of a decision tree made up of nodes and boolean decision points, deireved from the various parts of the refular expression
- ---- Optimising the pattern, by simplifying the expression and eliminateing redundancies. This process increases the efficienty of matching the expression to chracters srequencets

# Advantages of the Pattern Instance
- By compiling refular expression into Pattern objects, you benegit from improved performance and code efficiency, on subsequent matching
- The compiled pattern can be reused across multiple matchcing operations, saving computational resourcess, and reducting processing tiem.

# Matcher class advantages
- In aditional to matching on the entire intpu. Matcher offeres two other operations for partial matching
- ---- These methods are lookingAt and overloaded versions of the fine method
- ---- Matching supports capturign grouips and granting access to the text within the group
- ---- Matcher can be reused fro multople match operations on different String inputs, meaning the pattern doesn;t have to be recompiled

# Matcher calss disadvantages
- ---- An instance of a Matcher class has state, which changes as operations are performed on it
- ---- This means the Matcher class is not thread safe
- ---- Is also means ate may need to be result, before a new String is evaluated

# Greedy and Relecutabnt Regular Expression
- Greedy regular expression match as many characters as possible
- Teh expression .*,  which is a greedy expresssion, matches any number of characters, including the empty string
- 
- Reluctant refular experssion, on the other hand, match as few characters as possible from the input text
- The regular experssion .*? matches any number of characters, but stops at th eearliest succesful point, where the overall pattern is matched.
- The default type of refular expression is greedy
- You can use the ? to be a quantifier modifier, making the refular expression reluctant

# Matchers matching methods
All return a boolean if the refular expression was matched in some way to the string

[//]: # (TODO table here)

# Grouping and Capturing
- Grouping is a way to iedntify targetsd parts of a regular expressoin match, often because you want to do some additional processing on that sub expression. A group is enclosed in parenthese.
- A match to the group is said to be captured, meaning the tect is stored for additional usage later.

- Capturing is away to access the matched gruops, called capturing groups, of a refular expression match
- There are also named back references
- - The matcher class has methods that let us use bother numeric and named references to acces the captured groups

# Matcher and MatchResult
- The matcher class  implements MatchResult
- - Matcher though support the overloaded version of these methods which allow you to retieve a captureing group bny its name
- When you call the result method, you'll also get a stream of instance, types that also implement MatchResult
- This calss is declared on Matcher as a static nesterd class

# Back references
- You can use back references to refer to the text captured by these groups,. in refular expression and also in the replace methods on maetcher
- In a refular expression, a back reference is identified by a backslach and a number, which is the capturing group index
- In a replace String in Java though a back reference starts with a $, then a number, again the grouping index
- You can also use a capturing group names in back references, replacing the index with the named group

# Alternative Character Classes

[//]: # (TODO insert table)