import pytest
from login_page import LoginPage

def test_login_fallido(driver):
    """Prueba de usuario bloqueado"""
    driver.get("https://saucedemo.com")

    login = LoginPage(driver)
    login.ingresar_credenciales("locked_out_user", "secret_sauce")
    login.click_login()

    # Validación correcta del mensaje de error
    assert "locked out prueba de captura" in login.obtener_error().lower()


def test_login_exitoso(driver):
    """Prueba de login correcto"""
    driver.get("https://saucedemo.com")

    login = LoginPage(driver)
    login.ingresar_credenciales("standard_user", "secret_sauce")
    login.click_login()

    # Validar que llegó al inventario
    assert "inventory.html" in driver.current_url