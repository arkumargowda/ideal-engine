package stepdefinitions.uiTests;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FiltersPage;
import utils.CompareBigNumers;
import utils.ElementUtils;

/*
 * This is a stepdefinition file for Filter tests
 */
public class FilterTests {
	
	private FiltersPage filtersPage = new FiltersPage(DriverFactory.getDriver());
	WebDriver driver = DriverFactory.getDriver();
	
	@Given("open the url {string}")
	public void open_the_url(String url) { 
		driver.get(url);
		
		//code to close the info box
		new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.elementToBeClickable(filtersPage.infoCloseButton)).click();	
		
		//code to close the cookies footer
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(filtersPage.closeCookiesPopUp)).click();	
	}
	
	@When("user selects show rows dropdown to {int}")
	public void user_selects_show_rows_dropdown_to(Integer rowsCount) {
		ElementUtils.scrollToView(driver, filtersPage.rowsCountFilter);
		driver.findElement(filtersPage.rowsCountFilter).click();
		new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(filtersPage.rowsCountDropDown));
		driver.findElement(filtersPage.getRowsDropdownOption(rowsCount)).click();
	}
	

	@Then("{int} rows should be dispalyed")
	public void rows_should_be_dispalyed(Integer expectedRowsCount) {
		ElementUtils.waitForProgressBarToComplete(driver);
		Integer actualRowsCount = driver.findElements(filtersPage.rowsDisplayed).size();
		Assert.assertEquals(actualRowsCount, expectedRowsCount);
	    
	}
	
	@When("click on Filters button")
	public void click_on_filters_button() {
		ElementUtils.scrollToView(driver, filtersPage.filtersButton);
		driver.findElement(filtersPage.filtersButton).click();
		new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(filtersPage.addFilter));
		Assert.assertTrue(driver.findElement(filtersPage.addFilter).isDisplayed());
	}



	@When("filter records by MarketCap:$1B-$10B and Price:${int}-${int}")
	public void filter_records_by_market_cap_$1b_$10b_and_price_$_$(Integer int1, Integer int2) {
		driver.findElement(filtersPage.addFilter).click();
		
		//apply marketCap filter
		driver.findElement(filtersPage.marketCapButton).click();
		driver.findElement(filtersPage.oneBToTenBButton).click();
		ElementUtils.scrollToView(driver, filtersPage.applyFilterButton);
		driver.findElement(filtersPage.applyFilterButton).click();
		ElementUtils.waitForProgressBarToComplete(driver);

		//apply price filter
		ElementUtils.scrollToView(driver, filtersPage.priceButton);
		driver.findElement(filtersPage.priceButton).click();
		driver.findElement(filtersPage.priceValButton).click();
		ElementUtils.scrollToView(driver, filtersPage.applyFilterButton);
		driver.findElement(filtersPage.applyFilterButton).click();
		ElementUtils.waitForProgressBarToComplete(driver);

		
		
		driver.findElement(filtersPage.showResultsButton).click();
	}

	
	@Then("records displayed as per the filters applied")
	public void records_displayed_as_per_the_filters_applied() {
		
		//code to iterate over pagination and capture all the rows
		List<WebElement> rows = driver.findElements(filtersPage.rowsDisplayed);
		while (driver.findElement(filtersPage.nextPageButton).getAttribute("aria-disabled").equals("false")) {
			ElementUtils.scrollToView(driver, filtersPage.nextPageButton);
			driver.findElement(filtersPage.nextPageButton).click();
			ElementUtils.waitForProgressBarToComplete(driver);
			rows.addAll(driver.findElements(filtersPage.rowsDisplayed));
		}

		//code to retrieve marketCap and price values from the grid
		Map<Object, Object> priceMap = rows.stream()
				.collect(Collectors.toMap(ele -> ele.findElement(By.xpath("./td[3]")).getText(),
						ele -> ele.findElement(By.xpath("./td[4]")).getText().substring(1)));
		Map<Object, Object> marketCapMap = rows.stream()
				.collect(Collectors.toMap(ele -> ele.findElement(By.xpath("./td[3]")).getText(),
						ele -> ele.findElement(By.xpath("./td[8]")).getText().substring(1)));

		//code for price validation
		priceMap.forEach((a, b) -> {
			float price = Float.parseFloat(b.toString());
			Assert.assertTrue("Price of " + a + " is within selected price range",price >= 101 && price <= 1000);
			System.out.println("Price of " + a + " is "+price+" which is between $101-$1000");
		});
		
		
		//code for marketCap validation
		marketCapMap.forEach((a, b) -> {

			BigInteger marketCap = BigInteger.valueOf(Long.valueOf(b.toString().replaceAll(",", "").toString()));
			BigInteger min = BigInteger.valueOf(1000000000L);
			BigInteger max = BigInteger.valueOf(10000000000L);
			Assert.assertTrue("Market Cap of " + a + " is within selected market Cap range",CompareBigNumers.greaterOrEqual(marketCap, min) && CompareBigNumers.lesserOrEqual(marketCap, max));
			System.out.println("Market Cap of " + a + " is "+marketCap+" which is between $1B-$10B");
		});

	}
}
