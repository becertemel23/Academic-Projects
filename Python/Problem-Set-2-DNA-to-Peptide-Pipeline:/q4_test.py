import unittest

from q4 import dna_to_peptides


class TestDnaToPeptides(unittest.TestCase):
    def test_1(self):
        self.assertEqual(
            dna_to_peptides("ATGGCCATTGTAATGGGCCGCTGAATGCGTGA"), ("AIVMGR",)
        )

    def test_2(self):
        # a big sequence
        self.assertEqual(
            dna_to_peptides(
                "ATGGCCATTGTAATGGGCCGCTGAATGCGTGAATGGCCGCTGAATGCGTGAATGGCCGCTGAATGCGTGA"
            ),
            ("AIVMGR", "MREWPLNA"),
        )


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestDnaToPeptides)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
