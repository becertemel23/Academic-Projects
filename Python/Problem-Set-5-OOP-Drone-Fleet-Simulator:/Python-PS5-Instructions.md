[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/j5m_I9Xy)
# COMP100 2025S Problem Set 5: Object Oriented Programming
### Deadline Thursday, May 29, 2025 23:59

The test cases given are just sample tests, and additional tests may be used while grading.

## Description
You will design a base `Drone` class and two specialized subclasses (`DeliveryDrone` and `SurveyDrone`), then compose them into a `DroneHub` that manages these drones collectively.

## Task Overview
You will:
1. Implement a `Drone` base class.
2. Implement two subclasses, `DeliveryDrone` and `SurveyDrone`, each inheriting from `Drone`.
3. Implement a `DroneHub` class that holds multiple drones and can perform actions on them collectively.

## Understanding Classes, Inheritance, and Subclasses

### What is a Class?
A **class** is a blueprint or template that defines what attributes (data) and methods (functions) objects of that type should have. For example:

```python
class Vehicle:
    def __init__(self, color):
        self.color = color

    def move(self):
        print("The vehicle is moving.")
```

Here, `Vehicle` is a class. Every `Vehicle` has a `color` and a `move()` method.

### What is an Object?
When you create a specific instance of a class, it’s called an **object**. For example:

```python
my_vehicle = Vehicle("red")
my_vehicle.move()  # Prints: "The vehicle is moving."
```

`my_vehicle` is now an object of type `Vehicle` with its own `color` and the ability to `move()`.

### What is Inheritance?

**Inheritance** allows you to create a new class that builds upon an existing class. The new class (a **subclass**) gets all the features of the existing class (the **base class**) and can add or modify them.

### What is a Subclass?

A **subclass** is a class that inherits from another class. For example, `Car` might be a subclass of `Vehicle`:

```python
class Car(Vehicle):
    def __init__(self, color, make, model):
        super().__init__(color)  # Call the parent constructor
        self.make = make
        self.model = model

    def move(self):
        # Override the move method from Vehicle
        print(f"The {self.color} {self.make} {self.model} is driving on the road.")
```

Here’s what is happening:
- `Car` inherits from `Vehicle`, so it starts with what `Vehicle` provides.
- By calling `super().__init__(color)`, we ensure that the parent class (`Vehicle`) sets up the basic attributes like `color`.
- We add new attributes `make` and `model` to `Car`.
- We override the `move()` method to provide a more specific message for cars.

When we create a `Car` object:

```python
my_car = Car("blue", "Honda", "Civic")
my_car.move()  # Prints: "The blue Honda Civic is driving on the road."
```

This shows that `Car` is a specialized version of `Vehicle`.

### Applying These Concepts to Our Drone Scenario
In this problem set:
- We start with a `Drone` class that defines general features and behaviors shared by all drones.
- We then create subclasses `DeliveryDrone` and `SurveyDrone` that inherit from `Drone`. Because of inheritance, these subclasses automatically have all the features of `Drone`, and we can add or override features to make them unique.
- Finally, we use a `DroneHub` class to manage multiple drones (of various types) together.

## Detailed Requirements

### Classes

#### 1. `Drone` (Base Class)
- **Attributes**:
  - `id` (string): Unique identifier for the drone.
  - `ids` (class variable, list): Shared across all drones. Initially an empty list. It stores all existing drone IDs. Every new drone must append its ID to this list.
  - `max_speed` (int): Maximum speed (km/h).
  - `current_location` (tuple, e.g. `(x, y)` or `(lat, lon)`): Current position.
  
- **Methods**:
  - `__init__(self, id, max_speed, current_location)`: Initialize with given attributes.
    - Before setting the attributes, use an `assert` statement to ensure the `id` is not already in the `ids` list. If it is, raise an assertion error with the message:  
  `"Drone id {id} already exists"`  
    - After successful assertion, add `id` to the class variable `ids`.
  - `move_to(self, new_location)`: Update `current_location` to `new_location`.
  - `get_type(self)`: Return `"Generic Drone"` by default.
  
This sets the foundation for all drones.

#### 2. `DeliveryDrone` (Subclass of `Drone`)
- **Additional Attributes**:
  - `cargo_capacity` (float): Maximum cargo weight.
  - `current_cargo` (float): Current cargo load (starts at 0).
  
- **Methods**:
  - `__init__(self, id, max_speed, current_location, cargo_capacity)`: Calls `super()` and sets `cargo_capacity`.
  - `load_cargo(self, weight)`: Adds `weight` to `current_cargo` if it does not exceed `cargo_capacity`. Otherwise, print a warning (`"Weight exceeds maximum capacity!"`) and do not load.
  - `deliver_cargo(self)`: Sets `current_cargo` to 0 and prints a delivery success message: `"Cargo delivered successfully!"`.
  - `get_type(self)`: Override to return `"Delivery Drone"`.

#### 3. `SurveyDrone` (Subclass of `Drone`)
- **Additional Attributes**:
  - `camera_quality` (string): e.g., `"4K"`, `"HD"`.
  - `survey_data` (list): Stores information about scanned areas.
  
- **Methods**:
  - `__init__(self, id, max_speed, current_location, camera_quality)`: Calls `super()` and sets `camera_quality`. Initializes `survey_data` as empty list.
  - `scan_area(self, area_coordinates)`: Simulates scanning the given area (a list of coordinates). Appends data to `survey_data` and prints a completion message: `"Survey completed!"`.
  - `get_type(self)`: Override to return `"Survey Drone"`.

#### 4. `DroneHub`
- **Attributes**:
  - `name` (string): Name of the drone hub.
  - `drones` (list): A list of `Drone` objects (including `DeliveryDrone` and `SurveyDrone`).
  
- **Methods**:
  - `__init__(self, name)`: Sets the hub name and initializes an empty `drones` list.
  - `add_drone(self, drone)`: Adds a `Drone` object to `drones`.
  - `list_drones(self)`: Prints each drone’s ID and its type (by calling `get_type()`).
  - `relocate_all_drones(self, new_location)`: Calls `move_to(new_location)` on each drone.
  - `__str__(self)`: When a `DroneHub` object is printed, it displays the hub name and a list of drones docked in the following format:

<pre>
--- DroneHub Central Fleet: 4 drones docked ---
[0]: #D1001 Delivery Drone
[1]: #S1001 Survey Drone
[2]: #S1002 Survey Drone
[3]: #D1002 Delivery Drone
</pre>

### Example simulator
```python
hub = DroneHub("Central Fleet")
drone1 = DeliveryDrone("#D1001", 100, (0, 0), 100)
drone2 = SurveyDrone("#S1001", 80, (0, 0), "HD")

drone1.load_cargo(10)
drone1.deliver_cargo()  # Cargo delivered successfully!

drone2.scan_area([(0, 0), (10, 10), (20, 20), (30, 30)])  # Survey completed!

drone3 = SurveyDrone("#S1002", 100, (0, 0), "4K")
drone3.scan_area([(0, 0), (10, 10), (20, 20), (30, 30)])  # Survey completed!

drone4 = DeliveryDrone("#D1002", 180, (0, 0), 45)
drone4.load_cargo(50)  # Weight exceeds maximum capacity!

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
# Drone ID: #D1001, Type: Delivery Drone
# Drone ID: #S1001, Type: Survey Drone
# Drone ID: #S1002, Type: Survey Drone
# Drone ID: #D1002, Type: Delivery Drone

print("--- Relocating all drones to (50.0, -120.0) ---")
hub.relocate_all_drones((50.0, -120.0))
```
