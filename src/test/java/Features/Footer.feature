Feature: Verify the navigation in Footer


  Background: Which paltform and browser to be initilaised
    Given User lands on "https://www.polestar.com/us/polestar-2/" page and has to work on "HeaderAndFooter"

  @Footer @Desktop
  Scenario Outline: Verify the navigation to different pages from the footer of Polestar 2 US page
    Given User is in "Polestar2" page
    When user navigates to footer
    And clicks on "<link>"
    Then Verify the user lands on "<page>"
    And when user clicks on back verify that back user lands on Polestar2 homepage

    Examples:
      | link               | page                                                           |
      | Home               | Pure progressive performance * Polestar                        |
      | Polestar 1         | Polestar 1 – The hybrid electric performance car * Polestar US |
      | Polestar 2         | Polestar 2 – The 100% electric car * Polestar US               |
      | News               | Polestar - News                                                |
      | Service points     | Local service points for your Polestar * Polestar              |
      | Support            | Owners manual                                                  |
      | Recall information | VIN recall check * Polestar                                    |
      | Media              | Polestar media newsroom - Home                                 |
      | Contact us         | Contact us for personal support * Polestar                     |
      | Cookies            | Pure progressive performance * Polestar                        |
      | Privacy            | Pure progressive performance * Polestar                        |
      | Legal              | Polestar legal                                                 |
      | Download App       | Polestar.com * Download the Polestar app                       |
      | Careers            | Careers                                                        |
      | FAQ                | FAQ – Top questions about electric cars * Polestar US          |
      | Facebook           | Polestar - 1,483 photos - Cars -                               |
      | Twitter            | Polestar (@PolestarCars) / Twitter                             |
      | YouTube            | Polestar - YouTube                                             |
      | Subscribe          | Newsletter sign up                                             |

  @Footer @Desktop
  Scenario: Verify the Change Location link from Polestar 2 homepage footer
    Given User is in "Polestar2" page
    When user navigates to footer
    And clicks on "Change Location"
    Then Verify the user lands on "Select your region"
    And when user clicks on close user lands on Polestar2 homepage

  @Footer @Mobile
  Scenario: Verify the Change Location link from Polestar 2 homepage footer
    Given User is in "Polestar2" page
    When user navigates to footer
    And clicks on "Change Location"
    Then Verify the user lands on "Select your region"
    And when user clicks on close user lands on Polestar2 homepage

  @Footer @Mobile
  Scenario Outline: Verify the navigation to different pages from the footer of Polestar 2 US page in Mobile platform
    Given User is in "Polestar2" page
    When user navigates to footer
    And clicks on "<footerLink>"
    And clicks on "<link>"
    Then Verify the user lands on "<page>"
    And when user clicks on back verify that back user lands on Polestar2 homepage

    Examples:
      | footerLink   | link               | page                                                           |
      | polestar.com | Home               | Polestar – Electric cars * Polestar US                         |
      | polestar.com | Polestar 1         | Polestar 1 – The hybrid electric performance car * Polestar US |
      | polestar.com | Polestar 2         | Polestar 2 – The 100% electric car * Polestar US               |
      | polestar.com | News               | Polestar - News                                                |
      | polestar.com | Service points     | Local service points for your Polestar * Polestar              |
      | Polestar     | Support            | Owners manual                                                  |
      | Polestar     | Recall information | VIN recall check * Polestar                                    |
      | Polestar     | Media              | Polestar media newsroom - Home                                 |
      | Polestar     | Contact us         | Contact us for personal support * Polestar                     |
      | Polestar     | Cookies            | Pure progressive performance * Polestar                        |
      | Polestar     | Privacy            | Pure progressive performance * Polestar                        |
      | Polestar     | Legal              | Polestar legal                                                 |
      | Discover     | Download App       | Polestar.com * Download the Polestar app                       |
      | Discover     | Careers            | Careers                                                        |
      | Discover     | FAQ                | FAQ – Top questions about electric cars * Polestar US          |
      | Social       | Facebook           | Polestar - 1,483 photos - Cars -                               |
      | Social       | Twitter            | Polestar (@PolestarCars) / Twitter                             |
      | Social       | YouTube            | Polestar - YouTube                                             |
      | Social       | Subscribe          | Newsletter sign up                                             |

