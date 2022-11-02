package stepdefinitions.apiTests;

import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

/*
 * This is a base class for all API tests of https://pro-api.coinmarketcap.com application
 */
public class APITestPrequisite {
	
	/*
	 * This returns RequestSpecification with having baseUri and authentication header set
	 */
	public RequestSpecification getRequestSpecification() {
		
		Properties configProp = new ConfigReader().init_prop();
		
		return RestAssured.given()
				.baseUri("https://pro-api.coinmarketcap.com")
				.header("X-CMC_PRO_API_KEY",configProp.getProperty("key"));
		
	}

}
