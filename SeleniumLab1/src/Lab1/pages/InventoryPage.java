package Lab1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    By addBackpack = By.id("add-to-cart-sauce-labs-backpack");
    By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");

    public void agregarBackpack() {
        driver.findElement(addBackpack).click();
    }

    public int obtenerCantidadCarrito() {
        String texto = driver.findElement(cartBadge).getText();
        return Integer.parseInt(texto);
    }

    public boolean carritoTieneProductos() {
        return driver.findElement(cartBadge).isDisplayed();
    }
}