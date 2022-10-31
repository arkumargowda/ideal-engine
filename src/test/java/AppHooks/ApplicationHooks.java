package apphooks;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;

public class ApplicationHooks {
	
	private DriverFactory  driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	public static Properties config;
	private By gotItButton = By.xpath("//button[contains(text(),'Got it')]");
	private By closeCookiesPopUp = By.className("cmc-cookie-policy-banner__close");
	
	@Before(order=0)
	public void getProperty() {
		
		configReader = new ConfigReader();
		config = configReader.init_prop();
		
	}
	
	@Before(order=1)
	public void launchBrowser() {
		String browserName = config.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);
	}

	@Before(order=2)
	public void acceptPopUpIfPresent() {
		if(driver.findElements(gotItButton).size()==1) {
			driver.findElement(gotItButton).click();
		}
	}
	
	@Before(order=3)
	public void closeCookiesPopUp() {
		if(driver.findElements(closeCookiesPopUp).size()==1) {
			driver.findElement(closeCookiesPopUp).click();
		}
	}
	
	@After(order=0)
	public void quitBrowser() {
		driver.quit();
	}
	
	@After(order=1)
	public void tearDown(Scenario scenario) {
		if(scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sroucePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sroucePath, "image/png", screenshotName);
		}
		
	}

}
