package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;


    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        driver = Platform.getInstance().getDriver(); // Обращаемся к классу Platform, внутри создается единственный экземпляр класса и на основе это экземпляра мы строим драйвер
        this.rotateScreenPortrait();
        this.skipWelcomPageForIOSApp();

    }

    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        driver.quit();
    }

    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds){
        driver.runAppInBackground(Duration.ofMillis(seconds));
    }

    private void skipWelcomPageForIOSApp(){
        if(Platform.getInstance().isIOS()){
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }


}
