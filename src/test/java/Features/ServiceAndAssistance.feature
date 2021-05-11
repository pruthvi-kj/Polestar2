Feature: Verify Service And Assistance page

  Background: User is relevant page
    Given User is in "ServiceAndAssistance" page

  @Desktop @SA
  Scenario: Verify navigation using Tab Headings
    When user navigates to "tab headings"
    And user clicks on tab
      | Maintenance |
      | Service points |
      | Questions |
      | Roadside assistance |
    Then verify that user lands on the selected section

  @Desktop @SA
  Scenario: Verify that on clicking on Learn More, user lands on modal
    When clicks on Learn More under "Maintenance" section
    Then Verify the user lands on modal

  @Desktop @SA
  Scenario: Verify that on clicking on Learn More, user lands on modal
    When clicks on service point under "Service points" section
      | Los Angeles |
      | San Jose |
      | Boston |
    Then Verify the user lands on selected service point modal


  @Desktop @SA
  Scenario: Verify Service And Assistance links
    Then verify that all the links are valid