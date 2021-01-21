Feature: Verify the navigation in Header

    @Header
    Scenario Outline: Verify the Header links on Polestar 2 homepage
      Given User is in "<website>" page
      When user navigates to header
      And clicks on "<link>"
      Then Verify the user lands on "<page>"
      And when user clicks on back verify that back user lands on Polestar 2 homepage

      Examples:
        |website|link|page|
        |https://www.polestar.com/us/polestar-2/|Home|Polestar – Electric cars * Polestar US|
      |https://www.polestar.com/us/polestar-2/|Polestar 1|Polestar 1 – The hybrid electric performance car * Polestar US|
      |https://www.polestar.com/us/polestar-2/|Polestar 2|Polestar 2 – The 100% electric car * Polestar US|
      |https://www.polestar.com/us/polestar-2/|News|Polestar - News|
      |https://www.polestar.com/us/polestar-2/|Spaces|Polestar.com * Spaces. Our farewell to car dealerships. |
      |https://www.polestar.com/us/polestar-2/|Service & Assistance|Car service and roadside assistance * Polestar US|
      |https://www.polestar.com/us/polestar-2/|About|About Polestar|
      |https://www.polestar.com/us/polestar-2/|Support|Owners manual|
      |https://www.polestar.com/us/polestar-2/|Legal|Polestar legal|
      |https://www.polestar.com/us/polestar-2/|Media|Polestar media newsroom - Home|


     @Header
     Scenario Outline: Verify the Header links on Polestar 2 homepage for Polestar 1 menu
      Given User is in "<website>" page
      When user navigates to header
      And user moves the mouse to "<menuOption>"
      And clicks on "<link>"
      Then Verify the user lands on "<page>"
      And when user clicks on back verify that back user lands on Polestar 2 homepage

       Examples:
         | website | menuOption | link | page |
         |https://www.polestar.com/us/polestar-2/|Polestar 1|Polestar 1 Explore|Polestar 1 – The hybrid electric performance car * Polestar US|
         |https://www.polestar.com/us/polestar-2/|Polestar 1|Polestar 1 Configure|Polestar 1 - Configure your Polestar online * Polestar US|
         |https://www.polestar.com/us/polestar-2/|Polestar 2|Polestar 2 Explore|Polestar 2 – The 100% electric car * Polestar US|
         |https://www.polestar.com/us/polestar-2/|Polestar 2|Polestar 2 Configure|Polestar 2 - Configure your Polestar online * Polestar US|
         |https://www.polestar.com/us/polestar-2/|Polestar 2|Polestar 2 Test Drive|Book a Polestar test drive * Polestar US|
