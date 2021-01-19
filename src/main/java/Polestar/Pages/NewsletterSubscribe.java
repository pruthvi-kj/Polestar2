package Polestar.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Iterator;

public class NewsletterSubscribe {

    WebDriver driver;
    Iterator<WebElement> w;
    public NewsletterSubscribe(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "sign-up-newsletter-first-name")
    private WebElement FirstName;

    @FindBy(id = "sign-up-newsletter-last-name")
    private WebElement LastName;

    @FindBy(id = "sign-up-newsletter-email")
    private WebElement EmailAddress;

    @FindBy(id = "sign-up-newsletter-postal-code")
    private WebElement zipCode;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement SubscribeBtn;

    @FindBy(xpath = "//div[text()='Back']")
    private WebElement BackBtn;

    @FindBy(xpath = "//span[text()='Back']")
    private WebElement BackCTA;



}
