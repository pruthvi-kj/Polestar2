Feature: Verify Electric Driving page

  Background: User is in the relevant page
    Given User is in "ElectricDriving" page

  @Desktop
  Scenario: Verify navigation using Tab Headings
    When user navigates to "tab headings"
    And user clicks on tab
    | Range & Charging |
    | Sustainability |
    | Savings |
    | Maintenance |
    | Driving |
    Then verify that user lands on the selected section

  @Desktop
  Scenario: Verify that on clicking on Learn More user lands on modal and able to click on all tab headings
    When clicks on Learn More under section
      | Driving |
      | Range & Charging |
      | Sustainability |
    Then Verify the user lands on modal

  @Desktop
Scenario: Verify Electric Driving links
Then verify that all the links are valid

      @Desktop
  Scenario: Verify the time calculation under Charging modal
    When clicks on Learn More under "Range & Charging" section
    And the configuration to set as below
      | ChargerType | PowerOutput | Start% | End% |
      | Home Charging | 3.7 | 20 | -10 |
      | Public Charging | 150 | 20 | 10 |
    Then verify the charge time calculated
#
  @Desktop
    Scenario: Verify the range calculator
      When user navigates to "Range & Charging"
      And user update the slider position
        | 20 |
        | 60 |
      Then verify the miles calculated