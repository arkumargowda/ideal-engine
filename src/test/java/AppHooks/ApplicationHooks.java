package apphooks;

import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
/*
 * This class contains application hooks to execute before and after a test
 */
public class ApplicationHooks {
	
	private DriverFactory  driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	public static Properties prop;
	
	/*
	 * This hook will initialize the driver based on the browser mentioned in the config properties file
	 */
	@Before("@UITests")
	public void getBrowser() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);
	}
	
	/*
	 * This hook will take screenshot if the test has failed. Quits the browser. 
	 */
	@After("@UITests")
	public void quitBrowser(Scenario scenario) {
		if(scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sroucePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sroucePath, "image/png", screenshotName);
		}
		driver.quit();
	}

}
