[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/ccnblaS4)
# COMP100 - Problem Set 4 ([Recursion](https://www.google.com/search?q=recursion))
## Deadline Friday, May 16 at 7:00 PM

Please Read The Instructions Carefully.

# Question 1.
Imagine you're a treasure hunter who stumbled upon a hidden trove of sparkling gems.  Each gem has a unique value, and you want to collect gems such that their values add up to a desired total value. Your goal is to find out how many different combinations of gems you can collect to reach a specific target value. Note that you **must not exceed** the target value. Also,  the order in which gems are collected does not matter.

For example, let's say that there are five gems with values of 3000, 4000, 6000, 7000, and 9000 ₺. And that your target is 13000 ₺. Then, there are 3 different combinations of gems you can collect to reach exactly 13000 ₺:
- take the 1st, 2nd and 3rd gems (3000 + 4000 + 6000 = 13000)
- take the 2nd and 5th gems (4000 + 9000 = 13000)
- take the 3rd and 4th gems (6000 + 7000 = 13000)
 

Implement a **RECURSIVE** function called `solve_gems(values, target)`, which takes the 2 parameters. `values` is a list of unique integers containing the values of the gems. `target` is an integer, representing the target value that you want to achieve. Your function should return the number of different combinations of gems you can collect to reach the target. 

Note that:
- The order of collecting gems does not matter. 
- Gems and targets can have negative values.

### Examples
- `solve_gems([3000, 4000, 6000, 7000, 9000], 13000)` should return `3`.
- `solve_gems([3000, 4000, 6000, 7000, 9000], 10000)` should return `2`.
- `solve_gems([500, -800, -1100], -800)` should return `1`.
- `solve_gems([1000, -1000], 0)` should return `2` (collect both gems, or collect neither). 



### Hint:
As for most recursion problems, we need to figure out 1) how to solve an instance of the problem by breaking it into smaller problems and 2) how to solve the base cases.
 

For a general problem

<img src=imgs/q1.jpg height=160>

There are two possibilities:

1. We  **WILL NOT** collect the first gem.
    - This is a smaller instance of the original problem, where we have one less gem. Recursively solve this instance.

  <img src=imgs/q2.jpg height=160>
      
2. We **WILL** collect the first gem.
   - This can easily be transformed into a smaller instance of the original problem, where we again have one less gem. We are going to collect the first gem in this case. Therefore, our target will be reduced by the value of the first gem. Recursively solve the problem for the remaining gems with this new target. (Consider any collection of gems that includes the first gem, and their values add up to the original target value. If we remove the first gem, then values of the remaining gems will add up to this new target value and vice versa. Therefore, the number of combinations for the original problem that includes the first gem, is equal to the solution to this transformed problem.) 

  <img src=imgs/q4.jpg height=160>

  
The two possibilities above will lead to two answers. The first case will give us the number of combinations that do not include the first gem. The second case will give us the number of combinations that include the first gem. What is the final number of combinations?

However, we are not done yet, as we have not defined our base cases. Remember that base cases should be a small instance of the problem for which we can determine the solution straightforwardly. If we do not define our base cases, then our function will keep calling itself forever.

We are solving this problem by recursively breaking it into smaller instances of the problem with one less gem. Therefore, by removing gems, then we may finally encounter a case where there are ... gems left. What is the solution in that base case? (Does your answer depend on any other parameter?)


### Notes
- Your **MUST** use **RECURSION** in your function.
- You **MUST NOT** use global variables.
- You **MUST NOT** use any libraries.

***

# Question 2.
In this question, your task is to find all the possible permutations of a given string. 

A permutation is simply a name for a reordering. So, the permutations of the string
`"abc"` are `"abc"`, `"acb"`, `"bac"`, `"bca"`, `"cab"`, and `"cba"`.

Note that a string of length `n` has `n!` permutations.

Your task is to write a  function `generate_permutations(letter_sequence)` that takes a string as input and returns a list of all its permutations.

* Assume that there are **no duplicate letters** in the input string.
* The order of the strings in the returned list **DOES NOT** matter. For example, for the input `'ab'` you can return either `['ab', 'ba']` or `['ba', 'ab']`.

### Examples
```
# The outputs shown here are just one of the possible solutions for a given input, because the order of the elements in your list might be different.

generate_permutations('') -> ['']
generate_permutations('g') -> ['g']
generate_permutations('cd') -> ['cd', 'dc']
generate_permutations('abc') -> ['abc', 'acb', 'bac', 'bca', 'cab', 'cba']
```

