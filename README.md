# Polestar2

Running tests

mvn test verify -Dcucumber.filter.tags='@Desktop' -DdesktopPlatform='chrome' -DmobilePlatform='android' -Denvironment='qa'

tags allowed are @Desktop, @Footer, @Mobile, @Header

desktopPlatform allowed are firefox, chrome

mobilePlatform allowed are android, ios
