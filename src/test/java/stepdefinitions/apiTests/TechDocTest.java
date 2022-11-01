package stepdefinitions.apiTests;

import org.hamcrest.Matchers;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;

public class TechDocTest extends APITestPrequisite {

	ValidatableResponse response;
	
	@Given("Retrieve the Ethereum technical documentation from the cryptocurrency\\/info call")
	public void retrieve_the_ethereum_technical_documentation_from_the_cryptocurrency_info_call() {
		response = getRequestSpecification()
		.param("id", 1027)
		.get("/v2/cryptocurrency/info").then();
	}

	@Then("Confirm that the following logo URL is present")
	public void confirm_that_the_following_logo_url_is_present() {
		response.assertThat().body("data.1027.logo", Matchers.is("https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"));
	}

	@Then("Confirm that the technical_doc Url is present")
	public void confirm_that_the_technical_doc_url_is_present() {
		response.assertThat().body("data.1027.urls.technical_doc", Matchers.hasItem("https://github.com/ethereum/wiki/wiki/White-Paper"));
	}

	@Then("Confirm that the symbol of the currency is ETH")
	public void confirm_that_the_symbol_of_the_currency_is_eth() {
	   response.assertThat().body("data.1027.symbol", Matchers.is("ETH"));
	}

	@Then("Confirm that the date added is as expected")
	public void confirm_that_the_date_added_is_as_expected() {
		   response.assertThat().body("data.1027.date_added", Matchers.is("2015-08-07T00:00:00.000Z"));
	}

	@Then("Confirm that the currency has the mineable tag associate with it")
	public void confirm_that_the_currency_has_the_mineable_tag_associate_with_it() {
	    response.assertThat().body("data.1027.tags", Matchers.hasItem("mineable"));
	}
}
