import unittest

from q2c import translate_codons_to_amino_acids


class TestTranslateCodonsToAminoAcids(unittest.TestCase):
    def test_1(self):
        self.assertEqual(translate_codons_to_amino_acids(("AUG", "GCU", "UAA")), "MA*")

    def test_2(self):
        self.assertEqual(translate_codons_to_amino_acids(("AUG", "GAU", "UCU")), "MDS")

    def test_3(self):
        self.assertEqual(translate_codons_to_amino_acids(("AUG", "UAA", "UGA")), "M**")


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestTranslateCodonsToAminoAcids)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
