# Clout

## Problem
Create a simple CLI that allows the user to:

1. Establish and describe an asymmetrical social graph with a series of one-line commands.
2. Determine the **extended** influence of any particular person in the graph (ie total count of followers and followers of followers etc of that person).

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
As part of your assignment, please answer these quesitons:

1. How have you gained confidence in your code?
2. What are the performance characteristics of your implementation? Does it perform some operations faster than others? Explain any tradeoffs you made in architecting your solution.
3. One of the things we'll be evaluating is how your code is organized. Why did you choose the structure that you did? What principles were important to you as you organized this code?

### Guidelines

* You can use whatever language you like.
* Do not use a database to store the graph. Store it in memory.
* You can use any libraries you want as long as they don't directly implement the solution. 
* Even though this application is simple, we're looking for a demonstration of design pattern knowledge, performance and code quality. Treat this as if it were "production-ready code".
* That said, don't go off the deep end. This is just a homework assignment. You should be able to finish it in an evening. Please don't work on it more than two evenings -- your time is more valuable than that.
* Submit your response as a zip file.
* Reach out if you have any questions and document where you have made assumptions!

# Clarifications:
  - 

# Approach:
  - 

# Runtime Analysis:
##Definitions:
  - 

##Breakdown:
  - 

##Overall:
  - Space: 
  - Time: 

# Tags: 

# Notes:
  - 