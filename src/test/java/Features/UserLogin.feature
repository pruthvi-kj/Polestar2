Feature: Verify Service And Assistance page

  Background: User is relevant page
    Given User is in "Polestar2" page for "US" route

  @Desktop @Login
  Scenario: Verify the user login
    Given User navigates to "PolestarLogin" page
    When User enters "Email Address"
    And User enters "Password"
    And clicks on "Login"
    Then user should be signed in