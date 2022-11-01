package stepdefinitions.apiTests;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TagVerificationTest {

	Response response;

	
	@Given("Retrieve the first ten currencies from the cryptocurrency\\/info call")
	public void retrieve_the_first_ten_currencies_from_the_cryptocurrency_info_call() {
		RestAssured.baseURI="https://pro-api.coinmarketcap.com";
		response = RestAssured.given()
		.header("X-CMC_PRO_API_KEY","696f7d0e-c3e6-47ac-8135-d3852c39e2fb")
		.param("id", "1,2,3,4,5,6,7,8,9,10")
		.get("/v2/cryptocurrency/info");
	}

	@Then("Check which currencies have the mineable tag associated with them")
	public void check_which_currencies_have_the_mineable_tag_associated_with_them() {
		JsonPath j = new JsonPath(response.asString());
		for(int i=1;i<10;i++) {			
			String symbol = j.get("data."+i+".symbol");
			List<String> tags = j.getList("data."+i+".tags");
			Assert.assertTrue(tags.contains("mineable"), symbol+" contains miniable tag");
		}
		
	}

	@Then("Verify that correct cryptocurrencies have been printed out")
	public void verify_that_correct_cryptocurrencies_have_been_printed_out() {
		FileInputStream fis;
		Properties prop = null;
		try {
			 fis = new FileInputStream("./src/test/resources/config/CryptoCurrencies.properties");
			 prop = new Properties();
			 prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonPath j = new JsonPath(response.asString());
		for(int i=1;i<10;i++) {			
			String symbol = j.get("data."+i+".symbol");
			Assert.assertTrue(prop.getProperty(String.valueOf(i)).equals(symbol), symbol+" is the currect currency returned for id "+i);
		}
	
	}
}
