# Booking.com Mobile Automation Tests - Appium Java

This project contains automated mobile tests for Booking.com using Appium, Selenium WebDriver, TestNG, Maven, and Page Object Model (POM) design pattern.

## 📋 Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Appium Server 2.0 or higher
- Android SDK (for Android testing) or Xcode (for iOS testing)
- Chrome browser installed on mobile device/emulator

## 🚀 Setup

### 1. Install Appium Server

```bash
npm install -g appium
npm install -g appium-doctor
```

Or download Appium Desktop from: https://github.com/appium/appium-desktop/releases

### 2. Install Appium Drivers

```bash
appium driver install uiautomator2  # For Android
appium driver install xcuitest      # For iOS
```

### 3. Start Appium Server

```bash
appium
```

Or start Appium Desktop GUI application.

### 4. Setup Android Emulator or iOS Simulator

**For Android:**
- Create an Android Virtual Device (AVD) using Android Studio
- Or connect a physical Android device via USB with USB debugging enabled

**For iOS:**
- Use Xcode Simulator
- Or connect a physical iOS device

### 5. Clone and Setup Project

```bash
git clone https://github.com/YOUR_USERNAME/booking-automation-appium-java.git
cd booking-automation-appium-java
mvn clean install
```

### 6. Create Excel Test Data

```bash
mvn exec:java -Dexec.mainClass="com.booking.utils.CreateExcelData" -Dexec.classpathScope=test
```

Or manually create `data/test-data.xlsx` with the following structure:
- Column A: Location (e.g., "Alexandria")
- Column B: CheckInDate (format: DD/MM/YYYY)
- Column C: CheckOutDate (format: DD/MM/YYYY)

## 📁 Project Structure

```
booking-automation-appium-java/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── booking/
│       │           ├── pages/          # Page Object Model classes
│       │           │   ├── HomePage.java
│       │           │   ├── SearchResultsPage.java
│       │           │   ├── HotelDetailsPage.java
│       │           │   └── ReservationPage.java
│       │           ├── tests/          # Test classes
│       │           │   └── BookingFlowTest.java
│       │           └── utils/           # Utility classes
│       │               ├── AppiumDriverManager.java
│       │               ├── ExcelDataProvider.java
│       │               ├── DateHelper.java
│       │               └── CreateExcelData.java
│       └── resources/
│           └── testng.xml
├── data/                               # Test data files
│   └── test-data.xlsx
├── pom.xml
├── testng.xml
└── README.md
```

## 🧪 Test Cases

The project includes the following test cases:

1. **Complete booking flow** - End-to-end test covering:
   - Opening booking.com in mobile browser
   - Searching for Alexandria location
   - Selecting check-in (1 week from today) and check-out (4 days after check-in) dates
   - Finding and selecting Tolip Hotel Alexandria
   - Selecting bed and amount
   - Clicking "I'll reserve" button

2. **Verify check-in and check-out dates in details page** - Asserts that the chosen dates are displayed correctly on the hotel details page.

3. **Verify hotel name in reservation page** - Asserts that "Tolip Hotel Alexandria" is shown in the reservation page.

## 📊 Test Data

Test data is stored in `data/test-data.xlsx` with the following columns:
- **Location**: Search location (e.g., "Alexandria")
- **CheckInDate**: Check-in date (format: DD/MM/YYYY)
- **CheckOutDate**: Check-out date (format: DD/MM/YYYY)

If dates are not provided in Excel, the system will automatically calculate:
- Check-in: 1 week from today
- Check-out: 4 days after check-in

## 🏃 Running Tests

### Run all tests:
```bash
mvn test
```

### Run specific test class:
```bash
mvn test -Dtest=BookingFlowTest
```

### Run with TestNG XML:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run in IDE:
- Right-click on `testng.xml` → Run As → TestNG Suite
- Or right-click on `BookingFlowTest.java` → Run As → TestNG Test

## ⚙️ Configuration

### Change Platform (Android/iOS)

Edit `BookingFlowTest.java`:
```java
private String platform = "android"; // Change to "ios" for iOS testing
```

### Appium Server URL

Edit `AppiumDriverManager.java` if your Appium server is running on a different port:
```java
private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
```

### Device Capabilities

Edit `AppiumDriverManager.java` to customize device capabilities:
- Platform version
- Device name
- Browser name
- Automation name

## 🎯 Features

- ✅ Page Object Model (POM) design pattern
- ✅ Excel data provider using Apache POI
- ✅ TestNG for test execution and reporting
- ✅ Support for both Android and iOS
- ✅ Mobile web browser automation
- ✅ Automatic date calculation
- ✅ Comprehensive test coverage
- ✅ Maven project structure

## 📝 Notes

- The tests handle dynamic content and may need selector adjustments based on Booking.com's UI changes
- Cookies popup is automatically handled
- Tests include proper waits and error handling
- Supports both native app locators and mobile web locators
- Make sure Appium server is running before executing tests

## 📦 Dependencies

- **Appium Java Client** 8.5.0
- **Selenium WebDriver** 4.15.0
- **TestNG** 7.8.0
- **Apache POI** 5.2.4 (for Excel)
- **WebDriverManager** 5.6.2 (for driver management)

## 🔧 Troubleshooting

### Appium Connection Issues
- Ensure Appium server is running on port 4723
- Check device/emulator is connected and visible: `adb devices` (Android) or `xcrun simctl list` (iOS)
- Verify Appium drivers are installed: `appium driver list`

### Test Execution Issues
- Ensure Chrome browser is installed on the device/emulator
- Check device capabilities match your device/emulator
- Verify network connectivity for web testing

## 📄 License

ISC

## 👤 Author

Mina Nagy QA Engineer
