# COMP100 2025S Problem Set 1: Guess-Check, Approximation, Bisection - Saving the Patient

**Deadline:** 26 March 2025, Wednesday 11:59 PM

This problem set focuses on Python control flow and introduces Bisection Search. You will tackle four problems. For each part, save your code as `ps1{name of the part}`. For example, Part A should be `ps1a.py`.

## Introduction
A doctor rushes into the hospital emergency room. "We have a critical patient with a worsening infection!" they exclaim. The patient is battling a bacterial infection that’s aggressively spreading. The patient’s immune system, specifically white blood cells produced by the bone marrow, is mounting a defense, but it may not be sufficient. Doctors turn to you to assist in managing the patient’s treatment and determining the best course of action.

Your task is to calculate the survival chances by regulating white blood cell production, factoring in bacterial growth, and strategically using medication to assist the immune response.

## Parts
- **Early Battle**
- **Escalating Threat**
- **Life-or-Death Decision**

## PART A: Early Battle (20 Points)
The patient's immune system begins its fight. The number of bacteria changes daily due to both bacterial death and the white blood cells steadily produced by the bone marrow. Your task is to calculate how many days it will take for the white blood cell count to match or surpass the bacterial count.

- Let `initial_bacteria` represent the starting bacterial count.
- Let `initial_white_cells` represent the starting white blood cell count.
- Let `white_cell_growth_rate` represent the daily growth rate of white blood cells. (e.g., if `white_cell_growth_rate` is `0.08` and there are 1200 white blood cells today, there will be 1296 cells tomorrow).
- Let `bacteria_death_rate` represent the proportion of bacteria eliminated per white blood cell daily.
- IMPORTANT: The added cells should be converted to an integer (rounded down) using the `int()` method to avoid fractional values. Whenever you perform an operation using a float, round the result down to an integer. 

Each day:
1. Compute the new white blood cell count using `white_cell_growth_rate`.
2. Compute the new bacterial count by decreasing it with `bacteria_death_rate * white_cell_count_at_the_end_of_day`.

Your program should determine how many days it takes for the white blood cell count to equal or exceed the bacterial count and return `days` (int).
- **If the white blood cells cannot outnumber the bacteria within 85 days (i.e., up to and including the 85th day), return None.**

### Sample Runs:
- `wc_count_no_bacterial_growth(initial_bacteria= 85000, initial_white_cells= 32000, white_cell_growth_rate= 0.06, bacteria_death_rate= 0.06)`  Output: `11`

- `wc_count_no_bacterial_growth(60000, 17000, 0.09, 0.002)`  Output: `15`

- `wc_count_no_bacterial_growth(110000, 150, white_cell_growth_rate= 0.025, bacteria_death_rate= 0.0005)`  Output: `None`

## PART B: Escalating Threat (30 points)
In reality, bacteria also reproduce daily. Now, both the white blood cells and bacteria grow over time, and white blood cells also die off as part of their natural life cycle.

- Let `initial_bacteria` represent the starting bacterial count.
- Let `initial_white_cells` represent the starting white blood cell count.
- Let `white_cell_growth_rate` represent the daily increase rate of white blood cells.
- Let `bacteria_growth_rate` represent the daily increase rate of bacteria.
- Let `bacteria_death_rate` represent the proportion of bacteria eliminated per white blood cell daily.
- Let `white_cell_death_rate` represent the proportion of white blood cells that die naturally per day.
- IMPORTANT: The added cells should be converted to an integer (rounded down) using the `int()` method to avoid fractional values. Whenever you perform an operation using a float, round the result down to an integer. 

Each day:
1. Compute new white blood cell and bacteria counts independently using their respective growth rates.
2. Reduce bacteria by `bacteria_death_rate * white_cell_count_at_the_end_of_day`.
3. Reduce white blood cells by `white_cell_death_rate * white_cell_count_at_the_end_of_day`.

Your program should determine how many days it will take for the white blood cell count to reach or exceed the bacterial count and return `days` (int).
- **If the white blood cells fail to outnumber the bacteria within 85 days (i.e., up to and including the 85th day), return None.**

### Sample Runs:
- `wc_count_with_bacterial_growth(initial_bacteria= 60000, initial_white_cells= 17000, white_cell_growth_rate= 0.12, bacteria_growth_rate= 0.035, bacteria_death_rate= 0.002, white_cell_death_rate= 0.01)` Output: `18`

