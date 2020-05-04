package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ExpectedConditionsTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        options.addPreference("intl.accept_languages", "en");
        driver = new FirefoxDriver(options);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }



    @Test
    public void checkElementVisibleAndClickable(){
        driver.get("http://automationpractice.com");
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".shopping_cart a[title='View my shopping cart']")));

        driver.findElement(By.cssSelector("div.shopping_cart > a")).click();
        assertTrue(driver.getTitle().contains("Order"));
    }

    @Test
    public void checkElementToBeSelectedAndPresenceOfElement(){
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation");
        driver.findElement(By.id("email_create")).sendKeys("testtdsa@testsadasd.com");
        driver.findElement(By.id("SubmitCreate")).click();
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.presenceOfElementLocated(By.id("id_gender2")));
        driver.findElement(By.id("id_gender2")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeSelected(By.id("id_gender2")));

        WebElement queryTop = driver.findElement(By.cssSelector("h3.page-subheading"));
        assertEquals("your personal information",queryTop.getText().toLowerCase());
    }

    @Test
    public void checkElementWithTextPresent(){
        driver.get("http://book.theautomatedtester.co.uk/chapter1");
        driver.findElement(By.id("secondajaxbutton")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("html5div")), "I have been added with a timeout"));
        assertTrue(true);
    }

    @Test
    public void checkElementWithTextPresentAsValue(){
        driver.get("http://book.theautomatedtester.co.uk/chapter4");
        //No idea where to find this kind of situation
        driver.findElement(By.id("nextBid")).sendKeys("abc");
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("nextBid"), "abc"));
        assertTrue(true);
    }

    @Test
    public void checkElementWithTextPresentInTitle(){
        driver.get("http://automationpractice.com/index.php");
        driver.findElement(By.cssSelector("a[title='Contact Us']")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.titleContains("Contact us"));
        assertTrue(true);
    }
}
