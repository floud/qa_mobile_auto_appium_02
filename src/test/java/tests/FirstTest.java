package tests;



import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sun.rmi.runtime.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class FirstTest {
    private AppiumDriver<MobileElement> driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidTest");
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "org.wikipedia");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "org.wikipedia.main.MainActivity");
        caps.setCapability(MobileCapabilityType.APP, "C:\\Users\\ahalt\\IdeaProjects\\qa_mobile_auto_akhalturin01\\src\\test\\resources\\Apps\\wiki.apk");

        URL appiumURL = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(appiumURL, caps);
        SkipIntro();

    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void searchTest() throws InterruptedException {
        WebElement Search = waitForElement(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), 5);
        Search.click();
        WebElement SearchTextInput = waitForElement(By.id("search_src_text"), 5);
        SearchTextInput.click();
        SearchTextInput.sendKeys("google");
        Thread.sleep(2000);
        SearchTextInput.clear();
        Thread.sleep(2000);
    }

    @Test
    public void openSettings() {
        WebElement OptionsBtn = waitForElement(By.id("drawer_icon_menu"), 5);
        OptionsBtn.click();
        WebElement SettingsBtn = waitForElement(By.id("main_drawer_settings_container"), 5);
        SettingsBtn.click();
        WebElement BackBtn = waitForElement(By.xpath("//*[contains(@content-desc, 'Navigate up')]"), 5);
        BackBtn.click();
    }

    @Test
    public void loginFailed() {
        WebElement loginBtn = waitForElement(By.id("view_announcement_action_positive"), 5);
        loginBtn.click();
        WebElement userNameInput = waitForElement(By.xpath("//*[contains(@text, 'Username')]"), 5);
        userNameInput.click();
        WebElement userNameInputText = waitForElement(By.xpath("//*[contains(@text, 'Username')]"), 5);
        userNameInputText.sendKeys("12445");
        WebElement passwordInput = waitForElement(By.xpath("//*[contains(@text, 'Password')]"), 5);
        passwordInput.click();
        WebElement passwordInputText = waitForElement(By.xpath("//*[contains(@text, 'Password')]"), 5);
        passwordInputText.sendKeys("12445");
        WebElement Login = waitForElement(By.id("login_button"), 5);
        Login.click();
        waitForElement(By.xpath("//*[contains(@text, 'Incorrect username or password entered.\n" +
                "Please try again.')]"),5);
        WebElement BackBtn = waitForElement(By.xpath("//*[contains(@content-desc, 'Navigate up')]"), 5);
        BackBtn.click();
    }



    private WebElement waitForElement(By by, int TimeOut) {
        WebDriverWait wait = new WebDriverWait(driver, TimeOut);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private void SkipIntro(){
        WebElement SkipButton = waitForElement(By.id("fragment_onboarding_skip_button"), 5);
        SkipButton.click();
    }

}
