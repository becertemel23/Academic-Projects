import unittest

from q1 import transcribe_dna_to_mrna


class TestTranscribeDnaToMrna(unittest.TestCase):
    def test_1(self):
        result = transcribe_dna_to_mrna("ATCG")
        self.assertEqual(result, "UAGC")

    def test_2(self):
        result = transcribe_dna_to_mrna("ATGGCC")
        self.assertEqual(result, "AUGGCC")

    def test_3(self):
        result = transcribe_dna_to_mrna("TACCGG")
        self.assertEqual(result, "AUGGCC")

    def test_4(self):
        result = transcribe_dna_to_mrna("AtCG")
        self.assertEqual(result, "UAGC")

    def test_5(self):
        result = transcribe_dna_to_mrna("")
        self.assertEqual(result, "")


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestTranscribeDnaToMrna)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
