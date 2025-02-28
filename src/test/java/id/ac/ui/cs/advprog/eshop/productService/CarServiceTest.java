package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Car car = new Car();
        when(carRepository.create(car)).thenReturn(car);

        Car createdCar = carService.create(car);

        assertThat(createdCar).isEqualTo(car);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        Car car2 = new Car();
        List<Car> cars = Arrays.asList(car1, car2);
        Iterator<Car> iterator = cars.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> foundCars = carService.findAll();

        assertThat(foundCars).hasSize(2);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Car car = new Car();
        when(carRepository.findById("123")).thenReturn(car);

        Car foundCar = carService.findById("123");

        assertThat(foundCar).isEqualTo(car);
        verify(carRepository, times(1)).findById("123");
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        carService.update("123", car);

        verify(carRepository, times(1)).update("123", car);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("123");

        carService.deleteCarById("123");

        verify(carRepository, times(1)).delete("123");
    }
}
