package webdemo.seleniumDemo;

import io.github.bonigarcia.seljup.Options;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.seljup.SeleniumExtension;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SeleniumExtension.class)
public class InfUgTest {

    @Options
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    {
        firefoxOptions.setHeadless(true);
        firefoxOptions.addPreference("intl.accept_languages", "en");
    }
	@AfterEach
	public void tearDown(FirefoxDriver driver) throws Exception {
		driver.quit();
	}

	@Test
	public void getAmountOfLinks(FirefoxDriver driver){
        driver.get("https://inf.ug.edu.pl/");
		List<WebElement> elements = driver.findElements(By.xpath("//a[@href and string-length(@href)!=0]"));
		System.out.println("Found "+elements.size()+" links on https://inf.ug.edu.pl/");
		assertFalse(elements.isEmpty());
	}

	@Test
	public void getAmountOfImages(FirefoxDriver driver){
        driver.get("https://inf.ug.edu.pl/");
		List<WebElement> elements = driver.findElements(By.xpath("//img[@src and string-length(@src)!=0]"));
		System.out.println("Found "+elements.size()+" images on https://inf.ug.edu.pl/");
		assertFalse(elements.isEmpty());
	}
	@Test
	public void getAllLinksEnterEachOfThemAndReturn(FirefoxDriver driver){
        driver.get("https://inf.ug.edu.pl/");
		String startTitle = driver.getTitle();
		Stream<String> hrefs =
				driver
				.findElements(By.xpath("//a[@href and string-length(@href)!=0 ]"))
				.stream()
				.map(x -> x.getAttribute("href"))
				.filter(x-> !x.endsWith("xml"))
				.filter(x -> !x.startsWith("mailto"));
		for (String href :
				hrefs.collect(Collectors.toList())) {
			driver.get(href);
			driver.navigate().back();
		}
		assertEquals(startTitle, driver.getTitle());
	}
	@Test
	public void getFormAllChilds(FirefoxDriver driver){
		driver.get("https://inf.ug.edu.pl/awaria");
		List<WebElement> formElements = driver.findElement(By.xpath("//form")).findElements(By.xpath("./*"));
		System.out.println("Found "+formElements.size()+" elements at form on https://inf.ug.edu.pl/awaria");
		assertTrue(formElements.size() >= 6);
	}
}
