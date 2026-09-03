import unittest
from ps1b import wc_count_with_bacterial_growth

class TestPartB(unittest.TestCase):

    def test_sufficient_growth_rate(self):
        days = wc_count_with_bacterial_growth(initial_bacteria=10000, initial_white_cells=5000, white_cell_growth_rate=0.3, bacteria_growth_rate=0.1, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(days, "Expected a number of days for recovery.")
        self.assertGreater(days, 0, "Expected recovery in a positive number of days.")

    def test_exact_number_of_days(self):
        days = wc_count_with_bacterial_growth(initial_bacteria=75000, initial_white_cells=30000, white_cell_growth_rate=0.3, bacteria_growth_rate=0.1, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertEqual(days,7, "Expected a number of days for recovery.")

    def test_insufficient_white_cell_growth(self):
            result = wc_count_with_bacterial_growth(initial_bacteria=10000, initial_white_cells=5000, white_cell_growth_rate=0.1, bacteria_growth_rate=0.2, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
            self.assertIsNone(result, "Expected 'None' return for low white cell growth rate.")

    def test_recovery_with_high_bacteria_count(self):
        days = wc_count_with_bacterial_growth(initial_bacteria=50000, initial_white_cells=3000, white_cell_growth_rate=0.25, bacteria_growth_rate=0.1, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(days, "Expected recovery even with high initial bacterial count.")
        self.assertGreater(days, 0, "Expected a positive number of days for recovery.")

    def test_strong_initial_white_cells(self):
        days = wc_count_with_bacterial_growth(initial_bacteria=1000, initial_white_cells=1500, white_cell_growth_rate=0.2, bacteria_growth_rate=0.3, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertEqual(days, 0, "Expected 0 days as white cells are initially stronger.")

    def test_max_days_limit_without_recovery(self):
            result = wc_count_with_bacterial_growth(initial_bacteria=500000, initial_white_cells=100, white_cell_growth_rate=0.1, bacteria_growth_rate=0.02, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
            self.assertIsNone(result, "Expected 'None' return when max days limit is reached without recovery.")

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestPartB)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
