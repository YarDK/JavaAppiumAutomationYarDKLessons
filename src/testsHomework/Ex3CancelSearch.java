package testsHomework;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class Ex3CancelSearch extends CoreTestCase {
    /*
    Ex3: Тест: отмена поиска
    Написать тест, который:
    -Ищет какое-то слово
    -Убеждается, что найдено несколько статей
    -Отменяет поиск
    -Убеждается, что результат поиска пропал
    */

    @Test
    public void testCancelSearch()
    {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_line = "Java";
        searchPageObject.typeSearchLine(search_line);

        assertTrue(
                "We found too few result",
                searchPageObject.getAmountOfFoundArticles() > 0
        );

        searchPageObject.waitForCloseButtonToAppear();
        searchPageObject.clickByCloseButton();

        assertEquals(
                "Search container is not empty",
                "Search…",
                searchPageObject.getPlaceholderSearchField()
        );
    }
}
