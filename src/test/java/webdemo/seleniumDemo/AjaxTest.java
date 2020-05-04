package webdemo.seleniumDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AjaxTest {
    //Przykłady znajdowania elementów na stronie www z wykorzystaniem xpath

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        options.addPreference("intl.accept_languages", "en");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    @Test
    public void checkButtonRequestAjax(){
        driver.get("http://book.theautomatedtester.co.uk/chapter1");
        assertFalse(isElementPresent(By.cssSelector("#html5div div")));

        driver.findElement(By.id("secondajaxbutton")).click();
        WebElement message = new WebDriverWait(driver, 5)
                .until(new ExpectedCondition<WebElement>(){
                    @Override
                    public WebElement apply(WebDriver d){
                        return d.findElement(By.cssSelector("#html5div div"));
                    }
                });
        assertEquals("I have been added with a timeout", message.getText());
    }

    @Test
    public void checkLinkRequestAjax(){
        driver.get("http://book.theautomatedtester.co.uk/chapter1");
        assertFalse(isElementPresent(By.cssSelector("#ajaxdiv p")));

        driver.findElement(By.id("loadajax")).click();
        WebElement message = new WebDriverWait(driver, 5)
                .until(new ExpectedCondition<WebElement>(){
                    @Override
                    public WebElement apply(WebDriver d){
                        return d.findElement(By.cssSelector("#ajaxdiv p"));
                    }
                });
        assertEquals("The following text has been loaded from another page on this site. It has been loaded in an asynchronous fashion so that we can work through the AJAX section of this chapter", message.getText());
    }

    @Test
    public void checkThatSearchContextMenuShowsUp(){
        driver.get("http://automationpractice.com/index.php");

        assertFalse(isElementPresent(By.className("ac_results")));

        driver.findElement(By.id("search_query_top")).sendKeys("dress");
        WebElement message = new WebDriverWait(driver, 5)
                .until(new ExpectedCondition<WebElement>(){
                    @Override
                    public WebElement apply(WebDriver d){
                        return d.findElement(By.cssSelector("div.ac_results ul li:first-child"));
                    }
                });
        assertTrue(message.getText().toLowerCase().contains("dress"));
    }

}
