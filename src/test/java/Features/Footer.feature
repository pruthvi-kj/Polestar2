Feature: Verify the navigation in Footer

  @Footer
  Scenario Outline: Verify the navigation to different pages from the Polestar 2 US page
    Given User is in "<website>" page
    When user navigates to footer
    And clicks on "<link>"
    Then Verify the user lands on "<page>"
    And when user clicks on back verify that back user lands on Polestar 2 homepage

    Examples:
      | website | link | page |
      |https://www.polestar.com/us/polestar-2/|Home|Pure progressive performance * Polestar|
      |https://www.polestar.com/us/polestar-2/|Polestar 1|Polestar 1 – The hybrid electric performance car * Polestar US|
      |https://www.polestar.com/us/polestar-2/|Polestar 2|Polestar 2 – The 100% electric car * Polestar US|
      |https://www.polestar.com/us/polestar-2/|News|Polestar - News|
      |https://www.polestar.com/us/polestar-2/|Service points|Local service points for your Polestar * Polestar|
      |https://www.polestar.com/us/polestar-2/|Support|Owners manual|
      |https://www.polestar.com/us/polestar-2/|Recall information|VIN recall check * Polestar|
      |https://www.polestar.com/us/polestar-2/|Media|Polestar media newsroom - Home|
      |https://www.polestar.com/us/polestar-2/|Contact us|Contact us for personal support * Polestar|
      |https://www.polestar.com/us/polestar-2/|Cookies|Pure progressive performance * Polestar|
      |https://www.polestar.com/us/polestar-2/|Privacy|Pure progressive performance * Polestar|
      |https://www.polestar.com/us/polestar-2/|Legal|Polestar legal|
      |https://www.polestar.com/us/polestar-2/|Download App|Polestar.com * Download the Polestar app|
      |https://www.polestar.com/us/polestar-2/|Careers|Careers|
      |https://www.polestar.com/us/polestar-2/|FAQ|FAQ – Top questions about electric cars * Polestar US|
      |https://www.polestar.com/us/polestar-2/|Facebook|Polestar - 1,483 photos - Cars - |
      |https://www.polestar.com/us/polestar-2/|Twitter|Polestar (@PolestarCars) / Twitter|
      |https://www.polestar.com/us/polestar-2/|YouTube|Polestar - YouTube|
      |https://www.polestar.com/us/polestar-2/|Subscribe| Newsletter sign up|

  @Footer
  Scenario: Verify the Change Location link from Polestar 2 homepage
    Given User is in "https://www.polestar.com/us/polestar-2/" page
    When user navigates to footer
    And clicks on "Change Location"
    Then Verify the user lands on "Select your region"
    And when user clicks on close user lands on Polestar 2 homepage