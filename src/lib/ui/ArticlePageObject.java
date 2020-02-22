package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE_BY_ID = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT_BY_XPATH = "//*[@text='View page in browser']",
            OPTIONS_BUTTON_BY_XPATH = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON_BY_XPATH = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY_BY_ID = "org.wikipedia:id/onboarding_button",
            MY_NAME_LIST_TPL_BY_XPATH = "//*[@resource-id='org.wikipedia:id/item_container']//*[@text='{FOLDER_NAME}']",
            MY_NAME_LIST_INPUT_BY_ID = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON_BY_XPATH = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON_BY_XPATH = "//android.widget.ImageButton[@content-desc='Navigate up']";



    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getMyNameList(String name_of_folder){
        return MY_NAME_LIST_TPL_BY_XPATH.replace("{FOLDER_NAME}",name_of_folder);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(By.id(TITLE_BY_ID),"Cannot find article title on page",15);
    }

    public void waitForLoadingElementOrPage(int sleep_time_in_seconds){
        this.waitingForElement(sleep_time_in_seconds * 1000);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT_BY_XPATH),"Cannot find the end article",20);
    }

    public void addFirstArticleToMyList(String name_of_folder){
        this.waitForElementAndClick( By.xpath(OPTIONS_BUTTON_BY_XPATH),"'OPTIONS_BUTTON_BY_XPATH' cannot find and click",5);
        this.waitForElementAndClick( By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON_BY_XPATH),"'OPTIONS_ADD_TO_MY_LIST_BUTTON_BY_XPATH' cannot find and click",5);
        this.waitForElementAndClick( By.id(ADD_TO_MY_LIST_OVERLAY_BY_ID),"'ADD_TO_MY_LIST_OVERLAY_BY_ID' cannot find and click (button 'Got it')",5);
        this.waitForElementAndClear(By.id(MY_NAME_LIST_INPUT_BY_ID),"'MY_NAME_LIST_INPUT_BY_ID' cannot find and clear",5);
        this.waitForElementAndSetValue( By.id(MY_NAME_LIST_INPUT_BY_ID),name_of_folder,"Cannot set value by 'MY_NAME_LIST_INPUT_BY_ID'",5);
        this.waitForElementAndClick( By.xpath(MY_LIST_OK_BUTTON_BY_XPATH),"'MY_LIST_OK_BUTTON_BY_XPATH' cannot find and click",5);
    }

    public void addSecondArticleToMyList(String name_of_folder){
        this.waitForElementAndClick( By.xpath(OPTIONS_BUTTON_BY_XPATH),"'OPTIONS_BUTTON_BY_XPATH' cannot find and click",5);
        this.waitForElementAndClick( By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON_BY_XPATH),"'OPTIONS_ADD_TO_MY_LIST_BUTTON_BY_XPATH' cannot find and click",5);
        String my_name_list = getMyNameList(name_of_folder);
        this.waitForElementAndClick( By.xpath(my_name_list),"Cannot find list with name '" + name_of_folder + "'.",5);
    }

    public void closeArticle(){
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON_BY_XPATH),"Element 'CLOSE_ARTICLE_BUTTON_BY_XPATH' (Navigate up 'X') can not find.",5);
    }

    public void withoutWaitTitlePresent(){
        this.assertElementNotPresent(By.id(TITLE_BY_ID));
    }
}
