package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarTest {

    @Test
    void testCarGettersAndSetters() {
        Car car = new Car();

        car.setCarId("123");
        car.setCarName("Toyota Corolla");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        assertThat(car.getCarId()).isEqualTo("123");
        assertThat(car.getCarName()).isEqualTo("Toyota Corolla");
        assertThat(car.getCarColor()).isEqualTo("Red");
        assertThat(car.getCarQuantity()).isEqualTo(5);
    }
}
