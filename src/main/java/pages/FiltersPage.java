package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class FiltersPage {

	public WebDriver driver;
	public By rowsCountFilter = By.xpath("//div[contains(@class, 'table-control-page-sizer')]/div");
	public By rowsValue = By.xpath("//button[contains(text(),'100')]");
	public By rowsDisplayed = By.xpath("//table[contains(@class, 'cmc-table')]/tbody/tr");
	public By filtersButton = By.xpath("//div/button[contains(@class,'table-control-filter')]");
	public By addFilter = By.xpath("//body/div[@id='__next']/div[1]/div[1]/div[2]/div[1]/div[1]/ul[1]/li[5]/button[1]");
	public By marketCapButton = By.xpath("//button[text()='Market Cap']");
	public By oneBToTenBButton = By.xpath("//button[text()='$1B - $10B']");
	public By priceButton = By.xpath("//button[text()='Price']");
	public By priceValButton = By.xpath("//button[text()='$101 - $1,000']");
	public By applyFilterButton = By.xpath("//button[text()='Apply Filter']");
	public By showResultsButton = By.xpath("//button[text()='Show results']");
	public By gotItButton = By.xpath("//button[contains(text(),'Got it')]");
	public By closeCookiesPopUp = By.className("cmc-cookie-policy-banner__close");

	public By nextPageButton = By.xpath("(//a[@aria-label='Next page'])[2]");

	public FiltersPage(WebDriver driver) {
		this.driver = driver;

	}

	public By getRowsDropdownOption(int count) {
		return By.xpath("//button[contains(text(),'" + count + "')]");
	}

}
