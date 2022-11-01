package testrunners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/*
 * Runner file for running both UI and API tests
 */
@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/appfeatures"},
		glue ={"stepdefinitions", "apphooks"},
				 plugin = {
	                        "pretty",
	                        "json:target/cucumber.json",
	                        "html:target/cucumber.html"},

		publish = false
		
		)
public class MyTestRunner {

}
