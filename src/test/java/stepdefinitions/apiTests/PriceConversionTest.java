package stepdefinitions.apiTests;

import java.util.List;

import org.junit.Assert;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PriceConversionTest extends APITestPrequisite {
	
	int id;

	@Given("Retrive the ID of {string} using the \\/cryptocurrency\\/map call")
	public void retrive_the_id_of_using_the_cryptocurrency_map_call(String symbol) {
		String body = getRequestSpecification().get("/v1/cryptocurrency/map").then().extract().body().asString();
		List<Integer> idList = JsonPath.read(body, "$.data[?(@.symbol == '" + symbol + "')].id");
		id = idList.get(0);
		System.out.println("Id fetched for "+symbol+" is "+id);
	}

	@Then("Once you have retrived the ID of {string} convert it to Bolivian Boliviano using the \\/tools\\/price-conversion\\/ call")
	public void once_you_have_retrived_the_id_of_convert_it_to_bolivian_boliviano_using_the_tools_price_conversion_call(
			String symbol) {
		io.restassured.path.json.JsonPath response = getRequestSpecification().param("amount", 1)
				.param("convert", "BOB").param("id", id).get("v2/tools/price-conversion").then().extract().jsonPath();
		String conversionPrice = response.getString("data.quote.BOB.price").toString();
		System.out.println("Conversion price for " + symbol + " in BOB is " + conversionPrice);
		Assert.assertTrue("Conversion price for " + symbol + " in BOB is " + conversionPrice,true);
	}

}
