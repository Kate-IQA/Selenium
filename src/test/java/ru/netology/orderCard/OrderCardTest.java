package ru.netology.orderCard;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.System.setProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.trim;

public class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFillForm() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Дегтярева Екатерина");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79139469364");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFillNameWrong() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Alarm");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79139469364");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFillName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79139469364");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFillPhoneWrong() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Дегтярева Екатерина");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7913946934");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input_invalid .input__sub)).getText().trim();
        assertEquals(expected, actual);

    }
  @Test
    void shouldPhoneEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Дегтярева Екатерина");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);

    }
    @Test
    void shouldNotCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Дегтярева Екатерина");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79139469364");
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();
        assertEquals(expected, actual);

    }

}
