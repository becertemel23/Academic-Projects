try:
    from q1 import solve_gems
except:
    solve_gems = lambda x, y: None

def test_q1_1():
    assert solve_gems([6000, 8000, 9000, 10000, 13000, 14000, 15000], 23000) == 4
    assert solve_gems([6000, 8000, 9000, 10000, 13000, 14000, 15000], 24000) == 3

def test_q1_2():
    assert solve_gems([7,3,6,-4,11,-10,2,9], 0) == 4
    assert solve_gems([7,3,6,-4,11,-10,2,9], 1) == 6
    assert solve_gems([7,3,6,-4,11,-10,2,9], 2) == 7
    assert solve_gems([7,3,6,-4,11,-10,2,9], 3) == 6
    assert solve_gems([7,3,6,-4,11,-10,2,9], 4) == 7
    assert solve_gems([7,3,6,-4,11,-10,2,9], 5) == 8
    assert solve_gems([7,3,6,-4,11,-10,2,9], 6) == 9
    assert solve_gems([7,3,6,-4,11,-10,2,9], 7) == 8
    assert solve_gems([7,3,6,-4,11,-10,2,9], 8) == 10
    assert solve_gems([7,3,6,-4,11,-10,2,9], 9) == 9
    assert solve_gems([7,3,6,-4,11,-10,2,9], 10) == 9
    assert solve_gems([7,3,6,-4,11,-10,2,9], 12) == 10

if __name__ == '__main__':
    tests_q1 = [test_q1_1, test_q1_2]
    q1_passed = 0
    for test in tests_q1:
        try:
            test()
            q1_passed += 1
        except:
            pass
    print(f'Question 1: {q1_passed}/{len(tests_q1)} Tests Passed')
