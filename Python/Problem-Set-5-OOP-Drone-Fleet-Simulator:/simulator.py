from delivery_drone import DeliveryDrone
from survey_drone import SurveyDrone
from drone_hub import DroneHub


hub = DroneHub("Central Fleet")
drone1 = DeliveryDrone("#D1001", 100, (0, 0), 100)
drone2 = SurveyDrone("#S1001", 80, (0, 0), "HD")

drone1.load_cargo(10)
drone1.deliver_cargo()

drone2.scan_area([(0, 0), (10, 10), (20, 20), (30, 30)])

drone3 = SurveyDrone("#S1002", 100, (0, 0), "4K")
drone3.scan_area([(0, 0), (10, 10), (20, 20), (30, 30)])

drone4 = DeliveryDrone("#D1002", 180, (0, 0), 45)
drone4.load_cargo(50)

drone1.move_to((10, 10))
drone2.move_to((20, 20))
drone3.move_to((30, 30))
drone4.move_to((40, 40))

hub.add_drone(drone1)
hub.add_drone(drone2)
hub.add_drone(drone3)
hub.add_drone(drone4)

print(f"--- DroneHub: {hub.name} ---")
hub.list_drones()

print("--- Relocating all drones to (50.0, -120.0) ---")
hub.relocate_all_drones((50.0, -120.0))

