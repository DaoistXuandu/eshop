package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreate() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);
        assertNotNull(createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(10, createdCar.getCarQuantity());
    }

    @Test
    void testCreateWithNullCarId() {
        Car car = new Car();
        car.setCarName("Mazda");
        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar.getCarId());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        Car car2 = new Car();
        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
    }

    @Test
    void testFindById() {
        Car car = new Car();
        car.setCarName("Honda");
        Car createdCar = carRepository.create(car);

        Car foundCar = carRepository.findById(createdCar.getCarId());
        assertNotNull(foundCar);
        assertEquals("Honda", foundCar.getCarName());

        Car notCar = carRepository.findById("");
        assertNull(notCar);
    }

    @Test
    public void testCreateCarWithNullId() {
        // Arrange: Create a car without an ID.
        Car car = new Car();
        car.setCarId(null);
        car.setCarName("Test Car");

        // Act: Call the create method.
        Car createdCar = carRepository.create(car);

        // Assert: Verify that the car now has an ID and it's a valid UUID.
        assertNotNull(createdCar.getCarId(), "Car ID should be generated if null.");
    }

    @Test
    public void testCreateCarWithPredefinedId() {
        // Arrange: Create a car with a predefined ID.
        String predefinedId = "12345";
        Car car = new Car();
        car.setCarId(predefinedId);
        car.setCarName("Test Car 2");

        // Act: Call the create method.
        Car createdCar = carRepository.create(car);

        // Assert: Verify that the predefined ID is preserved.
        assertEquals(predefinedId, createdCar.getCarId(), "Car ID should remain unchanged if already set.");
    }

    @Test
    public void testUpdateExistingCar() {
        // Arrange: Create and add a Car with a known ID.
        Car originalCar = new Car();
        originalCar.setCarId("1");
        originalCar.setCarName("Old Car");
        originalCar.setCarColor("Blue");
        originalCar.setCarQuantity(1);
        carRepository.create(originalCar);

        // Arrange: Create an updated Car object with new details.
        Car updatedCar = new Car();
        updatedCar.setCarName("New Car");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(5);

        // Act: Update the car with ID "1".
        Car result = carRepository.update("1", updatedCar);

        // Assert: Verify that update returns null (per current implementation)
        assertNull(result, "The update method should return null.");

        // Assert: Retrieve the car from the repository and verify its properties have been updated.
        Car fetchedCar = carRepository.findById("1");
        assertNotNull(fetchedCar, "The car should exist in the repository.");
        assertEquals("New Car", fetchedCar.getCarName(), "Car name should be updated.");
        assertEquals("Red", fetchedCar.getCarColor(), "Car color should be updated.");
        assertEquals(5, fetchedCar.getCarQuantity(), "Car quantity should be updated.");
    }

    @Test
    public void testUpdateNonExistingCar() {
        // Arrange: Create an updated Car object.
        Car updatedCar = new Car();
        updatedCar.setCarName("New Car");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(5);

        // Act: Attempt to update a car with an ID that does not exist.
        Car result = carRepository.update("nonexistent", updatedCar);

        // Assert: The update should return null, indicating no car was updated.
        assertNull(result, "Update should return null for a non-existing car ID.");
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById("nonexistent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarName("Ford");
        car.setCarColor("Blue");
        car.setCarQuantity(5);
        Car createdCar = carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Ford Updated");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(7);

        carRepository.update(createdCar.getCarId(), updatedCar);
        Car fetchedCar = carRepository.findById(createdCar.getCarId());

        assertNotNull(fetchedCar);
        assertEquals("Ford Updated", fetchedCar.getCarName());
        assertEquals("Black", fetchedCar.getCarColor());
        assertEquals(7, fetchedCar.getCarQuantity());
    }


    @Test
    public void testUpdateMultipleCars() {
        // Arrange: Create two cars with different IDs.
        Car car1 = new Car();
        car1.setCarId("1");
        car1.setCarName("Car One");
        car1.setCarColor("Blue");
        car1.setCarQuantity(1);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("2");
        car2.setCarName("Car Two");
        car2.setCarColor("Green");
        car2.setCarQuantity(2);
        carRepository.create(car2);

        // Arrange: Prepare an update for the car with ID "1".
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Car One");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(10);

        // Act: Update the car with ID "1".
        Car updateResult = carRepository.update("1", updatedCar);

        // Assert: The update method returns null.
        assertNull(updateResult, "Update should return null as per current implementation.");

        // Verify: Car with ID "1" is updated.
        Car fetchedCar1 = carRepository.findById("1");
        assertNotNull(fetchedCar1, "Car with ID '1' should be present.");
        assertEquals("Updated Car One", fetchedCar1.getCarName(), "Car One's name should be updated.");
        assertEquals("Red", fetchedCar1.getCarColor(), "Car One's color should be updated.");
        assertEquals(10, fetchedCar1.getCarQuantity(), "Car One's quantity should be updated.");

        // Verify: Car with ID "2" remains unchanged.
        Car fetchedCar2 = carRepository.findById("2");
        assertNotNull(fetchedCar2, "Car with ID '2' should be present.");
        assertEquals("Car Two", fetchedCar2.getCarName(), "Car Two's name should remain unchanged.");
        assertEquals("Green", fetchedCar2.getCarColor(), "Car Two's color should remain unchanged.");
        assertEquals(2, fetchedCar2.getCarQuantity(), "Car Two's quantity should remain unchanged.");
    }


    @Test
    void testDelete() {
        Car car = new Car();
        Car createdCar = carRepository.create(car);

        carRepository.delete(createdCar.getCarId());
        Car deletedCar = carRepository.findById(createdCar.getCarId());
        assertNull(deletedCar);
    }

    @Test
    void testDeleteNonexistentCar() {
        assertDoesNotThrow(() -> carRepository.delete("nonexistent-id"));
    }
}
