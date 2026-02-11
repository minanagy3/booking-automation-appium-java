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
import java.util.List;

public class SearchResultsPage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "com.booking:id/property_card")
    @iOSXCUITFindBy(className = "XCUIElementTypeCell")
    private List<WebElement> hotelCards;

    @AndroidFindBy(id = "com.booking:id/see_availability")
    @iOSXCUITFindBy(accessibility = "See availability")
    private WebElement seeAvailabilityButton;

    public SearchResultsPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForResults() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            org.openqa.selenium.By.id("com.booking:id/property_card")
        ));
    }

    public void findAndClickTolipHotel() {
        String hotelName = "Tolip Hotel Alexandria";
        boolean found = false;

        // Scroll and search for hotel
        waitForResults();
        
        // Scroll through results
        for (int i = 0; i < 3; i++) {
            for (WebElement card : hotelCards) {
                String text = card.getText();
                if (text.contains("Tolip Hotel Alexandria")) {
                    // Find and click "See availability" button
                    try {
                        WebElement seeAvailability = card.findElement(
                            org.openqa.selenium.By.xpath(".//*[contains(@text, 'See availability')]")
                        );
                        seeAvailability.click();
                        found = true;
                        break;
                    } catch (Exception e) {
                        card.click();
                        found = true;
                        break;
                    }
                }
            }
            
            if (found) break;
            
            // Scroll down to load more results
            try {
                int height = driver.manage().window().getSize().getHeight();
                int width = driver.manage().window().getSize().getWidth();
                int startX = width / 2;
                int startY = (int) (height * 0.8);
                int endY = (int) (height * 0.2);
                // Use TouchAction for scrolling
                io.appium.java_client.TouchAction touchAction = new io.appium.java_client.TouchAction(driver);
                touchAction.press(io.appium.java_client.touch.offset.PointOption.point(startX, startY))
                    .moveTo(io.appium.java_client.touch.offset.PointOption.point(startX, endY))
                    .release()
                    .perform();
                Thread.sleep(1000);
            } catch (Exception e) {
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("Tolip Hotel Alexandria not found in search results");
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(
            org.openqa.selenium.By.id("com.booking:id/hotel_details")
        ));
    }
}

