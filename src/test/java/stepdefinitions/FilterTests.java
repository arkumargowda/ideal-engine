package stepdefinitions;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FiltersPage;
import utils.CompareBigNumers;
import utils.JSUtils;


public class FilterTests {
	
	private FiltersPage filtersPage = new FiltersPage(DriverFactory.getDriver());
	WebDriver driver = DriverFactory.getDriver();
	
	@Given("open the url {string}")
	public void open_the_url(String url) { 
		driver.get(url);
		new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.elementToBeClickable(filtersPage.infoCloseButton)).click();	
			new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(filtersPage.closeCookiesPopUp)).click();	
	}
	
	@When("user selects show rows dropdown to value to {int}")
	public void user_selects_show_rows_dropdown_to_value_to(Integer rowsCount) {
		JSUtils.scrollToView(driver, filtersPage.rowsCountFilter);
		driver.findElement(filtersPage.rowsCountFilter).click();
		driver.findElement(filtersPage.getRowsDropdownOption(rowsCount)).click();
		JSUtils.waitForPageLoad(driver);
	}
	

	@Then("{int} rows should be dispalyed")
	public void rows_should_be_dispalyed(Integer expectedRowsCount) {
		int actualRowsCount = driver.findElements(filtersPage.rowsDisplayed).size();
		Assert.assertEquals(actualRowsCount, actualRowsCount);
	    
	}
	
	@When("click on Filters button")
	public void click_on_filters_button() {
		JSUtils.scrollToView(driver, filtersPage.filtersButton);
		driver.findElement(filtersPage.filtersButton).click();
		JSUtils.waitForPageLoad(driver);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(driver.findElement(filtersPage.addFilter).isDisplayed());
	}



	@When("filter records by MarketCap:$1B-$10B and Price:${int}-${int}")
	public void filter_records_by_market_cap_$1b_$10b_and_price_$_$(Integer int1, Integer int2) {
		driver.findElement(filtersPage.addFilter).click();
		driver.findElement(filtersPage.marketCapButton).click();
		driver.findElement(filtersPage.oneBToTenBButton).click();
		JSUtils.scrollToView(driver, filtersPage.applyFilterButton);
		driver.findElement(filtersPage.applyFilterButton).click();
		JSUtils.scrollToView(driver, filtersPage.priceButton);
		driver.findElement(filtersPage.priceButton).click();
		driver.findElement(filtersPage.priceValButton).click();
		JSUtils.scrollToView(driver, filtersPage.applyFilterButton);
		driver.findElement(filtersPage.applyFilterButton).click();
		driver.findElement(filtersPage.showResultsButton).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	
	@Then("records displayed as per the filters applied")
	public void records_displayed_as_per_the_filters_applied() {
		SoftAssert sa = new SoftAssert();
		List<WebElement> rows = driver.findElements(filtersPage.rowsDisplayed);
		while (driver.findElement(filtersPage.nextPageButton).getAttribute("aria-disabled").equals("false")) {
			JSUtils.scrollToView(driver, filtersPage.nextPageButton);
			driver.findElement(filtersPage.nextPageButton).click();
			rows.addAll(driver.findElements(filtersPage.rowsDisplayed));
		}

		Map<Object, Object> priceMap = rows.stream()
				.collect(Collectors.toMap(ele -> ele.findElement(By.xpath("./td[3]")).getText(),
						ele -> ele.findElement(By.xpath("./td[4]")).getText().substring(1)));

		Map<Object, Object> marketCapMap = rows.stream()
				.collect(Collectors.toMap(ele -> ele.findElement(By.xpath("./td[3]")).getText(),
						ele -> ele.findElement(By.xpath("./td[8]")).getText().substring(1)));

		priceMap.forEach((a, b) -> {
			
			float price = Float.parseFloat(b.toString());
			sa.assertTrue(price >= 101 && price <= 1000, "Price of " + a + " is within selected price range");
		});
		marketCapMap.forEach((a, b) -> {

			BigInteger i = BigInteger.valueOf(Long.valueOf(b.toString().replaceAll(",", "").toString()));
			BigInteger j = BigInteger.valueOf(1000000000L);
			BigInteger k = BigInteger.valueOf(10000000000L);
			sa.assertTrue(CompareBigNumers.greaterOrEqual(i, j) && CompareBigNumers.lesserOrEqual(i, k),
					"Market Cap of " + a + " is within selected market Cap range");
			sa.assertAll();

		});

	}
}
