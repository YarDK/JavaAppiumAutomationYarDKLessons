package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_FIELD_BY_ID = "org.wikipedia:id/search_container",
            SEARCH_CLOSE_BUTTON_BY_ID = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL_BY_XPATH = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT_BY_XPATH = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL_BY_XPATH.replace("{SUBSTRING}",substring);
    }
    /* TEMPLATES METHODS */

    public void waitForCloseButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CLOSE_BUTTON_BY_ID),"Element 'SEARCH_CLOSE_BUTTON_BY_ID' can not find",5);
    }

    public void waitForCloseButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CLOSE_BUTTON_BY_ID),"Element 'SEARCH_CLOSE_BUTTON_BY_ID' stile present", 5);
    }

    public void clickByCloseButton(){
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BUTTON_BY_ID),"Element 'SEARCH_CLOSE_BUTTON_BY_ID' can not find and click",5);
    }

    public void initSearchInput(){
        this.waitForElementAndClick(By.id(SEARCH_FIELD_BY_ID),"Element 'SEARCH_FIELD_BY_ID'can not find or clicked",5);
        this.waitForElementPresent(By.id(SEARCH_FIELD_BY_ID),"Element 'SEARCH_FIELD_BY_ID'can not find",5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSetValue(By.id(SEARCH_FIELD_BY_ID),search_line,"Element 'SEARCH_FIELD_BY_ID'can not find",5);
    }

    public void waitForSearchResult(String substring){
        String search_result_by_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_by_xpath), "Result cannot find with substring '" + substring + "'");
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_by_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_by_xpath), "Result cannot find and click with substring '" + substring + "'",5);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT_BY_XPATH),"Cannot find anything by thw request.",15);
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT_BY_XPATH));
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH), "cannot found element 'SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH'");
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH));
    }

}
