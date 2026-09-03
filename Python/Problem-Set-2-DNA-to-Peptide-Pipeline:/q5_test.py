import unittest

from q5 import peptides_freq_count


class TestPeptidesFreqCount(unittest.TestCase):
    def test_1(self):
        self.assertEqual(
            peptides_freq_count(("GL", "MRL", "MFL", "MMM", "MMM", "MFL")),
            (("GL", 1), ("MRL", 1), ("MFL", 2), ("MMM", 2)),
        )

    def test_2(self):
        self.assertEqual(
            peptides_freq_count(
                (
                    "GL",
                    "MRL",
                    "MFL",
                    "MMM",
                    "MMM",
                    "MMM",
                    "MMM",
                    "MFL",
                    "MMM",
                    "GL",
                    "MFL",
                    "MMM",
                    "MFL",
                    "GL",
                    "MFL",
                    "GL",
                    "GL",
                    "MFL",
                    "GL",
                )
            ),
            (("GL", 6), ("MRL", 1), ("MFL", 6), ("MMM", 6)),
        )

    def test_3(self):
        self.assertEqual(
            peptides_freq_count(("GL", "MRL", "MFL", "MMM", "MMM", "MFL") * 1000000),
            (("GL", 1000000), ("MRL", 1000000), ("MFL", 2000000), ("MMM", 2000000)),
        )


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestPeptidesFreqCount)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
