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

public class HotelDetailsPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "com.booking:id/check_in_date_display")
    @iOSXCUITFindBy(accessibility = "Check-in date")
    private WebElement checkInDateDisplay;

    @AndroidFindBy(id = "com.booking:id/check_out_date_display")
    @iOSXCUITFindBy(accessibility = "Check-out date")
    private WebElement checkOutDateDisplay;

    @AndroidFindBy(id = "com.booking:id/reserve_button")
    @iOSXCUITFindBy(accessibility = "I'll reserve")
    private WebElement reserveButton;

    @AndroidFindBy(id = "com.booking:id/bed_selection")
    @iOSXCUITFindBy(accessibility = "Bed selection")
    private WebElement bedSelection;

    @AndroidFindBy(id = "com.booking:id/amount_selection")
    @iOSXCUITFindBy(accessibility = "Amount")
    private WebElement amountSelection;

    public HotelDetailsPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            org.openqa.selenium.By.id("com.booking:id/check_in_date_display")
        ));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCheckInDate() {
        return checkInDateDisplay.getText();
    }

    public String getCheckOutDate() {
        return checkOutDateDisplay.getText();
    }

    public void selectBedAndAmount() {
        // Try to select bed type if available
        try {
            if (bedSelection.isDisplayed()) {
                bedSelection.click();
                // Select first option
                driver.findElement(org.openqa.selenium.By.xpath("//android.widget.TextView[1]")).click();
            }
        } catch (Exception e) {
            // Bed selection might not be available
        }

        // Try to select amount if available
        try {
            if (amountSelection.isDisplayed()) {
                amountSelection.click();
                amountSelection.clear();
                amountSelection.sendKeys("1");
            }
        } catch (Exception e) {
            // Amount selection might not be available
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickReserveButton() {
        // Scroll to reserve button if needed
        try {
            int height = driver.manage().window().getSize().getHeight();
            int width = driver.manage().window().getSize().getWidth();
            int startX = width / 2;
            int startY = (int) (height * 0.7);
            int endY = (int) (height * 0.3);
            // Use TouchAction for scrolling
            io.appium.java_client.TouchAction touchAction = new io.appium.java_client.TouchAction(driver);
            touchAction.press(io.appium.java_client.touch.offset.PointOption.point(startX, startY))
                .moveTo(io.appium.java_client.touch.offset.PointOption.point(startX, endY))
                .release()
                .perform();
        } catch (Exception e) {
            // Swipe might not be available, continue
        }

        wait.until(ExpectedConditions.elementToBeClickable(reserveButton));
        reserveButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
            org.openqa.selenium.By.id("com.booking:id/reservation_page")
        ));
    }
}