### Hint:
After finding an appropriate base case, you can solve an instance of a problem by solving smaller instances of the problem. Here are two different ways that you can think about the problem recursively. You can use either of them:
1. Take a letter out and generate the permutations for the remaining smaller string. Then, find a way to use that output to generate the solution for the original problem.
2. Generate all the permutations that start with a specific letter (How?). If you do this for all letters, then you have basically generated the solution for the original problem.

  
- Your **MUST** use **RECURSION** in your function.
- You **MUST NOT** use global variables.
- You **MUST NOT** use any libraries.

***

# Question 3.
In this question, we will be given a function $F(x)$, and we want to calculate the value of this function $F$ and its derivative at a given $x$. 
For example: $F(x) = \sin(\cos(x))\times \sin(x) \times \exp(x)$.

Remember that $\exp(x)$ simply means $e^x$.

Assume that $F$ is always a sum or multiplication of one or more functions:

$$ F(x) = f_1(x) + f_2(x) + ... + f_n(x) \quad \text{(or)} \quad f_1(x) \times f_2(x) \times f_3(x) ... \times f_n(x)  $$

where each $f_i(x)$ is a composition of one or more functions:

$$ f_i(x) = g_1(g_2(g_3(...g_m(x))))$$

and each function $g_i(x)$ is either $\sin(x)$ or $\cos(x)$ or $\exp(x)$. 

**In other words, $F(x)$ is created only by chaining $\sin(x)$ and $\cos(x)$ and $\exp(x)$ functions and combining them with `+` or `*`.** 

For example, we can have:

$$F(x) = \sin(\sin(\cos(\exp(x)))) + \cos(\exp(\sin(x))) \times \exp(\sin(\cos(x))) $$


* The function is represented as a string, where `'sin'`, `'cos'` and `'exp'` strings are used to represent the corresponding functions.
* The multiplication sign is represented using `'*'`, and addition using `'+'`.
* There are **NO SPACES** anywhere in the string representation of $F$. For example, we can have `'sin(x)*cos(x)'` or `'sin(x)+cos(x)'`but not `'sin(x) * cos(x)'` 
* There is a pair of parenthesis corresponding to the input of each `sin` or `cos` or `exp` function, and there are **no extra parentheses** anywhere in the string. For example, we cnnot have `'sin(x)*(sin(exp(x)))'` because the second term has redundant parenthesis. Instead we can have `'sin(x)*sin(exp(x))'`

The above function will be represented as the following string: `'sin(sin(cos(exp(x))))+cos(exp(sin(x)))*exp(sin(cos(x)))'`

In your implementation:

* You **ARE ALLOWED** to use the `math.sin()`, `math.cos()` and `math.exp()` functions.
* You **MUST NOT** use any other functions from the `math` library.
* You **MUST NOT** use any other libraries.
* You **MUST** implement each part using **RECURSION**.
* You **MUST NOT** use global variables.
* You **MUST NOT** use python's `eval()` or `exec()` functions.

*Hint:* You might find `str.index()` or `str.find()` or `str.rfind()` functions useful.

***

## Q3 Part 1:
Implement `calculate(F, x)`, where `F` is the string representing the function $F$ as described above, and `x` is a number in radians. `calculate(F, x)` should return $F(x)$, which is  the value of the function $F$ at point `x`. Your implementation must be **RECURSIVE**.


### Examples

- `calculate('sin(exp(x))', 0.7)` should return  `0.903488472004459`
- `calculate('sin(cos(x))*exp(sin(cos(sin(x))))*cos(exp(x))', 1.2)` should return `-0.6116323306863213`
- `calculate('sin(x)+cos(x)', 0.5)` should return `1.3570081004945758`

***

## Q3 Part 2:
Implement `derivative(F, x)`, where `F` is a string representing the function $F$ as described above, and `x` is a number in radians. `derivative(F, x)` should return $F'(x)$, which is  the value of the derivative of the function $F$ at point `x`. Your implementation must be **RECURSIVE**.

### Examples
- `derivative('sin(exp(x))', 0.7)` should return  `-0.8631193610376219`
- `derivative('sin(cos(x))*exp(sin(cos(sin(x))))*cos(exp(x))', 1.2)` should return `2.017408588854982`
- `derivative('sin(x)+cos(x)', 0.5)` should return `0.39815702328616975`

### In case you forgot
* $\sin'(x) = \cos(x)$ and $\cos'(x) = -\sin(x)$ and $\exp'(x) = \exp(x)$
* $f(g(x))' = f'(g(x)) \times g'(x) \rightarrow$  chain rule
* $(f(x) \times g(x))' = f'(x) * g(x) + f(x) * g'(x) \rightarrow$ product rule




