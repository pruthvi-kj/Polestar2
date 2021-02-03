Feature: Verify Polestar2 page

  Background: Which page to be loaded
    Given User lands on "https://yml.dev.devhouse.digital/us/yml/" page and has to work on "Polestar2"


  @Desktop
  Scenario: Verify the navigation to different pages from the footer of Polestar 2 US page
    Given User is in "Polestar2" page
    When we read the Web Elements data from excel "src/main/resources/excel.xlsx" and sheet "Locators"
    And extract the required data
    Then write extracted data to excel "src/main/resources/excel.xlsx"



