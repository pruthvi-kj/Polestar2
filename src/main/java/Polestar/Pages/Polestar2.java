package Polestar.Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;
import java.util.List;

public class Polestar2 {
    WebDriver driver;
    Iterator<WebElement> w;
    public Polestar2(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div/ul/li/a[@href]")
    private List<WebElement> footerLinks;

    @FindBy(xpath = "//a[@href='/us/sign-up-newsletter/?redirect-url=/us/polestar-2/']")
    private WebElement SubscribeBtn;

    public void navigateToFooter() throws InterruptedException {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    public void clickOnSubscribe(){
        SubscribeBtn.click();
    }

    public void clickOnTheLink(String linktext){
        w=footerLinks.iterator();
        while (w.hasNext()) {
            WebElement e=w.next();
            String text=e.getText();
            if(text!=null && text.equalsIgnoreCase(linktext)){
                e.click();
                break;
            }
        }
    }

    public String getPageTitle() throws InterruptedException {
        Thread.sleep(1000);
        return driver.getTitle();
    }


}
