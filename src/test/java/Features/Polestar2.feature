Feature: Verify Polestar2 page

  Background: User is relevant page
    Given User is in "Polestar2" page

##  @Desktop
##  Scenario: Verify the font size, colour, text and style of any web element
##    Given User is in "Polestar2" page
##    When we read the Web Elements data from excel "src/tes/resources/excel.xlsx" and sheet "Locators"
##    And extract the required data
##    Then write extracted data to excel "src/test/resources/excel.xlsx"
##
##    @Mobile
##    Scenario: Verify that when user clicks on Reserve Now will take the user to Configurator Flow
##      Given User is in "Polestar2" page
##      When clicks on "Order Now"
##      Then Verify the user navigates to "Polestar 2 - Configure your Polestar online | Polestar US"
##
##  @Desktop
##  Scenario: Verify that when user clicks on See More on Exterior design will take the user to Exterior design modal
##    Given User is in "Polestar2" page
##    When user navigates to "Exterior PDP"
##    And clicks on "See More" option for "Panoramic glass roof"
##    Then Verify the user lands on "Exterior Modal"
##
##  @Desktop
##  Scenario Outline: Verify the Callouts for all the sections present in the PDP
##    Given User is in "Polestar2" page
##    When extract all the callout for the section "<section>"
##    Then if all the callouts are matching
##    Examples:
##      | section |
##      | Safety  |

#  @Desktop
#  Scenario: Verify that Book home a test drive takes user to the Polestar 2 test drive page for US region
#    When user navigates to "Hero"
#    And clicks on "Book a test drive"
#    Then Verify the user navigates to "Book a Polestar test drive | Polestar US"

#  @Desktop
#    Scenario: Verify the range calculator
#      When user navigates to "Range"
#      And user update the slider position
#        | 20 |
#        | 60 |
#      Then verify the miles calculated

  @Desktop
  Scenario: Verify that on clicking on Learn More user lands on modal and able to click on all tab headings
    When clicks on Learn More under section
      | Interior |
      | Safety |
      | Infotainment |
    Then Verify the user lands on modal
#
#  @Desktop
#  Scenario: Verify that user is able to click on all tab headings within modal
#    When clicks on Learn More under "Range" section
#    Then Verify that all the tab headings are clickable
#
#  @Desktop
#  Scenario: Verify that on clicking on See More in design feature intro, user lands on modal
#    When clicks on See More under feature
#      | Panoramic glass roof |
#      | Frameless mirrors |
#    Then Verify the user lands on modal
#
#  @Desktop
#  Scenario: Verify the time calculation under Charging modal
#    When clicks on Learn More under "Range" section
#    And the configuration to set as below
#      | ChargerType | PowerOutput | Start% | End% |
#      | Home Charging | 3.7 | 20 | -10 |
#      | Public Charging | 150 | 20 | 10 |
#    Then verify the charge time calculated
#
#  @Desktop
#  Scenario: Verify the Savings calculation for US
#    When user navigates to "Range"
#    And user slides upto 40 px
#    Then verify the savings
#
#  @Desktop
#  Scenario: Verify the Savings calculation for US
#    When user navigates to "Range"
#    And user slides upto 40 px
#    And selects "Maryland" as the state
#    Then verify the savings
#
#  @Desktop
#  Scenario: Verify the Savings calculation for US
#    When user navigates to "Range"
#    Then verify the savings
#
#  @Desktop
#  Scenario Outline: Verify the Savings calculation for US
#    When user navigates to "Range"
#    And selects "<State>" as the state
#    Then verify the savings
#    Examples:
#      | State |
#      | Arizona |
#      | Maryland |
#      | California |
#      | Washington |
#
#  @Desktop
#  Scenario Outline: Verify navigation to sections using nav bar
#    When user clicks on section in nav bar
#      | Exterior |
#      | Interior |
#    Then verify that user lands on the selected section
#    Examples:
#
#  @Desktop
#  Scenario: Verify Polestar 2 links
#    Then verify that all the links are valid
#
#Scenario: junk
#  Given  some shit