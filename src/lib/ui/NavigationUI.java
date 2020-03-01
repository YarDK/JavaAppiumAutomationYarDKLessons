package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;


public class NavigationUI extends MainPageObject {

    private static final String
            MY_LISTS_LINK_BY_XPATH = "xpath://android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver){
        super(driver);
    }

    public void clickMyList(){
        this.waitForElementAndClick(MY_LISTS_LINK_BY_XPATH,"Element 'MY_LISTS_LINK_BY_XPATH' can not find.",5);
    }
}
