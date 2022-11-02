package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * 
 * This class contains javascript implementation for ui actions
 */
public class ElementUtils {

	/**
	 * @
	 * @apiNote This method is used to scroll to element
	 * @param driver
	 * @param locator
	 */
	public static void scrollToView(WebDriver driver, By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
	}
	
	/**
	 * @apiNote This method is used to wait for the progress bar to complete
	 * @param driver
	 */
	public static void waitForProgressBarToComplete(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[@class='nprogress-busy']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html[@class='nprogress-busy']")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[@class=' ']")));

	}
}


