package testsHomework;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class Ex8RefactoringTemplate extends CoreTestCase {

    @Test
    public void testRefactoringTemplate()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        String title_of_article = "Java (programming language)";
        String description_of_article = "Object-oriented programming language";
        searchPageObject.waitForElementByTitleAndDescription(title_of_article,description_of_article);
    }
}
