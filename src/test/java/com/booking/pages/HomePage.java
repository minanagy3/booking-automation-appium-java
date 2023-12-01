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

    @AndroidFindBy(id = "com.booking:id/search_box")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchInput;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Check-in date']")
    @iOSXCUITFindBy(accessibility = "Check-in date")
    private WebElement checkInDateButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Check-out date']")
    @iOSXCUITFindBy(accessibility = "Check-out date")
    private WebElement checkOutDateButton;

    @AndroidFindBy(id = "com.booking:id/search_button")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchButton;

    // For mobile web browser
    private static final String SEARCH_INPUT_CSS = "input[name='ss']";
    private static final String CHECK_IN_DATE_CSS = "button[data-testid='date-display-field-start']";
    private static final String CHECK_OUT_DATE_CSS = "button[data-testid='date-display-field-end']";
    private static final String SEARCH_BUTTON_CSS = "button[type='submit']";

    public HomePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void navigate() {
        driver.get("https://www.booking.com");
        // Handle cookies popup if it appears
        try {
            WebElement acceptCookies = driver.findElement(
                org.openqa.selenium.By.id("onetrust-accept-btn-handler")
            );
            if (acceptCookies.isDisplayed()) {
                acceptCookies.click();
            }
        } catch (Exception e) {
            // Cookies popup might not appear
        }
    }

    public void searchLocation(String location) {
        try {
            // Try mobile app locators first
            wait.until(ExpectedConditions.visibilityOf(searchInput));
            searchInput.clear();
            searchInput.sendKeys(location);
        } catch (Exception e) {
            // Fallback to mobile web locators
            WebElement searchField = driver.findElement(
                org.openqa.selenium.By.cssSelector(SEARCH_INPUT_CSS)
            );
            wait.until(ExpectedConditions.visibilityOf(searchField));
            searchField.clear();
            searchField.sendKeys(location);
        }
        
        try {
            Thread.sleep(1000); // Wait for autocomplete
            // For mobile, use touch actions or direct key events
            org.openqa.selenium.interactions.Actions actions = 
                new org.openqa.selenium.interactions.Actions(driver);
            actions.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN).perform();
            Thread.sleep(500);
            actions.sendKeys(org.openqa.selenium.Keys.ENTER).perform();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void selectCheckInDate(LocalDate date) {
        try {
            checkInDateButton.click();
        } catch (Exception e) {
            WebElement checkInBtn = driver.findElement(
                org.openqa.selenium.By.cssSelector(CHECK_IN_DATE_CSS)
            );
            checkInBtn.click();
        }
        
        String dateString = formatDateForBooking(date);
        WebElement dateElement = driver.findElement(
            org.openqa.selenium.By.cssSelector("span[data-date='" + dateString + "']")
        );
        wait.until(ExpectedConditions.elementToBeClickable(dateElement)).click();
    }

    public void selectCheckOutDate(LocalDate date) {
        String dateString = formatDateForBooking(date);
        WebElement dateElement = driver.findElement(
            org.openqa.selenium.By.cssSelector("span[data-date='" + dateString + "']")
        );
        wait.until(ExpectedConditions.elementToBeClickable(dateElement)).click();
    }

    public void clickSearch() {
        try {
            searchButton.click();
        } catch (Exception e) {
            WebElement searchBtn = driver.findElement(
                org.openqa.selenium.By.cssSelector(SEARCH_BUTTON_CSS)
            );
            searchBtn.click();
        }
        wait.until(ExpectedConditions.urlContains("searchresults"));
    }

    private String formatDateForBooking(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void searchHotel(String location, LocalDate checkIn, LocalDate checkOut) {
        searchLocation(location);
        selectCheckInDate(checkIn);
        selectCheckOutDate(checkOut);
        clickSearch();
    }
}
