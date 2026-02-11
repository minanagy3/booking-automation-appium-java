package com.booking.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ReservationPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "com.booking:id/hotel_name")
    @iOSXCUITFindBy(accessibility = "Hotel name")
    private WebElement hotelNameElement;

    public ReservationPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            org.openqa.selenium.By.xpath("//*[contains(@text, 'Tolip Hotel Alexandria')]")
        ));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getHotelName() {
        try {
            return hotelNameElement.getText();
        } catch (Exception e) {
            // Try alternative selector
            return driver.findElement(
                org.openqa.selenium.By.xpath("//*[contains(@text, 'Tolip Hotel Alexandria')]")
            ).getText();
        }
    }

    public boolean verifyHotelName(String expectedName) {
        String hotelName = getHotelName();
        return hotelName.contains(expectedName);
    }
}

