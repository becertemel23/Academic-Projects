from drone import Drone


class DeliveryDrone(Drone):
    """
    A delivery drone.

    Attributes:
        cargo_capacity (float): The maximum cargo weight.
        current_cargo (float): The current cargo load (starts at 0).

    Methods:
        __init__(self, id: str, max_speed: int, current_location: tuple[float, float], cargo_capacity: float):
            Initializes a delivery drone with the given attributes.
        load_cargo(self, weight: float) -> None:
            Loads cargo onto the drone.
        deliver_cargo(self) -> None:
            Delivers the cargo.
    """
    
    def __init__(self, id: str, max_speed: int, current_location: tuple[float, float], cargo_capacity: float):
        
        super().__init__(id,max_speed,current_location)
        self.cargo_capacity=cargo_capacity
        self.current_cargo=0
        """
        Initializes a delivery drone with the given attributes.

        Args:
            id (str): The unique identifier for the drone.
            max_speed (int): The maximum speed of the drone (km/h).
            current_location (tuple[float, float]): The current location of the drone (latitude, longitude).
            cargo_capacity (float): The maximum cargo weight.
        """


    def load_cargo(self, weight: float) -> None:
        
        
        if self.current_cargo+weight>self.cargo_capacity:
            print("Weight exceeds maximum capacity!")
        else:     
            self.current_cargo+=weight        
        """
        Loads cargo onto the drone.

        Args:
            weight (float): The weight of the cargo to load.

        Returns:
            None
        """


    def deliver_cargo(self) -> None:
        
        
        self.current_cargo=0
        
        print("Cargo delivered successfully!")
        """
        Delivers the cargo.
        """


    def get_type(self) -> str:
        
        return "Delivery Drone"
        """
        Returns the type of the drone.

        Returns:
            str: The type of the drone.
        """

