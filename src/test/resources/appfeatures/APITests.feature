Feature: Feature file for backend tests

  @BackendTests
  Scenario Outline: Backend Task1 - PriceConversion
    Given Retrive the ID of <symbol> using the /cryptocurrency/map call
    Then Once you have retrived the ID of <symbol> convert it to Bolivian Boliviano using the /tools/price-conversion/ call

    Examples: 
      | symbol |
      | "BTC"  |
      | "USDT" |
      | "ETH"  |

  @BackendTests
  Scenario: Backend Task2 - Tech doc verification
    Given Retrieve the Ethereum technical documentation from the cryptocurrency/info call
    Then Confirm that the logo URL is present
    And Confirm that the technical_doc Url is present
    And Confirm that the symbol of the currency is ETH
    And Confirm that the date added is as expected
    And Confirm that the currency has the mineable tag associate with it

  @BackendTests
  Scenario: Backend Task3 - Tag verification
    Given Retrieve the first ten currencies from the cryptocurrency/info call
    Then Check which currencies have the mineable tag associated with them
    And Verify that correct cryptocurrencies have been printed out
