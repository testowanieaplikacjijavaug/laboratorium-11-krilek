package webdemo.seleniumDemo;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.seljup.Arguments;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
@ExtendWith(SeleniumExtension.class)
public class GoogleTest {
	
	private WebDriver driver;
	public GoogleTest(@Arguments("--headless") FirefoxDriver driver){
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	@BeforeEach
	public void setUp() throws Exception {
		driver.get("https://duckduckgo.com/");
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
	}

	@Test
	public void testTitlePage() {
    	assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
	}

	@Test
	public void testSource(){
		String source = driver.getPageSource();
		assertTrue(source.contains("DuckDuckGo"));
	}
	@Test
	public void checkSearchInputById(){
		assertTrue(isElementPresent(By.id("search_form_input_homepage")));
	}

	@Test
	public void checkSearchButtonByCss(){
		assertTrue(isElementPresent(By.cssSelector("#search_button_homepage")));
	}

	@Test
	public void checkTitleByClassName(){
		assertTrue(isElementPresent(By.className("badge-link__title")));
	}

	@Test
	public void checkSearchButtonByXPath(){
		assertTrue(isElementPresent(By.xpath("//input[@type='submit']")));
	}

	@Test
	public void testClick(){
		driver.findElement(By.id("search_form_input_homepage")).sendKeys("Mateusz Miotk");
		driver.findElement(By.id("search_button_homepage")).click();
		assertEquals("Mateusz Miotk at DuckDuckGo", driver.getTitle());
	}

}
