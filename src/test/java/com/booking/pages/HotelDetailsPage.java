package com.booking.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HotelDetailsPage {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @AndroidFindBy(id = "com.booking:id/check_in_date")
    @iOSXCUITFindBy(accessibility = "Check-in date")
    private WebElement checkInDateDisplay;

    @AndroidFindBy(id = "com.booking:id/check_out_date")
    @iOSXCUITFindBy(accessibility = "Check-out date")
    private WebElement checkOutDateDisplay;

    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"I'll reserve\"]")
    @iOSXCUITFindBy(accessibility = "I'll reserve")
    private WebElement reserveButton;

    private static final String CHECK_IN_DATE_CSS = "[data-testid='date-display-field-start']";
    private static final String CHECK_OUT_DATE_CSS = "[data-testid='date-display-field-end']";
    private static final String RESERVE_BUTTON_XPATH = "//button[contains(text(), \"I'll reserve\")]";

    public HotelDetailsPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(CHECK_IN_DATE_CSS)
        ));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCheckInDate() {
        try {
            return checkInDateDisplay.getText();
        } catch (Exception e) {
            return driver.findElement(By.cssSelector(CHECK_IN_DATE_CSS)).getText();
        }
    }

    public String getCheckOutDate() {
        try {
            return checkOutDateDisplay.getText();
        } catch (Exception e) {
            return driver.findElement(By.cssSelector(CHECK_OUT_DATE_CSS)).getText();
        }
    }

    public void selectBedAndAmount() {
        // Try to select bed type if dropdown exists
        try {
            List<WebElement> bedDropdowns = driver.findElements(By.cssSelector("select"));
            if (!bedDropdowns.isEmpty()) {
                bedDropdowns.get(0).click();
            }
        } catch (Exception e) {
            // Bed selection might not be available
        }

        // Try to select amount if input exists
        try {
            List<WebElement> amountInputs = driver.findElements(By.cssSelector("input[type='number']"));
            if (!amountInputs.isEmpty()) {
                amountInputs.get(0).clear();
                amountInputs.get(0).sendKeys("1");
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
        // Scroll to reserve button
        try {
            js.executeScript("arguments[0].scrollIntoView(true);", reserveButton);
        } catch (Exception e) {
            WebElement reserveBtn = driver.findElement(By.xpath(RESERVE_BUTTON_XPATH));
            js.executeScript("arguments[0].scrollIntoView(true);", reserveBtn);
            wait.until(ExpectedConditions.elementToBeClickable(reserveBtn));
            reserveBtn.click();
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(reserveButton));
        reserveButton.click();
        wait.until(ExpectedConditions.urlContains("checkout"));
    }
}
