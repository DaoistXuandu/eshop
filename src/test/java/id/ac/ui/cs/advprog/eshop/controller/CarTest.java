package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testHomePage() {
        String viewName = carController.homePage();
        assertEquals("HomePage", viewName);
    }

    @Test
    void testCreateCar() {
        String viewName = carController.createCar(model);
        assertEquals("createCar", viewName);
        verify(model, times(1)).addAttribute(eq("car"), any(Car.class));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        when(carService.create(car)).thenReturn(car);

        mockMvc.perform(post("/car/createCar").flashAttr("car", car))
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testCarListPage() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carService.findAll()).thenReturn(cars);

        String viewName = carController.carListPage(model);
        assertEquals("CarList", viewName);
        verify(model, times(1)).addAttribute("cars", cars);
    }

    @Test
    void testEditCar() {
        Car car = new Car();
        when(carService.findById("1")).thenReturn(car);

        String viewName = carController.editCar("1", model);
        assertEquals("editCar", viewName);
        verify(model, times(1)).addAttribute("car", car);
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("1");
        doNothing().when(carService).update("1", car);

        mockMvc.perform(post("/car/editCar").flashAttr("car", car))
                .andExpect(redirectedUrl("listCar"));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).deleteCarById("1");

        mockMvc.perform(post("/car/deleteCar").param("carId", "1"))
                .andExpect(redirectedUrl("listCar"));
    }
}