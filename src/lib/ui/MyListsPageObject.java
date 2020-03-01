package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class MyListsPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL_BY_XPATH = "xpath://*[@text='{NAME_FOLDER}']",
            ARTICLE_BY_TITLE_TPL_BY_XPATH = "xpath://*[@text='{TITLE}']";

    /* TEMPLATES METHODS */
    private static String getFolderByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL_BY_XPATH.replace("{NAME_FOLDER}",name_of_folder);
    }

    private static String getTitleOfSaveArticle(String article_title){
        return ARTICLE_BY_TITLE_TPL_BY_XPATH.replace("{TITLE}",article_title);
    }
    /* TEMPLATES METHODS */

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }

    public void openFolderByName(String name_of_folder){
        String folder_name = getFolderByName(name_of_folder);
        this.waitForElementAndClick(folder_name,"Cannot find folder by name '" + name_of_folder + "'.",5);
    }

    public void waitForListPresent(String name_of_folder){
        String folder_name = getFolderByName(name_of_folder);
        this.waitForElementPresent(folder_name,"Cannot find folder with name '" +name_of_folder+ "'.",10);
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String title = getTitleOfSaveArticle(article_title);
        this.waitForElementPresent(title,"Save article with title " + article_title + "' can not find.",15);
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String title = getTitleOfSaveArticle(article_title);
        this.waitForElementNotPresent(title,"Save article still present with title " + article_title + "'.",15);
    }

    public void swipeByArticleToDelete(String article_title){
        this.waitForArticleToAppearByTitle(article_title);
        String title = getTitleOfSaveArticle(article_title);
        this.swipeElementToLeft(title,"Article with title '" + article_title + "' can not find.");
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void clickByArticleWithTitle(String title){
        String saving_title = getTitleOfSaveArticle(title);
        this.waitForElementAndClick(saving_title, "Title with name '" + title + "' cannot find and clicked",5);
    }


}

