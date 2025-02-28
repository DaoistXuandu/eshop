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
    void testUpdateNonexistentCar() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Unknown");
        Car result = carRepository.update("nonexistent-id", updatedCar);
        assertNull(result);
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
