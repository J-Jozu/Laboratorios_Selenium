package Lab1.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Localizadores
    By username = By.id("user-name");
    By password = By.id("password");
    By loginBtn = By.id("login-button");
    By errorMsg = By.cssSelector("[data-test='error']");

    // Acciones
    public void escribirUsuario(String user) {
        driver.findElement(username).sendKeys(user);
    }

    public void escribirPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public boolean errorVisible() {
        return driver.findElement(errorMsg).isDisplayed();
    }

    // Método combinado (más limpio)
    public void login(String user, String pass) {
        escribirUsuario(user);
        escribirPassword(pass);
        clickLogin();
    }
}