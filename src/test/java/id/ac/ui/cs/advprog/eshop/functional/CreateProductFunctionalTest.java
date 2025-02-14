package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    /*
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /*
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest(){
        baseUrl=String.format("%s:%d",testBaseUrl, serverPort);
    }

    @AfterEach
    void cleanup(ChromeDriver driver) {
        driver.get(baseUrl + "/product/list");
        while (pageSourceContains(driver, "Delete Product")) {
            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Delete Product"))).click();
        }
        driver.quit();
    }

    @Nested
    class ProductListPageTests {
        @Test
        void productListPage_isDisplayed(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/list");
            assertPageTitle(driver, "Product List");
        }
        @Test
        void productListPage_hasCreateButton(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/list");
            driver.findElement(By.id("create")).click();
            assertPageTitle(driver, "Create New Product");
        }
    }
    @Nested
    class CreateProductPageTests {
        @Test
        void createProductPage_isDisplayed(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            assertPageTitle(driver, "Create New Product");
        }
        @Test
        void createProductPage_hasCorrectFormFields(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");

            assertFormAction(driver, baseUrl + "/product/create");
            assertFormFieldNotNull(driver, "nameInput");
            assertFormFieldNotNull(driver, "quantityInput");
        }
        @Test
        void createProductPage_canCreateProduct(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");


            driver.findElement(By.id("nameInput")).sendKeys("SatuSigma");
            driver.findElement(By.id("quantityInput")).sendKeys("123");
            driver.findElement(By.tagName("form")).submit();

            assertPageTitle(driver, "Product List");

            driver.get(baseUrl + "/product/list");
            assertEquals(true, pageSourceContains(driver, "SatuSigma"));
            assertEquals(true, pageSourceContains(driver, "123"));
        }
    }
    @Nested
    class EditProductPageTests {
        @Test
        void editProductPage_isDisplayed(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("Sigma");
            driver.findElement(By.id("quantityInput")).sendKeys("123");
            driver.findElement(By.tagName("form")).submit();

            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Edit Product"))).click();
            assertPageTitle(driver, "Edit Product");
        }
        @Test
        void editProductPage_hasCorrectFormFields(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("Sigma");
            driver.findElement(By.id("quantityInput")).sendKeys("123");
            driver.findElement(By.tagName("form")).submit();

            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Edit Product"))).click();
            assertPageTitle(driver, "Edit Product");

            assertFormFieldNotNull(driver, "nameInput");
            assertFormFieldNotNull(driver, "quantityInput");
            assertEquals("Sigma", getFormFieldValue(driver, "nameInput"));
            assertEquals("123", getFormFieldValue(driver, "quantityInput"));
        }
        @Test
        void editProductPage_canEditProduct(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("Sigma");
            driver.findElement(By.id("quantityInput")).sendKeys("123");
            driver.findElement(By.tagName("form")).submit();

            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Edit Product"))).click();
            driver.findElement(By.id("nameInput")).clear();
            driver.findElement(By.id("nameInput")).sendKeys("Sekai wa zankoku da~");
            driver.findElement(By.id("quantityInput")).clear();
            driver.findElement(By.id("quantityInput")).sendKeys("353770");
            driver.findElement(By.tagName("form")).submit();

            assertPageTitle(driver, "Product List");

            driver.get(baseUrl + "/product/list");
            assertEquals(true, pageSourceContains(driver, "Sekai wa zankoku da~"));
            assertEquals(true, pageSourceContains(driver, "353770"));
        }
    }
    @Nested
    class DeleteProductTests {
        @Test
        void deleteProduct(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("All my Fellas");
            driver.findElement(By.id("quantityInput")).sendKeys("177013");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/list");
            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Delete Product"))).click();
            assertPageTitle(driver, "Product List");
            assertEquals(false, pageSourceContains(driver, "All my Fellas"));
        }
        @Test
        void deleteFirstProduct(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");

            driver.findElement(By.id("nameInput")).sendKeys("All my Fellas");
            driver.findElement(By.id("quantityInput")).sendKeys("177013");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("Usagi");
            driver.findElement(By.id("quantityInput")).sendKeys("353770");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/list");
            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Delete Product"))).click();
            assertPageTitle(driver, "Product List");
            assertEquals(false, pageSourceContains(driver, "All my Fellas"));
            assertEquals(true, pageSourceContains(driver, "Usagi"));
        }
        @Test
        void deleteLastProduct(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("All my Fellas");
            driver.findElement(By.id("quantityInput")).sendKeys("177013");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("Usagi");
            driver.findElement(By.id("quantityInput")).sendKeys("353770");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/list");
            driver.findElement(By.xpath(String.format("(//*[text()='%s'])[last()]", "Delete Product"))).click();
            assertPageTitle(driver, "Product List");
            assertEquals(true, pageSourceContains(driver, "All my Fellas"));
            assertEquals(false, pageSourceContains(driver, "Usagi"));
        }
        @Test
        void deleteEditedProduct(ChromeDriver driver) throws Exception {
            driver.get(baseUrl + "/product/create");
            driver.findElement(By.id("nameInput")).sendKeys("Pokemon");
            driver.findElement(By.id("quantityInput")).sendKeys("177013");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/list");
            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Edit Product"))).click();
            driver.findElement(By.id("nameInput")).clear();
            driver.findElement(By.id("nameInput")).sendKeys("Palworld");
            driver.findElement(By.id("quantityInput")).clear();
            driver.findElement(By.id("quantityInput")).sendKeys("353770");
            driver.findElement(By.tagName("form")).submit();

            driver.get(baseUrl + "/product/list");
            driver.findElement(By.xpath(String.format("//*[text()='%s']", "Delete Product"))).click();
            assertPageTitle(driver, "Product List");
            assertEquals(false, pageSourceContains(driver, "Palworld"));
        }
    }

    //////////////////////////////
    // Helper methods for tests //
    //////////////////////////////


    private String getPageTitle(ChromeDriver driver) {
        return driver.getTitle();
    }

    private void assertPageTitle(ChromeDriver driver, String expectedTitle) {
        String pageTitle = getPageTitle(driver);
        assertEquals(expectedTitle, pageTitle);
    }

    private void assertFormFieldNotNull(ChromeDriver driver, String fieldId) {
        assertNotNull(driver.findElement(By.id(fieldId)));
    }

    private String getFormFieldValue(ChromeDriver driver, String fieldId) {
        return driver.findElement(By.id(fieldId)).getAttribute("value");
    }

    private void assertFormAction(ChromeDriver driver, String expectedAction) {
        String formAction = driver.findElement(By.tagName("form")).getAttribute("action");
        assertEquals(expectedAction, formAction);
    }

    private boolean pageSourceContains(ChromeDriver driver, String expectedContent) {
        String pageSource = driver.getPageSource();
        return pageSource.contains(expectedContent);
    }
}