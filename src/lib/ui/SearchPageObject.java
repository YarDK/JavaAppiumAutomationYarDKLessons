package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_FIELD_BY_ID = "id:org.wikipedia:id/search_container",
            INITIALIZED_SEARCH_FIELD_BY_ID = "id:org.wikipedia:id/search_src_text",
            SEARCH_CLOSE_BUTTON_BY_ID = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_LIST_BY_ID = "id:org.wikipedia:id/search_results_list",
            SEARCH_RESULT_BY_SUBSTRING_TPL_BY_XPATH = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_BY_TITLE_TPL_BY_XPATH = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{TITLE}']",
            SEARCH_RESULT_ELEMENT_BY_XPATH = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH = "xpath://*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElementBySubstring(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL_BY_XPATH.replace("{SUBSTRING}",substring);
    }

    private static String getResultSearchElementByTitle(String title){
        return SEARCH_RESULT_BY_TITLE_TPL_BY_XPATH.replace("{TITLE}",title);
    }
    /* TEMPLATES METHODS */

    public void waitForCloseButtonToAppear(){
        this.waitForElementPresent(SEARCH_CLOSE_BUTTON_BY_ID,"Element 'SEARCH_CLOSE_BUTTON_BY_ID' can not find",5);
    }

    public void waitForCloseButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CLOSE_BUTTON_BY_ID,"Element 'SEARCH_CLOSE_BUTTON_BY_ID' stile present", 5);
    }

    public void clickByCloseButton(){
        this.waitForElementAndClick(SEARCH_CLOSE_BUTTON_BY_ID,"Element 'SEARCH_CLOSE_BUTTON_BY_ID' can not find and click",5);
    }

    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_FIELD_BY_ID,"Element 'SEARCH_FIELD_BY_ID'can not find or clicked",5);
        this.waitForElementPresent(INITIALIZED_SEARCH_FIELD_BY_ID,"Element 'SEARCH_FIELD_BY_ID'can not find",5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSetValue(INITIALIZED_SEARCH_FIELD_BY_ID,search_line,"Element 'SEARCH_FIELD_BY_ID'can not find",10);
    }

    public void waitForSearchResult(String substring){
        String search_result_by_xpath = getResultSearchElementBySubstring(substring);
        this.waitForElementPresent(search_result_by_xpath, "Result cannot find with substring '" + substring + "'");
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_by_xpath = getResultSearchElementBySubstring(substring);
        this.waitForElementAndClick(search_result_by_xpath, "Result cannot find and click with substring '" + substring + "'",5);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT_BY_XPATH,"Cannot find anything by thw request.",15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT_BY_XPATH);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH, "cannot found element 'SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH'");
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_EMPTY_RESULT_ELEMENT_BY_XPATH, "Found some result");
    }

    public String getPlaceholderSearchField(){
        WebElement element_search_field = this.waitForElementPresent(INITIALIZED_SEARCH_FIELD_BY_ID,"Element 'SEARCH_FIELD_BY_ID'can not find",5);
        return element_search_field.getAttribute("text");
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        String search_result_description_by_xpath = getResultSearchElementBySubstring(description);
        String search_result_title_by_xpath = getResultSearchElementByTitle(title);
        this.waitForElementPresent(SEARCH_RESULT_LIST_BY_ID,"Element 'SEARCH_RESULT_LIST_BY_ID' not found");
        this.assertTwoElementNotPresent(
                search_result_description_by_xpath,
                search_result_title_by_xpath,
                "Cannot find result with title '"+title+"' and description '"+description+"'.");
    }

}
