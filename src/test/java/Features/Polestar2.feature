Feature: Verify Polestar2 page

  Background: Which page to be loaded
    Given User lands on "https://user:P0L3574R@yml.staging.devhouse.digital/us/polestar-2-b" page
#    Given User lands on "https://www.polestar.com/us/polestar-2" page


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
#
#  @Desktop
#  Scenario: Verify that when user clicks on See More on Exterior design will take the user to Exterior design modal
#    Given User is in "Polestar2" page
#    When user navigates to "Exterior PDP"
#    And clicks on "See More" option for "Panoramic glass roof"
#    Then Verify the user lands on "Exterior Modal"
#
#
#  @Desktop
#      Scenario: Verify that user lands on Exterior Design PDP
#        Given User is in "Polestar2" page
#        When user navigates to "Exterior PDP"
#        Then Verify the user lands on "Exterior PDP"
#
#  @Desktop
#  Scenario: Verify that Book home a test drive takes user to the Polestar 2 test drive page for US region
#    Given User is in "Polestar2" page
#    When user navigates to "Hero Unit"
#    And clicks on "Book a test drive"
#    Then Verify the user navigates to "Book a Polestar test | Polestar US"
#
#
#  @Desktop
#  Scenario Outline: Verify the Callouts for all the sections present in the PDP
#    Given User is in "Polestar2" page
#    When extract all the callout for the section "<section>"
#    Then if all the callouts are matching
#    Examples:
#      | section |
#      | Safety  |
#
#
    @Desktop
    Scenario: Verify the range calculator
      Given User is in "Polestar2" page
      And user navigates to "Range"
      When user slides upto 20 px
      Then verify the miles calculated

#  @Desktop
#  Scenario: Verify that on clicking on Learn More, user lands on modal
#    Given User is in "Polestar2" page
#    When clicks on Learn More under "Range" section
#    Then Verify the user lands on modal

#  @Desktop
#  Scenario: Verify that user is able to click on all tab headings within modal
#    Given User is in "Polestar2" page
#    When clicks on Learn More under "Range" section
#    Then Verify that all the tab headings are clickable

#  @Desktop
#  Scenario: Verify that on clicking on See More in design feature intro, user lands on modal
#    Given User is in "Polestar2" page
#    When clicks on See More under "Panoramic glass roof" feature
#    Then Verify the user lands on modal

#  @Desktop
#  Scenario: Verify the Home charger
#    Given User is in "Polestar2" page
#    And clicks on Learn More under "Range" section
#    And clicks on "Home charging" section
#    When clicks on 3.7 kW charger
#    And slider positions are set at 15 px and 100 px
#    Then verify the charge time calculated

#  @Desktop
#  Scenario: Verify the Home charger
#    Given User is in "Polestar2" page
#    And user navigates to "Range"
#    When user slides upto 40 px
#    Then verify the savings



