package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




public class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarServiceImpl carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }


    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        mockMvc.perform(post("/car/createCar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));
        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        when(carService.findById("123")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("123");

        mockMvc.perform(post("/car/editCar").flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).update(eq("123"), any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar").param("carId", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).deleteCarById("123");
    }
}
