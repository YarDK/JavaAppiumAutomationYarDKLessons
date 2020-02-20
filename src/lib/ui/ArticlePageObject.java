package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT_BY_XPATH = "//*[@text='View page in browser']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresents(By.id(TITLE),"Cannot find article title on page",5);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT_BY_XPATH),"Cannot find the end article",20);
    }
}
