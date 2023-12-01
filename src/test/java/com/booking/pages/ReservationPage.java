package com.booking.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
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

    private static final String HOTEL_NAME_XPATH = "//*[contains(text(), 'Tolip Hotel Alexandria')]";

    public ReservationPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath(HOTEL_NAME_XPATH)
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
            WebElement hotelNameEl = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(HOTEL_NAME_XPATH)
            ));
            return hotelNameEl.getText();
        }
    }

    public boolean verifyHotelName(String expectedName) {
        String hotelName = getHotelName();
        return hotelName.contains(expectedName);
    }
}
