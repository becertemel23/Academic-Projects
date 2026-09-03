import unittest
import sys
from io import StringIO

from drone import Drone
from delivery_drone import DeliveryDrone
from survey_drone import SurveyDrone
from drone_hub import DroneHub


class TestDroneHub(unittest.TestCase):
    def setUp(self):
        Drone.ids = []

    def test_1(self):
        hub = DroneHub("Central Fleet")
        self.assertEqual(hub.name, "Central Fleet")
        self.assertEqual(hub.drones, [])

    def test_2(self):
        hub = DroneHub("Central Fleet")
        hub.add_drone(Drone("#D1001", 100, (34.0522, -118.2437)))
        self.assertEqual(len(hub.drones), 1)
        self.assertEqual(hub.drones[0].id, "#D1001")
        self.assertEqual(hub.drones[0].max_speed, 100)
        self.assertEqual(hub.drones[0].current_location, (34.0522, -118.2437))

    def test_3(self):
        hub = DroneHub("Central Fleet")
        hub.add_drone(Drone("#D1001", 100, (34.0522, -118.2437)))
        hub.add_drone(Drone("#D1002", 100, (34.0522, -118.2437)))
        self.assertEqual(len(hub.drones), 2)
        self.assertEqual(hub.drones[0].id, "#D1001")
        self.assertEqual(hub.drones[1].id, "#D1002")

    def test_4(self):
        hub = DroneHub("Central Fleet")
        hub.add_drone(DeliveryDrone("#D1001", 100, (34.0522, -118.2437), 100))
        hub.add_drone(SurveyDrone("#S1001", 100, (34.0522, -118.2437), "HD"))
        self.assertEqual(len(hub.drones), 2)
        self.assertEqual(hub.drones[0].id, "#D1001")
        self.assertEqual(hub.drones[1].id, "#S1001")

    def test_5(self):
        expected_lines = [
            "Drone ID: #D1001 Type: Delivery Drone",
            "Drone ID: #S1001 Type: Survey Drone"
        ]
        captured_output = StringIO()
        sys_stdout_backup = sys.stdout
        sys.stdout = captured_output
        try:
            hub = DroneHub("Central Fleet")
            hub.add_drone(DeliveryDrone("#D1001", 100, (34.0522, -118.2437), 100))
            hub.add_drone(SurveyDrone("#S1001", 100, (34.0522, -118.2437), "HD"))
            hub.list_drones()
        finally:
            sys.stdout = sys_stdout_backup
        actual_lines = captured_output.getvalue().strip().split("\n")
        self.assertEqual(actual_lines, expected_lines)

    def test_6(self):
        hub = DroneHub("Central Fleet")
        hub.add_drone(DeliveryDrone("#D1001", 100, (34.0522, -118.2437), 100))
        hub.add_drone(SurveyDrone("#S1001", 100, (34.0522, -118.2437), "HD"))
        hub.relocate_all_drones((50.0, -120.0))

        # Because the implementation removes drones after moving them
        self.assertEqual(len(hub.drones), 0)

    def test_7_str_representation(self):
        hub = DroneHub("Central Fleet")
        hub.add_drone(DeliveryDrone("#D1001", 100, (0, 0), 100))
        hub.add_drone(SurveyDrone("#S1001", 80, (0, 0), "HD"))
        expected = (
            "\n--- DroneHub Central Fleet: 2 drones docked ---\n"
            "[0]: #D1001 Delivery Drone\n"
            "[1]: #S1001 Survey Drone\n"
        )
        self.assertEqual(str(hub), expected)


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestDroneHub)
    result = unittest.TextTestRunner().run(suite)
    total_tests_run = result.testsRun
    total_failures = len(result.failures) + len(result.errors)
    total_passed = total_tests_run - total_failures
    print(f"Test Passed: {total_passed}/{total_tests_run}")