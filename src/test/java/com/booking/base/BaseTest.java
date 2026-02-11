package com.booking.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    protected AppiumDriver driver;
    protected String platformName;

    @Parameters({"platform", "deviceName", "platformVersion", "appPackage", "appActivity", "appiumServerUrl"})
    @BeforeClass
    public void setUp(String platform, String deviceName, String platformVersion, 
                     String appPackage, String appActivity, String appiumServerUrl) throws MalformedURLException {
        platformName = platform.toLowerCase();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platformName.equals("android")) {
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability("appPackage", appPackage);
            capabilities.setCapability("appActivity", appActivity);
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
            
            driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
        } else if (platformName.equals("ios")) {
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
            capabilities.setCapability("bundleId", appPackage);
            capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            
            driver = new IOSDriver(new URL(appiumServerUrl), capabilities);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

