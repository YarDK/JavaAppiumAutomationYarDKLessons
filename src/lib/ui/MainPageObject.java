package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    // Метод для ожидания появления элемента
    public WebElement waitForElementPresent(String locator, String error_massage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_massage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    // Перегруженный метод для ожидания появления элемента, таймаут задан по умолчанию 5 секунд
    public WebElement waitForElementPresent(String locator, String error_massage) {
        return waitForElementPresent(locator, error_massage, 5);
    }

    // Метод для совершения клика по элементу
    public WebElement waitForElementAndClick(String locator, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_massage, timeoutInSeconds);
        element.click();
        return element;
    }

    // Метод для совершения клика по элементу
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_massage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    // Метод для ввода значения в выбранный элемент
    public WebElement  waitForElementAndSetValue(String locator, String value, String error_massage, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        waitForElementPresent(locator,error_massage,timeoutInSeconds);
        MobileElement element = (MobileElement) driver.findElement(by);
        element.setValue(value);
        return element;
    }

    // Метод для проверки отсутствия элемента
    public boolean waitForElementNotPresent(String locator, String error_massage, long timeInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_massage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    // Метод для удаления текста
    public WebElement waitForElementAndClear(String locator, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_massage, timeoutInSeconds);
        element.clear();
        return element;
    }

    // Метод для скролла
    public void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2; // Делим ширену экрана пополам
        int start_y = (int) (size.height * 0.8); // Процентное соотношение относительно общей высоты экрана
        int end_y = (int) (size.height * 0.2);
        // Нажать в заданной координате, держать заданное время, перетащить в заданную координату, закончить действие, передать на выполнение
        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    // Метод для быстрого скрола на основе обычного
    public void swipeUpQuick(){
        swipeUp(200);
    }

    // Метод для скорла, пока не появится элемент на странице
    public void swipeUpToFindElement(String locator, String error_message, int max_swipes){
        By by = this.getLocatorByString(locator);
        int already_swipe = 0;
        while(driver.findElements(by).size() == 0){
            if (already_swipe > max_swipes){
                waitForElementPresent(locator, error_message + " Swipe limit exceeded", 0);
                return;
            }
            swipeUpQuick();
            already_swipe++;
        }
    }

    public void swipeElementToLeft(String locator, String error_message){
        WebElement element = waitForElementPresent(locator, error_message, 10);
        int left_x = element.getLocation().getX(); // Левая сторона найденного элемента. Берем нулевое значение по оси Х
        int right_x = left_x + element.getSize().getWidth(); // Правая сторона элемента
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();
    }

    public void waitingForElement(long timeForWaiting){
        try{
            Thread.sleep(timeForWaiting);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void assertElementNotPresent(String locator, String error_message){
        By by = this.getLocatorByString(locator);
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e){
            throw new AssertionError(error_message);
        }
    }

    public int getAmountOfElements(String locator){
        By by = this.getLocatorByString(locator);
        List list = driver.findElements(by);
        return list.size();
    }

    public void assertTwoElementNotPresent(String locator_first_element, String locator_second_element, String error_message){

        By by_one = this.getLocatorByString(locator_first_element);
        By by_two = this.getLocatorByString(locator_second_element);
        try {
            driver.findElement(by_one);
            driver.findElement(by_two);
        } catch (NoSuchElementException e){
            throw new AssertionError(error_message);
        }
    }

    private By getLocatorByString(String locator_with_type){
        // Данная строка записывает делит по символу двоеточие
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")){
            return By.xpath(locator);
        } else if (by_type.equals("id")){
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get typ of locator. Locator: " + locator_with_type);
        }
    }


}
