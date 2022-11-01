package testrunners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

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
