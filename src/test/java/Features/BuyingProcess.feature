Feature: Verify Buying Process page

  Background: User is in the relevant page
    Given User is in "BuyingProcess" page

  @Desktop @BP
  Scenario: Verify navigation using Tab Headings
    When user navigates to "tab headings"
    And user clicks on tab
      | Order     |
      | Delivery  |
      | Questions |
      | Customize |
    Then verify that user lands on the selected section
#
#  @Desktop @BP
#  Scenario: Verify that on clicking on Learn More, user lands on modal
#    When clicks on Spaces under "Delivery" section
#      | Los Angeles   |
#      | Marin         |
#      | New York City |
#    Then Verify the user lands on selected Spaces modal
#
#  @Desktop @BP
#  Scenario: Verify Buying Process links
#    Then verify that all the links are valid