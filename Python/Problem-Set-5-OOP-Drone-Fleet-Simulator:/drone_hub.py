from drone import Drone


class DroneHub:
    """
    A drone hub.

    Attributes:
        name (str): The name of the drone hub.
        drones (list): A list of drones in the hub.

    Methods:
        __init__(self, name: str): Initializes the drone hub with the given name.
        add_drone(self, drone: Drone): Adds a drone to the hub.
        list_drones(self): Prints the list of drones in the hub.
        relocate_all_drones(self, new_location: tuple[float, float]): Relocates all drones to the new location.
    """

    def __init__(self, name: str):
        self.name=name
        self.drones=[]
        """
        Initializes the drone hub with the given name.

        Args:
            name (str): The name of the drone hub.
        """


    def add_drone(self, drone: Drone) -> None:
        
        self.drones.append(drone)
        """
        Adds a drone to the hub.

        Args:
            drone (Drone): The drone to add.
        """


    def list_drones(self) -> None:
        
        for drone in self.drones:
            print(f"Drone ID: {drone.id} Type: {drone.get_type()}")
        """
        Prints the list of drones in the hub.
        """


    def relocate_all_drones(self, new_location: tuple[float, float]) -> None:
        
        for drone in self.drones:
            drone.move_to(new_location)
        self.drones.clear()
        """
        Relocates all drones to the new location.

        Args:
            new_location (tuple[float, float]): The new location to move the drones to (latitude, longitude).
        """
    def __str__(self):
        
        result="\n"+f"--- DroneHub Central Fleet: {len(self.drones)} drones docked ---"+"\n"
        for i in range(len(self.drones)):
            result+=f"[{i}]: {self.drones[i].id} {self.drones[i].get_type()}\n"
        return result
        
        """
        Return a string of the form:
        --- DroneHub Central Fleet: 4 drones docked ---
        [0]: #D1001 Delivery Drone
        [1]: #S1001 Survey Drone
        ...
        """