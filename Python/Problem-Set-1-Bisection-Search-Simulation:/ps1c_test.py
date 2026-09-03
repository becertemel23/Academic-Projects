import unittest
from ps1c import find_minimum_wc_growth_rate

class TestPartC(unittest.TestCase):

    def test_acceptable_growth_rate_found(self):
        result = find_minimum_wc_growth_rate(initial_bacteria=10000, initial_white_cells=500, bacteria_growth_rate=0.1, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(result, "Expected an acceptable white cell production rate.")
        bacteria, white_cells, white_cell_growth_rate = result
        self.assertTrue(bacteria - 800 <= white_cells <= bacteria + 800, "White cells should be within acceptable range of bacteria.")

    def test_bacteria_growth_outpaces_white_cells(self):
            result = find_minimum_wc_growth_rate(initial_bacteria=10000, initial_white_cells=50, bacteria_growth_rate=0.8, bacteria_death_rate=0.1, white_cell_death_rate=0.2)
            self.assertIsNone(result, "Expected 'None' return when white cells cannot match bacterial growth.")
    
    def test_exact_result(self):
        result = find_minimum_wc_growth_rate(initial_bacteria=65000, initial_white_cells=40000, bacteria_growth_rate=0.1, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(result, "Expected an acceptable white cell production rate.")
        bacteria, white_cells, white_cell_growth_rate = result
        self.assertTrue(bacteria - 800 <= white_cells <= bacteria + 800, "White cells should be within acceptable range of bacteria.")
        self.assertAlmostEqual(white_cell_growth_rate, 0.14507446289062498, delta=1e-2)

    
    def test_no_bacteria_reproduction_result(self):
        result = find_minimum_wc_growth_rate(initial_bacteria=60000, initial_white_cells=60000, bacteria_growth_rate=0.0, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(result, "Expected an acceptable white cell production rate.")
        bacteria, white_cells, white_cell_growth_rate = result
        self.assertTrue(bacteria - 800 <= white_cells <= bacteria + 800, "White cells should be within acceptable range of bacteria.")
        self.assertAlmostEqual(white_cell_growth_rate, 0.0, delta=1e-2)

    
    def test_low_initial_white_cells_with_slow_bacterial_growth(self):
        result = find_minimum_wc_growth_rate(initial_bacteria=20000, initial_white_cells=10000, bacteria_growth_rate=0.05, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(result, "Expected an acceptable white cell production rate for low bacterial growth.")
        bacteria, white_cells, white_cell_growth_rate = result
        self.assertTrue(bacteria - 800 <= white_cells <= bacteria + 800, "White cells should be within acceptable range of bacteria.")

    def test_high_initial_bacteria_count(self):
        result = find_minimum_wc_growth_rate(initial_bacteria=50000000, initial_white_cells=3, bacteria_growth_rate=0.2, bacteria_death_rate=0.2, white_cell_death_rate=0.3)
        self.assertIsNone(result, "Expected 'None' return when initial bacteria count is significantly higher than white cells.")

    def test_initial_white_cells_greater_than_bacteria(self):
        result = find_minimum_wc_growth_rate(initial_bacteria=30000, initial_white_cells=60000, bacteria_growth_rate=0.1, bacteria_death_rate=0.1, white_cell_death_rate=0.1)
        self.assertIsNotNone(result, "Expected an acceptable white cell production rate when initial white cells outnumber bacteria.")
        bacteria, white_cells, white_cell_growth_rate = result
        self.assertAlmostEqual(white_cell_growth_rate, 0.0, delta=1e-2)
if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestPartC)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
