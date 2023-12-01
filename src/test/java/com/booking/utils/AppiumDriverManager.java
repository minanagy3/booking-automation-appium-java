package com.booking.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppiumDriverManager {
    private static AppiumDriver driver;
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";

    public static AppiumDriver getDriver(String platform) throws MalformedURLException {
        if (driver == null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            
            if (platform.equalsIgnoreCase("android")) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                
                driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), capabilities);
            } else if (platform.equalsIgnoreCase("ios")) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15.0");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 13");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                
                driver = new IOSDriver(new URL(APPIUM_SERVER_URL), capabilities);
            }
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static AppiumDriver getDriver() {
        return driver;
    }
}

