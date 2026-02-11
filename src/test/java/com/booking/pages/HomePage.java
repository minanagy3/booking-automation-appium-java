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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "com.booking:id/search_box_text")
    @iOSXCUITFindBy(accessibility = "Search destination")
    private WebElement searchInput;

    @AndroidFindBy(id = "com.booking:id/check_in_date")
    @iOSXCUITFindBy(accessibility = "Check-in date")
    private WebElement checkInDateButton;

    @AndroidFindBy(id = "com.booking:id/check_out_date")
    @iOSXCUITFindBy(accessibility = "Check-out date")
    private WebElement checkOutDateButton;

    @AndroidFindBy(id = "com.booking:id/search_button")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchButton;

    @AndroidFindBy(id = "com.booking:id/accept_cookies")
    @iOSXCUITFindBy(accessibility = "Accept")
    private WebElement acceptCookiesButton;

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void navigate() {
        // App is already launched by Appium
        // Handle cookies popup if it appears
        try {
            if (acceptCookiesButton.isDisplayed()) {
                acceptCookiesButton.click();
            }
        } catch (Exception e) {
            // Cookies popup might not appear
        }
    }

    public void searchLocation(String location) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(location);
        try {
            Thread.sleep(1000); // Wait for autocomplete
            // Select first suggestion
            driver.findElement(org.openqa.selenium.By.xpath("//android.widget.TextView[contains(@text, '" + location + "')]")).click();
        } catch (Exception e) {
            // Try alternative approach
            searchInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        }
    }

    public void selectCheckInDate(LocalDate date) {
        checkInDateButton.click();
        String dateString = formatDateForBooking(date);
        // Navigate to the date in calendar
        selectDateInCalendar(date);
    }

    public void selectCheckOutDate(LocalDate date) {
        selectDateInCalendar(date);
    }

    private void selectDateInCalendar(LocalDate date) {
        // Mobile calendar selection logic
        int day = date.getDayOfMonth();
        String dayXpath = "//android.widget.TextView[@text='" + day + "']";
        try {
            driver.findElement(org.openqa.selenium.By.xpath(dayXpath)).click();
        } catch (Exception e) {
            // Alternative selector
            driver.findElement(org.openqa.selenium.By.xpath("//*[@text='" + day + "']")).click();
        }
    }

    private String formatDateForBooking(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void clickSearch() {
        searchButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
            org.openqa.selenium.By.id("com.booking:id/search_results")
        ));
    }

    public void searchHotel(String location, LocalDate checkIn, LocalDate checkOut) {
        searchLocation(location);
        selectCheckInDate(checkIn);
        selectCheckOutDate(checkOut);
        clickSearch();
    }
}

