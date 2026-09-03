import unittest

from q3b import maybe_remove_initial_m


class TestMaybeRemoveInitialM(unittest.TestCase):
    def test_1(self):
        self.assertEqual(maybe_remove_initial_m("MAIV"), "AIV")

    def test_2(self):
        self.assertEqual(maybe_remove_initial_m("MLIV"), "MLIV")

    def test_3(self):
        self.assertEqual(maybe_remove_initial_m("MGIV"), "GIV")


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(TestMaybeRemoveInitialM)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
