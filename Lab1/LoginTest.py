from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# Ruta al chromedriver
service = Service("C:\\selenium webdriver\\ChromeDriver\\chromedriver-win64\\chromedriver.exe")

driver = webdriver.Chrome(service=service)
wait = WebDriverWait(driver, 10)

try:
    # TEST 1: LOGIN INVÁLIDO
    driver.get("https://www.saucedemo.com/")
    driver.maximize_window()

    driver.find_element(By.ID, "user-name").send_keys("usuario_incorrecto")
    driver.find_element(By.ID, "password").send_keys("clave_incorrecta")
    driver.find_element(By.ID, "login-button").click()

    # Esperar mensaje de error
    error = wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, "[data-test='error']")))

    if error.is_displayed():
        print("Login inválido: OK (mensaje mostrado)")
    else:
        print("Login inválido: FALLÓ")

    # TEST 2: LOGIN CORRECTO + AGREGAR PRODUCTO
    driver.get("https://www.saucedemo.com/")

    driver.find_element(By.ID, "user-name").send_keys("standard_user")
    driver.find_element(By.ID, "password").send_keys("secret_sauce")
    driver.find_element(By.ID, "login-button").click()

    # Esperar inventario
    wait.until(EC.visibility_of_element_located((By.ID, "inventory_container")))

    # Esperar botón y hacer click
    wait.until(EC.element_to_be_clickable((By.ID, "add-to-cart-sauce-labs-backpack")))
    driver.find_element(By.ID, "add-to-cart-sauce-labs-backpack").click()
    print("Login correcto: OK")
    print("Producto agregado")

    # TEST 3: VALIDAR CARRITO
    badge = wait.until(EC.visibility_of_element_located(
        (By.CSS_SELECTOR, "[data-test='shopping-cart-badge']")
    ))

    cantidad = int(badge.text)

    if cantidad > 0:
        print("Carrito OK - cantidad:", cantidad)
    else:
        print("Carrito FALLÓ")

finally:
    driver.quit()