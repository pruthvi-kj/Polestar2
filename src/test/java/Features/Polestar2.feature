Feature: Verify Polestar2 page

  Background: Which page to be loaded
    Given User lands on "https://yml.dev.devhouse.digital/us/yml/pdp" page and has to work on "Polestar2"


#  @Desktop
#  Scenario: Verify the navigation to different pages from the footer of Polestar 2 US page
#    Given User is in "Polestar2" page
#    When we read the Web Elements data from excel "src/main/resources/excel.xlsx" and sheet "Locators"
#    And extract the required data
#    Then write extracted data to excel "src/main/resources/excel.xlsx"
#
#    @Desktop
#    Scenario: Verify that when user clicks on Reserve Now will take the user to Configurator Flow
#      Given User is in "Polestar2" page
#      When clicks on "Order Now"
#      Then Verify the user lands on "Configurator page"

#  @Desktop
#  Scenario: Verify that when user clicks on See More on Exterior design will take the user to Exterior design modal
#    Given User is in "Polestar2" page
#    When user navigates to "Exterior PDP"
#    And clicks on "See More" option for "Panoramic glass roof"
#    Then Verify the user lands on "Exterior Modal"


  @Desktop
      Scenario: Verify that user lands on Exterior Design PDP
        Given User is in "Polestar2" page
        When user navigates to "Exterior PDP"
        Then Verify the user lands on "Exterior PDP"





