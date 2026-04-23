package Lab1.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Lab1.pages.LoginPage;
import Lab1.pages.InventoryPage;
import Lab1.utils.ReportManager;

import com.aventstack.extentreports.*;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;
    LoginPage loginPage;

    static ExtentReports extent;
    ExtentTest test;

    @BeforeAll
    static void iniciarReporte() {
        extent = ReportManager.getReport();
    }

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\\\selenium webdriver\\\\ChromeDriver\\\\chromedriver-win64\\\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @AfterAll
    static void cerrarReporte() {
        extent.flush(); // Genera el HTML
    }

    // TEST 1: Login correcto
    @Test
    void loginCorrecto() {

        test = extent.createTest("Login Correcto");

        loginPage.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.urlContains("inventory"));

        if (driver.getCurrentUrl().contains("inventory")) {
            test.pass("Login exitoso");
        } else {
            test.fail("Login fallido");
        }

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    // TEST 2: Login incorrecto
    @Test
    void loginIncorrecto() {

        test = extent.createTest("Login Incorrecto");

        loginPage.login("usuario_incorrecto", "contraseña_incorrecta");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test='error']")
        ));

        if (loginPage.errorVisible()) {
            test.pass("Mensaje de error mostrado correctamente");
        } else {
            test.fail("No se mostró mensaje de error");
        }

        assertTrue(loginPage.errorVisible());
    }

    // TEST 3: Agregar producto al carrito
    @Test
    void agregarProductoAlCarrito() throws InterruptedException {

        test = extent.createTest("Agregar producto al carrito");

        loginPage.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.urlContains("inventory"));

        InventoryPage inventory = new InventoryPage(driver);
        
        inventory.agregarBackpack();
        Thread.sleep(2000); // ver después del click

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test='shopping-cart-badge']")
        ));

        int cantidad = inventory.obtenerCantidadCarrito();

        if (cantidad > 0) {
            test.pass("Producto agregado correctamente al carrito");
        } else {
            test.fail("No se agregó el producto al carrito");
        }

        assertTrue(inventory.carritoTieneProductos(),
                "El carrito debería mostrar productos");

        assertTrue(cantidad > 0,
                "El carrito debería tener al menos 1 producto");
    }
}