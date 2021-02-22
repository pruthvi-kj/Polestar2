Feature: Verify Polestar2 page

  Background: Which page to be loaded
    Given User lands on "https://user:P0L3574R@yml.dev.devhouse.digital/us/polestar-2/pdp" page and has to work on "Polestar2"


#  @Desktop
#  Scenario: Verify the font size, colour, text and style of any web element
#    Given User is in "Polestar2" page
#    When we read the Web Elements data from excel "src/main/resources/excel.xlsx" and sheet "Locators"
#    And extract the required data
#    Then write extracted data to excel "src/main/resources/excel.xlsx"
#
#    @Mobile
#    Scenario: Verify that when user clicks on Reserve Now will take the user to Configurator Flow
#      Given User is in "Polestar2" page
#      When clicks on "Order Now"
#      Then Verify the user navigates to "Polestar 2 - Configure your Polestar online | Polestar US"

#  @Desktop
#  Scenario: Verify that when user clicks on See More on Exterior design will take the user to Exterior design modal
#    Given User is in "Polestar2" page
#    When user navigates to "Exterior PDP"
#    And clicks on "See More" option for "Panoramic glass roof"
#    Then Verify the user lands on "Exterior Modal"

#
#  @Desktop
#      Scenario: Verify that user lands on Exterior Design PDP
#        Given User is in "Polestar2" page
#        When user navigates to "Exterior PDP"
#        Then Verify the user lands on "Exterior PDP"

#  @Desktop
#  Scenario: Verify that Book home a test drive takes user to the Polestar 2 test drive page for US region
#    Given User is in "Polestar2" page
#    When user navigates to "Hero Unit"
#    And clicks on "Book a test drive"
#    Then Verify the user navigates to "Book a Polestar test | Polestar US"


  @Desktop
  Scenario Outline: Verify the Callouts for all the sections present in the PDP
    Given User is in "Polestar2" page
    When extract all the callout for the section "<section>"
    Then if all the callouts are matching
    Examples:
      | section      |
      | Safety       |








