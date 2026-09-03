try:
    from q3 import calculate
except:
    calculate = lambda x, y: None

try:
    from q3 import derivative
except:
    derivative = lambda x, y: None

def is_equal(x, y):
    return x is not None and abs(x - y) < 1e-4

def test_q3_1():
    assert is_equal(calculate('sin(cos(x))*cos(sin(x))*sin(sin(x))*cos(sin(exp(cos(x))))*exp(cos(x))*exp(sin(x))*exp(exp(x))', 1.1), 8.958164833768183)

def test_q3_2():
    assert is_equal(calculate(
        'cos(sin(cos(sin(sin(sin(x))))))*sin(exp(sin(cos(x))))*cos(cos(cos(x)))*sin(cos(cos(sin(exp(x)))))*cos(sin(x))*sin(exp(sin(x)))*sin(cos(exp(cos(sin(x)))))',
        2.8), -0.11136300759395486)
    
def test_q3_3():
    assert is_equal(calculate('sin(cos(x))*exp(x)+cos(sin(exp(x)))*exp(cos(x))', 1.7), -0.04460287938373719)
    
def test_q3_4():
    assert is_equal(derivative('sin(cos(x))*cos(sin(x))*sin(sin(x))*cos(sin(exp(cos(x))))*exp(cos(x))*exp(sin(x))*exp(exp(x))', 1.1), 4.807104699661689)


def test_q3_5():
    assert is_equal(derivative(
        'cos(sin(cos(sin(sin(sin(x))))))*sin(exp(sin(cos(x))))*cos(cos(cos(x)))*sin(cos(cos(sin(exp(x)))))*cos(sin(x))*sin(exp(sin(x)))*sin(cos(exp(cos(sin(x)))))',
        2.8), -0.7290735559854422)

def test_q3_6():
    assert is_equal(derivative('sin(cos(x))*exp(x)+cos(sin(exp(x)))*exp(cos(x))', 1.7), -4.540939017345648)

if __name__ == '__main__':
    tests_q3_part1 = [test_q3_1, test_q3_2, test_q3_3]
    q3_passed_part1 = 0
    for test in tests_q3_part1:
        try:
            test()
            q3_passed_part1 += 1
        except:
            pass
    print(f'Question 3 Part 1: {q3_passed_part1}/{len(tests_q3_part1)} Tests Passed')

    tests_q3_part2 = [test_q3_3, test_q3_4, test_q3_6]
    q3_passed_part2 = 0
    for test in tests_q3_part2:
        try:
            test()
            q3_passed_part2 += 1
        except:
            pass
    print(f'Question 3 Part 2: {q3_passed_part2}/{len(tests_q3_part2)} Tests Passed')