import unittest

from q3a import build_peptides_from_amino_sequence


class TestBuildPeptidesFromAminoSequence(unittest.TestCase):
    def test_1(self):
        self.assertEqual(
            build_peptides_from_amino_sequence("GGSMAIV*GGMLR*MP*"),
            ("MAIV", "MLR", "MP"),
        )

    def test_2(self):
        self.assertEqual(build_peptides_from_amino_sequence("MA*MG*L*"), ("MA", "MG"))

    def test_3(self):
        self.assertEqual(
            build_peptides_from_amino_sequence("GGSMAIV*GGMLR*MP*"),
            ("MAIV", "MLR", "MP"),
        )

    def test_4(self):
        self.assertEqual(
            build_peptides_from_amino_sequence("MAIVMGR*MR"),
            ("MAIVMGR",),
        )


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(
        TestBuildPeptidesFromAminoSequence
    )
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")
