package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class FirstTest {
    private AppiumDriver<MobileElement> driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidTest");
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.afollestad.materialdialogssample");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.afollestad.materialdialogssample.MainActivity");
        caps.setCapability(MobileCapabilityType.APP, "C:\\Users\\ahalt\\IdeaProjects\\qa_mobile_auto_akhalturin01\\src\\test\\resources\\Apps\\sample.apk");
        URL appiumURL = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(appiumURL, caps);

    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void PopUpNotExistsTest() throws InterruptedException {
        WebElement BasicStackedBtn = waitForElement(By.id("basic_stacked_buttons"), 5);
        BasicStackedBtn.click();
        WebElement Md_BtnNegative = waitForElement(By.id("md_button_negative"), 5);
        Md_BtnNegative.click();
        Assert.assertEquals(waitForElementNotExists(By.id("md_button_negative"), 5), true, "md_button_negative' not exists");
        driver.resetApp();
    }

    @Test
    public void rotationElementDisappearedTest () throws InterruptedException {
        WebElement Search = waitForElement(By.id("basic_long_titled_buttons"), 5);
        driver.rotate(ScreenOrientation.LANDSCAPE);
        waitForElementNotExists(By.id("basic_long_titled_buttons"), 5);
        driver.resetApp();
    }

    @Test
    public void scrollToElementTest () {
        scrollToElement(By.id("bottomsheet_dateTimePicker"), 20, Scroll.UP);
        driver.resetApp();
    }

    @Test
    public void swipeElementTest() throws InterruptedException {
        scrollToElement(By.id("colorChooser_primary_customRgb"), 20, Scroll.UP);
        WebElement element = waitForElement(By.id("colorChooser_primary_customRgb"), 5);
        element.click();
        waitForElement(By.id("colorPresetGrid"),5);
        swipeElement(By.id("colorPresetGrid"), SwipeDirection.LEFT);
        waitForElementNotExists(By.id("colorPresetGrid"), 5);
        driver.resetApp();
    }

    @Test
    public void backGroundTest (){
        waitForElement(By.id("action_bar"), 1);
        driver.runAppInBackground(Duration.ofSeconds(5));
        driver.resetApp();

    }


    private void swipeElement(By by, SwipeDirection swipeDirection) {
        WebElement element = waitForElement(by, 5);
        int leftX = element.getLocation().getX();
        int upperY = element.getLocation().getY();

        int middleY = upperY + element.getSize().height/2;
        int middleX = leftX + element.getSize().width/2;

        int destinationX = swipeDirection == SwipeDirection.LEFT ? 0 : driver.manage().window().getSize().width;

        TouchAction touchAction = new TouchAction(driver);
        touchAction
                .press(PointOption.point(middleX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(destinationX, middleY))
                .release()
                .perform();


    }

    private void scrollToElement (By by, int maxScrolls, Scroll scroll) {
        int scrollCount = 0;
        while (driver.findElements(by).size() == 0) {
            if (scrollCount > maxScrolls) {
                waitForElement(by, 5);
                return;
            }
            if (scroll == Scroll.UP) {
                scrollQuickUp();
            }
            else scrollQuickDown();
            scrollCount++;
        }
    }

    private void scroll(int swipeTime, Scroll scroll) {
        TouchAction touchAction = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width/2;
        int startY = 0;
        switch (scroll) {
            case UP: startY = (int) (size.height*0.8);
            break;
            case DOWN: startY = (int) (size.height*0.2);
            break;
        }
        int finishY = size.height - startY;
        touchAction
                .press(PointOption.point(x, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(swipeTime)))
                .moveTo(PointOption.point(x, finishY))
                .release()
                .perform();
    }

    private void scrollQuickUp() {
        scroll(1, Scroll.UP);
    }

    private void scrollQuickDown() {
        scroll(1, Scroll.DOWN);
    }

    private WebElement waitForElement(By by, int TimeOut) {
        WebDriverWait wait = new WebDriverWait(driver, TimeOut);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private boolean waitForElementNotExists(By by, int TimeOut) {
        WebDriverWait wait = new WebDriverWait(driver, TimeOut);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }


}
