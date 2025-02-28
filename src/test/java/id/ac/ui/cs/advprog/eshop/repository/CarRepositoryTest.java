package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import java.util.Iterator;

public class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    public void testCreateCar() {
        Car car = new Car();
        car.setCarName("Test Car");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        Car createdCar = carRepository.create(car);
        assertNotNull(createdCar.getCarId());
        assertEquals("Test Car", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(10, createdCar.getCarQuantity());
    }

    @Test
    public void testFindAllCars() {
        Car car1 = new Car();
        car1.setCarName("Car One");
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarName("Car Two");
        carRepository.create(car2);

        Iterator<Car> cars = carRepository.findAll();
        assertTrue(cars.hasNext());
    }

    @Test
    public void testFindCarById() {
        Car foundCar = carRepository.findById("No car");
        assertNull(foundCar);

        Car car = new Car();
        car.setCarName("Find Me");
        Car createdCar = carRepository.create(car);

        foundCar = carRepository.findById(createdCar.getCarId());
        assertNotNull(foundCar);
        assertEquals("Find Me", foundCar.getCarName());

        foundCar = carRepository.findById("Invalid Car");
        assertNull(foundCar);
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car();
        car.setCarName("Old Name");
        Car createdCar = carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(20);

        carRepository.update(createdCar.getCarId(), updatedCar);

        Car fetchedCar = carRepository.findById(createdCar.getCarId());
        assertNotNull(fetchedCar);
        assertEquals("New Name", fetchedCar.getCarName());
        assertEquals("Blue", fetchedCar.getCarColor());
        assertEquals(20, fetchedCar.getCarQuantity());
    }

    @Test
    public void testDeleteCar() {
        Car car = new Car();
        car.setCarName("To Be Deleted");
        Car createdCar = carRepository.create(car);

        carRepository.delete(createdCar.getCarId());
        Car deletedCar = carRepository.findById(createdCar.getCarId());
        assertNull(deletedCar);
    }
}