- `wc_count_with_bacterial_growth(130000, 65000, 0.28, 0.12, 0.003, 0.015)` Output: `6`

- `wc_count_with_bacterial_growth(160000, 80, 0.25, 0.16, 0.002, 0.02)` Output: `None`

## PART C: Life-or-Death Decision (50 points)
The infection reaches a critical stage, and it becomes evident that the natural immune response alone won’t be sufficient in the necessary time frame. The doctors estimate the patient has only **28 days** before succumbing to the infection. To save the patient, you must determine the minimum `white_cell_growth_rate` required to combat the bacteria within this window, while ensuring the patient does not receive excessive medication.

Your code should contain three functions: `simulate_growth` and `wc_count_is_acceptable` are helper functions for your main function `find_minimum_wc_growth_rate`.
- `simulate_growth` function simulates bacterial and white cell growth over **28 days**.
- `wc_count_is_acceptable` function verifies if the final white cell count is within an acceptable range relative to the bacteria count.

* While matching the bacterial count, aim for a tolerance of **800 white blood cells**, i.e., if there are `x` bacteria, anything in the range `[x-800, x+800]` is acceptable.
* The maximum permissible `white_cell_growth_rate` through drug treatment is **0.95**.
- IMPORTANT: The added cells should be converted to an integer (rounded down) using the `int()` method to avoid fractional values. Whenever you perform an operation using a float, round the result down to an integer. 

### Problem Setup
- If the white blood cell count can match or slightly exceed the bacteria count (within 800 cells), the treatment is considered successful.
- The `white_cell_growth_rate` can be adjusted up to **0.95**, but if no dose that is effective in 28 days has been found within 400 iterations, return `None`.

---


`simulate_growth` function should take:
- Initial bacterial count (`initial_bacteria`)
- Initial white blood cell count (`initial_white_cells`)
- Daily white blood cell growth rate (`white_cell_growth_rate`)
- Daily bacterial growth rate (`bacteria_growth_rate`)
- Daily bacterial death rate (`bacteria_death_rate`)
- Daily white blood cell death rate (`white_cell_death_rate`)
- Simulation duration (`days=28`, optional)

It should simulate the growth over 28 days and return:
- Final bacterial count (`final_bacteria`)
- Final white blood cell count (`final_white_cells`)

---

`wc_count_is_acceptable` function should take:
- Bacterial count (`final_bacteria`)
- White blood cell count (`final_white_cells`)

It should return `True` or `False` based on whether the white cell count is within the acceptable range.

---

Your main function `find_minimum_wc_growth_rate` should take:
- Initial bacterial count (`initial_bacteria`)
- Initial white blood cell count (`initial_white_cells`)
- Daily bacterial growth rate (`bacteria_growth_rate`)
- Daily bacterial death rate (`bacteria_death_rate`)
- Daily white blood cell death rate (`white_cell_death_rate`)

It should return a three-element tuple:
- Final bacterial count (`int`)
- Final white blood cell count (`int`)
- Minimum effective `white_cell_growth_rate` (`float`)

If no dose that is effective in 28 days has been found within ***400 iterations*** return `None`.

IMPORTANT: Before running the 400 iterations, test to see if, by the end of the 28 days, the number of white blood cells exceeds the number of bacteria even when the white cell growth rate is 0. This case may occur when the initial white blood cell count is significantly higher than the bacteria count. In that case, simply return the final bacterial and white blood cell counts (after 28 days) and 0 (minimum effective growth rate). 

### Sample Runs:
- `find_minimum_wc_growth_rate(initial_bacteria = 95000, initial_white_cells= 45000, bacteria_growth_rate= 0.25, bacteria_death_rate=0.1, white_cell_death_rate=0.2)` Output: `(5898078, 5898148, 0.487767219543457)`

- `find_minimum_wc_growth_rate(140000, 4000, 0.28, 0.2, 0.1)` Output: `(39687845, 39688191, 0.543455743789673)`

- `find_minimum_wc_growth_rate(90000, 450, 0.75, 0.1, 0.1)` Output: `None`

- `find_minimum_wc_growth_rate(95000, 105000, 0.001, 0, 0)` Output: `(97684, 105000, 0)`
