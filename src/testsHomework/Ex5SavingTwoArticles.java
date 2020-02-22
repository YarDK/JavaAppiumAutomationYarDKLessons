package testsHomework;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class Ex5SavingTwoArticles extends CoreTestCase {

    /*
    Написать тест, который:
    1. Сохраняет две статьи в одну папку
    2. Удаляет одну из статей
    3. Убеждается, что вторая осталась
    4. Переходит в неё и убеждается, что title совпадает
    */

    @Test
    public void testSavingTwoArticles() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String article_title_java = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning Programming";
        articlePageObject.addFirstArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Ruby");
        searchPageObject.clickByArticleWithSubstring("Programming language");
        articlePageObject.waitForTitleElement();
        String article_title_ruby = articlePageObject.getArticleTitle();
        articlePageObject.addSecondArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();
        navigationUI.clickMyList();
        myListsPageObject.waitForListPresent(name_of_folder);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(article_title_ruby);
        myListsPageObject.waitForArticleToAppearByTitle(article_title_java);
        myListsPageObject.waitForArticleToDisappearByTitle(article_title_ruby);
        myListsPageObject.clickByArticleWithTitle(article_title_java);

        assertEquals(
                "Article is not about '" + article_title_java + "'",
                articlePageObject.getArticleTitle(),
                article_title_java
        );
    }
}
