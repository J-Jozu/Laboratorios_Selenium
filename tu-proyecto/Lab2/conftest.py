import pytest
from selenium import webdriver
import pytest_html
from selenium.webdriver.chrome.options import Options

@pytest.hookimpl(hookwrapper=True)
def pytest_runtest_makereport(item, call):
    """Captura de pantalla automática en caso de fallo"""
    outcome = yield
    report = outcome.get_result()

    if report.when == "call" and report.failed:
        driver = item.funcargs.get("driver", None)

        if driver is not None:
            screenshot = driver.get_screenshot_as_base64()

            html = f'''
            <div>
                <img src="data:image/png;base64,{screenshot}"
                     alt="screenshot"
                     style="width:304px;height:228px;"
                     onclick="window.open(this.src)"
                     align="right"/>
            </div>
            '''

            extras = getattr(report, "extras", [])
            extras.append(pytest_html.extras.html(html))
            report.extras = extras
            
@pytest.fixture
def driver():
    options = Options()
    options.add_argument("--headless")  # 👈 clave
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")

    driver = webdriver.Chrome(options=options)
    driver.maximize_window()

    yield driver
    driver.quit()