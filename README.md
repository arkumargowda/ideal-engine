# ideal-engine
Project for UI and API automated tests developed using Selenium, RestAssured and cucumber in java language.

## Tests automated
### UI Tests
* Verifying rowsCount filter 
* Verifying marketCap and Price filters
### API Tests
* Price Conversion test
* Technical doc validation
* Tag verification


## How to run?
* Clone this project to any machine where java, maven and chrome browsers are installed.
* Navigate to the cloned project folder in command line.
* Type the command ``` mvn clean test ``` and hit enter.

## Reports
Link to test execution report can be found in the logs at the end. Copy the link to any browser to view the report.

## <span color="red">Note</span>:
```diff 
-Technical doc validation test will fail as 'minimal' tag which is not present in the response.
```
