# Clout

Create a simple CLI that allows the user to:

1. Establish and describe an asymmetrical social graph with a series of one-line commands.
2. Determine the **extended** influence of any particular person in the graph (i.e. total count of followers and followers of followers etc of that person).

### Commands

1\. Add a relationship:

  - > [person a] follows [person b]

2\. Determine the influence of a person:

  - > clout [person]

3\. Determine the influence of all people in the graph:

  - > clout

### Rules
* A person can have an unlimited number of followers.
* A person can only follow one person.
* A person can change who they follow.
* A person may not follow her/himself.


### Example

```
$ ./clout

> Neymar follows Xavi

OK!

> clout Xavi

Xavi has 1 follower.

> Neymar follows Messi

OK!

> clout Xavi

Xavi has no followers.

> clout Messi

Messi has 1 follower.

> Messi follows Messi

Interesting, but that doesn't make sense.

> Pique follows Victor Valdes

OK!

> Jordi Alba follows Pique

OK!

> clout Victor Valdes

Victor Valdes has 2 followers.

> clout

Victor Valdes has 2 followers
Messi has 1 follower
Pique has 1 follower
Jordi Alba has no followers
Neymar has no followers
Xavi has no followers

> Messi follows Victor Valdes

OK!

> clout Victor Valdes

Victor Valdes has 4 followers
```

### Questions
As part of your assignment, please answer these questions:

1. How have you gained confidence in your code?
  * I got to use a few new things that I haven't used before, including the Files class and the Command pattern (without the Invoker layer).  It's also nice to write more functional style code which I'm relatively new to.  The greatest take-away from this assignment is to do more work with graphs.  I learned about the concept of a Directed Pseudoforest (https://en.wikipedia.org/wiki/Pseudoforest) which is very cool.
2. What are the performance characteristics of your implementation? Does it perform some operations faster than others? Explain any tradeoffs you made in architecting your solution.
  * Please see the Runtime Analysis section below.
3. One of the things we'll be evaluating is how your code is organized. Why did you choose the structure that you did? What principles were important to you as you organized this code?

| Concept | Reasoning |
| ------- | --------- |
| Person | I wanted to create this wrapping on a String to give some domain terminology in the code and to avoid the confusion involved with passing primitive Strings around. |
| Cycle and Non-Cycle Clout | After analyzing the nature of how the graph and components can evolve, these two emerging concepts seems to become more relevant and so I've adopted them in my abstraction of the problem.  They are defined in the "Approach" section. |
| Cycle | I wanted to support cycles in the graph and decided to model a concept of a "Cycle Clout" as the People that are in the cycle have a very interesting property of having the same Clout value. |
| User Interface | I wanted to both have a Command Line Interface and a way to pump in an input file and generate an output file so I wouldn't have to type every line out against a prompt. |
| Clout Service | I created this interface since there could be different approaches and internal representations of the clout graph.  This interface defines a contract which alternative implementations must adhere to. |
| Response | At first when I didn't have this concept, I printed the console output inside the Command layer.  I could have probably mocked something to assert on but decided to wrap the actual Strings in this concept so that I could more explicitly assert against them in my unit tests. |
| Command Pattern | I've never used this pattern before and though it would fit the problem as there are intuitively a Follow and a Clout command.  I also added an Exit command for a way to exit the Command Line Interface. I decided to not utilize a CommandInvoker abstraction which I found used in some examples of this pattern because it seemed like a redundant layer of abstraction. |
| Factory Pattern | Where appropriate I've used this pattern to decouple the construction knowledge and also to isolate that logic for unit testing, which has been omitted for the UserInterfaceFactory. |
| Optionals | I've used Optionals in some places, for example in the CloutCommand, because it was a clean way to differentiate between a Clout Command for a single Person versus everybody. |
| Integration Testing | I wanted to separate the unit testing from more complex scenarios which were integration-level testing. There are a number of interesting and important test cases relating to cycles that I wanted to ensure my code supported. In addition I also created a TestUtils class to encapsulate some repeatedly used entities and to create "known" graph components. |
| Code Generation | Although somewhat controversial, I've used Lombok in the past and it's worked great.  If the reader is not aware of it I would hope that they would agree that it does help to keep the code base concise. Although not being familiar with it can make reading the code harder. |

### Guidelines

* You can use whatever language you like.
* Do not use a database to store the graph. Store it in memory.
* You can use any libraries you want as long as they don't directly implement the solution. 
* Even though this application is simple, we're looking for a demonstration of design pattern knowledge, performance and code quality. Treat this as if it were "production-ready code".
* That said, don't go off the deep end. This is just a homework assignment. You should be able to finish it in an evening. Please don't work on it more than two evenings -- your time is more valuable than that.
* Submit your response as a zip file.
* Reach out if you have any questions and document where you have made assumptions!

# Assumptions:
* It's stated above, but that I would be able to hold the entire graph in memory.
* That cycles can exist, specifically, if "Anthony follows Steve" and "Steve follows Anthony" then each Person has a Clout of 1.
* Names are case sensitive.
* Whitespace surrounding and encapsulated by names are retained.
* Empty or null names are not supported.

# Running the Program:
There are two scripts available to run the program from inside the "scripts" folder:

| Script | Description |
| ------ | ----------- |
| clout | Standard CLI |
| clout-input-output-file | Allows you to specific the full file path for an input file and generates an inlined output file.  Samples can be found in the "input" and "output" folders.  An example usage would be ./clout-input-output-file /etsy/input/sample.in /etsy/output/sample.out |

# Approach:
  - I wanted my code to be as performant as possible and I was willing to use/tradeoff more memory for the internal representation of the graph details to optimize for better runtime.
  - I also wanted to support cycles since I found that to be the most interesting part of this problem.  In particular, the creation of cycles and the breaking of cycles are the non-trivial scenarios.
  - In term of structural awareness, my implementation of the CloutService will be aware of the following:
    1. The edges between each "follower" and who they are "following".
    2. All of the cycles that exist in the graph.
    3. All of the people that are part of a cycle.
    4. The Non-cycle Clout for every Person, where for a Person that is not part of a cycle, it is simply their Clout.  And for a Person that is in a cycle, it would be their Clout if the cycle did not exist.
  
# Runtime Analysis:
##Definitions:
  - N is the number of people in the entire Clout graph.
  - C is the number of people in a cycle if it's relevant.
  - A is the number of "ancestors" of a specific person, where if the entire graph represents "Anthony follows Steve" then Anthony has 1 ancestor and Steve has none.

##Breakdown:
  1. Removing an Edge
    - Edge removal currently requires a cycle detection pass which can take up to O(A) from the target time and space.  Please add this cost to the runtimes below.
    1. From a Tree Component
      - O(A) from the target
    2. From a Tree Component that is connected to a Cycle
      - Bounded by path from the target to the first node that is part of the cycle.
    3. From a Cycle itself
      - O(2C), as we are breaking the cycle we need to remove the "cycles" map entries as well as correct the non-cycle clout for each member of the former cycle.
  2. Adding an Edge
    1. To a different Tree Component
      - O(A) from the target
    2. To a different Tree Component that is connected to a Cycle
      - Bounded by path from the target to the first node that is part of the cycle.
    3. To the same Tree Component (creating a Cycle)
      - O(2C), as we are creating a cycle we did one pass that needs to be "corrected" by a second pass.

# Tags: Graphs, Directed Pseudoforests, Corrective Pass, Cycles

# Notes:
  - 