package Lab1;
import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement; 
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
public class Main {

	public static void main(String[] args) {
	    // Configurar driver 
        System.setProperty("webdriver.chrome.driver", "C:\\selenium webdriver\\ChromeDriver\\chromedriver-win64\\chromedriver.exe"); 
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
 
        try { 
            // Abrir página 
            driver.get("https://www.saucedemo.com/"); 
 
            // Maximizar ventana 
            driver.manage().window().maximize(); 
 
            // Ingresar usuario 
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
            username.sendKeys("standard_user"); 
 
            // Ingresar contraseña 
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            password.sendKeys("secret_sauce"); 
 
            // Click en login 
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
            loginBtn.click(); 
 
            // Validación simple 
            String currentUrl = driver.getCurrentUrl(); 
 
            if (currentUrl.contains("inventory")) { 
                System.out.println("   Prueba exitosa: Login correcto"); 
            } else if (driver.getPageSource().contains("Epic sadface: Username and password do not match any user in this service")){ 
                System.out.println("  Prueba fallida: Credenciales incorrectas"); 
            } 
 
        } finally { 
            // Cerrar navegador
            try {
                Thread.sleep(5000); // pausa antes de cerrar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.close();
        } 
    } 
}
