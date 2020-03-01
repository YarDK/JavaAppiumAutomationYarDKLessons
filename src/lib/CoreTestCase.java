package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios",
    PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    //protected RemoteWebDriver driver;

    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";
    private static String platform = System.getenv("PLATFORM");

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
        driver = getDriverByPlatform(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();

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

    private AppiumDriver getDriverByPlatform(URL url, DesiredCapabilities capabilities) throws Exception{
        if(platform.equals(PLATFORM_ANDROID)){
            return new AndroidDriver(url, capabilities);
        } else if (platform.equals(PLATFORM_IOS)) {
            return new IOSDriver(url, capabilities);
        } else {
            throw new Exception("Cannot get driver by platform from env variable. Platform value " + platform);
        }
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if(platform.equals(PLATFORM_ANDROID)){
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("platformVersion", "6.0");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            //capabilities.setCapability("app", "C:\\Users\\yako\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");
            //capabilities.setCapability("app", "D:\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");
            capabilities.setCapability("app", "/Users/yaroslavkorotyshov/Desktop/JavaAppiumAutomationYarDKLessons/apks/org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)) {

            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE 11.4");
            capabilities.setCapability("platformVersion", "11.4");
            capabilities.setCapability("app", "/Users/yaroslavkorotyshov/Desktop/JavaAppiumAutomationYarDKLessons/apks/Wikipedia.app");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }

        return capabilities;

    }

}
