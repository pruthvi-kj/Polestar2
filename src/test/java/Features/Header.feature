Feature: Verify the navigation in Header

#
#  @Header @Desktop
#  Scenario Outline: Verify the Header links on Polestar 2 homepage
#    Given User is in "HeaderAndFooter" page
#    When user navigates to header
#    And clicks on "<link>"
#    Then Verify the user navigates to "<page>"
#    And when user clicks on back verify that back user lands on Polestar2 homepage
#
#    Examples:
#      | link                 | page                                                           |
#      | Home                 | Polestar – Electric cars * Polestar US                         |
#      | Polestar 1           | Polestar 1 – The hybrid electric performance car * Polestar US |
#      | Polestar 2           | Polestar 2 – The 100% electric car * Polestar US               |
#      | News                 | Polestar - News                                                |
#      | Spaces               | Polestar.com * Spaces. Our farewell to car dealerships.        |
#      | Service & Assistance | Car service and roadside assistance * Polestar US              |
#      | About                | About Polestar                                                 |
#      | Support              | Owners manual                                                  |
#      | Legal                | Polestar legal                                                 |
#      | Media                | Polestar media newsroom - Home                                 |
#
#
#  @Header @Desktop
#  Scenario Outline: Verify the Header links on Polestar 2 homepage for Hamburger Menu
#    Given User is in "HeaderAndFooter" page
#    When user navigates to header
#    And user moves the mouse to "<menuOption>"
#    And clicks on "<link>"
#    Then Verify the user navigates to "<page>"
#    And when user clicks on back verify that back user lands on Polestar2 homepage
#
#    Examples:
#      | menuOption | link                  | page                                                           |
#      | Polestar 1 | Polestar 1 Explore    | Polestar 1 – The hybrid electric performance car * Polestar US |
#      | Polestar 1 | Polestar 1 Configure  | Polestar 1 - Configure your Polestar online * Polestar US      |
#      | Polestar 2 | Polestar 2 Explore    | Polestar 2 – The 100% electric car * Polestar US               |
#      | Polestar 2 | Polestar 2 Configure  | Polestar 2 - Configure your Polestar online * Polestar US      |
#      | Polestar 2 | Polestar 2 Test Drive | Book a Polestar test drive * Polestar US                       |
#
#
#  @Header @Mobile
#  Scenario Outline: Verify the Header links on Polestar 2 homepage for Hamburger Menu for Mobile platform
#    Given User is in "HeaderAndFooter" page
#    When user navigates to header
#    And clicks on "<menuOption>"
#    And clicks on "<link>"
#    Then Verify the user navigates to "<page>"
#    And when user clicks on back verify that back user lands on Polestar2 homepage
#
#    Examples:
#      | menuOption | link                  | page                                                           |
#      | Polestar 1 | Polestar 1 Explore    | Polestar 1 – The hybrid electric performance car * Polestar US |
#      | Polestar 1 | Polestar 1 Configure  | Polestar 1 - Configure your Polestar online * Polestar US      |
#      | Polestar 2 | Polestar 2 Explore    | Polestar 2 – The 100% electric car * Polestar US               |
#      | Polestar 2 | Polestar 2 Configure  | Polestar 2 - Configure your Polestar online * Polestar US      |
#      | Polestar 2 | Polestar 2 Test Drive | Book a Polestar test drive * Polestar US                       |
#
#  @Header @Mobile
#  Scenario Outline: Verify the Header links on Polestar 2 homepage for Mobile platform
#    Given User is in "HeaderAndFooter" page
#    When user navigates to header
#    And clicks on "<link>"
#    Then Verify the user navigates to "<page>"
#    And when user clicks on back verify that back user lands on Polestar2 homepage
#
#    Examples:
#      | link    | page                           |
#      | About   | About Polestar                 |
#      | Support | Owners manual                  |
#      | Legal   | Polestar legal                 |
#      | Media   | Polestar media newsroom - Home |

    @Header @Desktop
    Scenario: Verify the Header links on Polestar 2 homepage for Hamburger Menu
      Given User is in "HeaderAndFooter" page for "US" route
      When user navigates to header
      Then verify that all the links are valid