Feature: Filters feature on home page

Scenario Outline: Frontend Task1 - Verify the rows count filter
Given open the url "https://coinmarketcap.com"
When user selects show rows dropdown to value to <count>
Then <count> rows should be dispalyed

Examples:
	|count  |
	|100		|
	|50			|
	|20			|

Scenario: Frontend Task2 - Verify the Market Cap and Price filters
Given open the url "https://coinmarketcap.com"
When click on Filters button
And filter records by MarketCap:$1B-$10B and Price:$101-$1000
Then records displayed as per the filters applied
