import unittest
from drone import Drone


class TestDrone(unittest.TestCase):
    def setUp(self):
        # Clear class-level ID list before each test
        Drone.ids = []
        self.drone = Drone("#D1001", 100, (34.0522, -118.2437))

    def test_1_initialization(self):
        self.assertEqual(self.drone.id, "#D1001")
        self.assertEqual(self.drone.max_speed, 100)
        self.assertEqual(self.drone.current_location, (34.0522, -118.2437))

    def test_2_move_to(self):
        new_location = (40.7128, -74.0060)
        self.drone.move_to(new_location)
        self.assertEqual(self.drone.current_location, new_location)

    def test_3_get_type(self):
        self.assertEqual(self.drone.get_type(), "Generic Drone")

    def test_4_location_type(self):
        self.assertIsInstance(self.drone.current_location, tuple)
        self.assertEqual(len(self.drone.current_location), 2)
        self.assertIsInstance(self.drone.current_location[0], float)
        self.assertIsInstance(self.drone.current_location[1], float)

    def test_5_ids_class_variable(self):
        # The ID should be in the class variable
        self.assertIn("#D1001", Drone.ids)
        self.assertEqual(len(Drone.ids), 1)

    def test_6_duplicate_id_assertion(self):
        with self.assertRaises(AssertionError) as cm:
            duplicate = Drone("#D1001", 80, (0, 0))
        self.assertEqual(str(cm.exception), "Drone id #D1001 already exists")


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestDrone)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")