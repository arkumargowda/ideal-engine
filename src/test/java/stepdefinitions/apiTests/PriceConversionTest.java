package stepdefinitions.apiTests;

import java.util.List;

import org.testng.Assert;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;


public class PriceConversionTest {
	
	int id;
	
	@Given("Retrive the ID of {string} using the \\/cryptocurrency\\/map call")
	public void retrive_the_id_of_using_the_cryptocurrency_map_call(String symbol) {
		RestAssured.baseURI="https://pro-api.coinmarketcap.com";
		String body = RestAssured.given()
		.header("X-CMC_PRO_API_KEY","696f7d0e-c3e6-47ac-8135-d3852c39e2fb")
		.get("/v1/cryptocurrency/map").then().extract().body().asString();
		List<Integer> idList = JsonPath.read(body, "$.data[?(@.symbol == '"+symbol+"')].id"); 
		id = idList.get(0);
		System.out.println("Retrieved ID for "+symbol+" is "+id);
		Assert.assertTrue(true, "Retrieved ID for "+symbol+" is "+id);
	}

	@Then("Once you have retrived the ID of {string} convert it to Bolivian Boliviano using the \\/tools\\/price-conversion\\/ call")
	public void once_you_have_retrived_the_id_of_convert_it_to_bolivian_boliviano_using_the_tools_price_conversion_call(String symbol) {
		RestAssured.baseURI="https://pro-api.coinmarketcap.com";
		io.restassured.path.json.JsonPath response = RestAssured.given()
				.header("X-CMC_PRO_API_KEY","696f7d0e-c3e6-47ac-8135-d3852c39e2fb")
				.param("amount", 1)
				.param("convert", "BOB")
				.param("id", id).get("v2/tools/price-conversion").then().extract().jsonPath();
		String conversionPrice = response.getString("data.quote.BOB.price").toString();
		System.out.println("Conversion price for "+symbol+ " in BOB is "+conversionPrice);
		Assert.assertTrue(true, "Conversion price for "+symbol+ " in BOB is "+conversionPrice);
	}
	
	
	
		

}
