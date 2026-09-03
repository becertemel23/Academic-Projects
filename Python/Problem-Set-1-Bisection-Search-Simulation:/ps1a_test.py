import unittest
from ps1a import wc_count_no_bacterial_growth

class TestPartA(unittest.TestCase):

    def test_sufficient_growth_rate(self):
        days = wc_count_no_bacterial_growth(initial_bacteria=1000, initial_white_cells=500, white_cell_growth_rate=0.5, bacteria_death_rate=0.4)
        self.assertEqual(days, 1, "Expected recovery in 1 day.")

    def test_insufficient_growth_rate(self):
            result = wc_count_no_bacterial_growth(initial_bacteria=10000, initial_white_cells=500, white_cell_growth_rate=0.001, bacteria_death_rate=0.2)
            self.assertIsNone(result, "Expected 'None' return for low growth rate.")

    def test_exact_threshold(self):
        days = wc_count_no_bacterial_growth(initial_bacteria=5000, initial_white_cells=100, white_cell_growth_rate=0.25, bacteria_death_rate=0.3)
        self.assertEqual(days, 14, "Expected recovery in 14 days at threshold rate.")

    def test_strong_initial_white_cells(self):
        days = wc_count_no_bacterial_growth(initial_bacteria=100, initial_white_cells=150, white_cell_growth_rate=0.3, bacteria_death_rate=0.4)
        self.assertEqual(days, 0, "Expected 0 days as white cells are initially stronger.")

    def test_max_days_limit(self):
            result = wc_count_no_bacterial_growth(initial_bacteria=50000, initial_white_cells=10, white_cell_growth_rate=0.1, bacteria_death_rate=0.04)
            self.assertIsNone(result, "Expected 'None' return for max days limit exceeded.")

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestPartA)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
