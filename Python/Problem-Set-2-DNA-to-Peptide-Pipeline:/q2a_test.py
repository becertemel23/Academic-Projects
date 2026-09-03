import unittest

from q2a import codon_to_amino


class TestCodonToAmino(unittest.TestCase):
    def test_1(self):
        self.assertEqual(codon_to_amino("AUG"), "M")

    def test_2(self):
        self.assertEqual(codon_to_amino("UAA"), "*")

    def test_3(self):
        self.assertEqual(codon_to_amino("GCU"), "A")


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestCodonToAmino)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
