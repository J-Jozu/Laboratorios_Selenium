from login_page import LoginPage

def test_ejecucion_pom(driver):
    driver.get("https://saucedemo.com")

    # Instanciamos la página
    login = LoginPage(driver)

    # Caso de uso: Login fallido
    login.ingresar_credenciales("locked_out_user", "secret_sauce")
    login.click_login()

    mensaje = login.obtener_error()

    # Validación real (assert)
    assert "locked out" in mensaje.lower()