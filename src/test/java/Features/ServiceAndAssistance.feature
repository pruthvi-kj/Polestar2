Feature: Verify Service And Assistance page

#  @Desktop
#  Scenario: Verify navigation using Tab Headings
#    Given User is in "ServiceAndAssistance" page
#    And user navigates to "tab headings"
#    When user clicks on "Maintenance" tab
#    Then verify that user lands on the same section
#
#  @Desktop
#  Scenario: Verify that on clicking on Learn More, user lands on modal
#    Given User is in "ServiceAndAssistance" page
#    When clicks on Learn More under "Maintenance" section
#    Then Verify the user lands on modal

  @Desktop
  Scenario: Verify that on clicking on Learn More, user lands on modal
    Given User is in "ServiceAndAssistance" page
    When clicks on "Los Angeles" service point under "Service points" section
    Then Verify the user lands on selected service point modal