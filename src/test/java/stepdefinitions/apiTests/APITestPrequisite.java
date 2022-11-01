package stepdefinitions.apiTests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

/*
 * This is a base class for all API tests of https://pro-api.coinmarketcap.com application
 */
public class APITestPrequisite {
	
	/*
	 * This returns RequestSpecification with having baseUri and authentication header set
	 */
	public RequestSpecification getRequestSpecification() {
		
		return RestAssured.given()
				.baseUri("https://pro-api.coinmarketcap.com")
				.header("X-CMC_PRO_API_KEY","696f7d0e-c3e6-47ac-8135-d3852c39e2fb");
		
	}

}
