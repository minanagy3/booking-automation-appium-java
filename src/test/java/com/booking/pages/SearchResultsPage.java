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
import java.util.List;

public class SearchResultsPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "com.booking:id/property_card")
    @iOSXCUITFindBy(className = "XCUIElementTypeCell")
    private List<WebElement> hotelCards;

    private static final String HOTEL_CARD_CSS = "[data-testid='property-card']";

    public SearchResultsPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForResults() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(HOTEL_CARD_CSS)
        ));
    }

    public void findAndClickTolipHotel() {
        String hotelName = "Tolip Hotel Alexandria";
        boolean found = false;

        // Try to find on first page
        waitForResults();
        List<WebElement> cards = driver.findElements(By.cssSelector(HOTEL_CARD_CSS));
        
        for (WebElement card : cards) {
            String text = card.getText();
            if (text.contains("Tolip Hotel Alexandria")) {
                WebElement seeAvailabilityLink = card.findElement(
                    By.xpath(".//a[contains(text(), 'See availability')]")
                );
                seeAvailabilityLink.click();
                found = true;
                break;
            }
        }

        // If not found on first page, scroll and try again
        if (!found) {
            try {
                // Scroll down
                int screenHeight = driver.manage().window().getSize().getHeight();
                int startY = (int) (screenHeight * 0.7);
                int endY = (int) (screenHeight * 0.3);
                driver.executeScript("mobile: scroll", 
                    "{\"direction\": \"down\", \"element\": null}");
                
                Thread.sleep(2000);
                waitForResults();
                
                cards = driver.findElements(By.cssSelector(HOTEL_CARD_CSS));
                for (WebElement card : cards) {
                    String text = card.getText();
                    if (text.contains("Tolip Hotel Alexandria")) {
                        WebElement seeAvailabilityLink = card.findElement(
                            By.xpath(".//a[contains(text(), 'See availability')]")
                        );
                        seeAvailabilityLink.click();
                        found = true;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!found) {
            throw new RuntimeException("Tolip Hotel Alexandria not found in search results");
        }

        wait.until(ExpectedConditions.urlContains("hotel"));
    }
}
