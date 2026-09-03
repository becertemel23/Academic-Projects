import unittest

from q2b import get_codons


class TestGetCodons(unittest.TestCase):
    def test_1(self):
        self.assertEqual(get_codons("AUGGCUUAA"), ("AUG", "GCU", "UAA"))

    def test_2(self):
        self.assertEqual(get_codons("AUG"), ("AUG",))


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestGetCodons)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
