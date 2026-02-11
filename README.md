# Booking.com Mobile Automation Tests - Appium Java

This project contains automated mobile tests for Booking.com using Appium, TestNG, Maven, and Page Object Model (POM) design pattern.

## 📋 Requirements

- Java 11 or higher
- Maven 3.6 or higher
- Appium Server (latest version)
- Android SDK (for Android testing) or Xcode (for iOS testing)
- Android Emulator or iOS Simulator / Real Device
- Booking.com mobile app installed on device/emulator

## 🚀 Setup

### 1. Install Appium Server

```bash
npm install -g appium
npm install -g @appium/doctor
appium doctor
```

### 2. Install Appium Drivers

```bash
appium driver install uiautomator2  # For Android
appium driver install xcuitest      # For iOS
```

### 3. Start Appium Server

```bash
appium
```

### 4. Setup Android Emulator/Device

- Start Android Emulator or connect physical device
- Enable USB debugging
- Verify device is connected: `adb devices`

### 5. Install Booking.com App

- Download Booking.com APK (Android) or install from App Store (iOS)
- Install on device/emulator

### 6. Clone and Setup Project

```bash
git clone https://github.com/YOUR_USERNAME/booking-automation-appium-java.git
cd booking-automation-appium-java
mvn clean install
```

### 7. Create Excel Test Data

```bash
mvn exec:java -Dexec.mainClass="com.booking.utils.CreateExcelData" -Dexec.classpathScope=test
```

Or manually create `data/test-data.xlsx` with:
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
│       │           ├── base/              # Base test class
│       │           │   └── BaseTest.java
│       │           ├── pages/             # Page Object Model classes
│       │           │   ├── HomePage.java
│       │           │   ├── SearchResultsPage.java
│       │           │   ├── HotelDetailsPage.java
│       │           │   └── ReservationPage.java
│       │           ├── tests/             # Test classes
│       │           │   └── BookingFlowTest.java
│       │           └── utils/             # Utility classes
│       │               ├── ExcelDataProvider.java
│       │               ├── DateHelper.java
│       │               └── CreateExcelData.java
│       └── resources/
│           └── appium.properties
├── data/                                  # Test data files
│   └── test-data.xlsx
├── pom.xml
├── testng.xml
└── README.md
```

## 🧪 Test Cases

The project includes the following test cases:

1. **Complete booking flow** - End-to-end test covering:
   - Opening Booking.com app
   - Searching for Alexandria location
   - Selecting check-in (1 week from today) and check-out (4 days after check-in) dates
   - Finding and selecting Tolip Hotel Alexandria
   - Selecting bed and amount
   - Clicking "I'll reserve" button

2. **Verify check-in and check-out dates in details page** - Asserts that the chosen dates are displayed correctly on the hotel details page.

3. **Verify hotel name in reservation page** - Asserts that "Tolip Hotel Alexandria" is shown in the reservation page.

## 🏃 Running Tests

### Update testng.xml with your device details:

Edit `testng.xml` and update:
- `deviceName`: Your device/emulator name (check with `adb devices`)
- `platformVersion`: Your Android/iOS version
- `appPackage`: Booking.com app package name
- `appActivity`: Booking.com app main activity
- `appiumServerUrl`: Appium server URL (default: http://localhost:4723/wd/hub)

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

## 📱 Mobile-Specific Features

- ✅ Appium Java Client 8.5.0
- ✅ Support for both Android and iOS
- ✅ Page Object Model with Appium PageFactory
- ✅ Touch actions for scrolling and gestures
- ✅ Mobile-specific locators (ID, Accessibility ID, XPath)
- ✅ Excel data provider using Apache POI
- ✅ Automatic date calculation

## 🔧 Configuration

### Android Configuration

In `testng.xml`:
```xml
<parameter name="platform" value="android"/>
<parameter name="deviceName" value="emulator-5554"/>
<parameter name="platformVersion" value="11.0"/>
<parameter name="appPackage" value="com.booking"/>
<parameter name="appActivity" value="com.booking.ui.activities.SplashActivity"/>
```

### iOS Configuration

In `testng.xml`:
```xml
<parameter name="platform" value="ios"/>
<parameter name="deviceName" value="iPhone 13"/>
<parameter name="platformVersion" value="15.0"/>
<parameter name="appPackage" value="com.booking.iphone"/>
```

### Finding App Package and Activity

**Android:**
```bash
adb shell pm list packages | grep booking
adb shell dumpsys window windows | grep -E 'mCurrentFocus'
```

**iOS:**
- Check Bundle ID in Xcode or App Store Connect

## 📝 Notes

- The tests use mobile-specific selectors that may need adjustment based on the actual app
- Appium server must be running before executing tests
- Device/emulator must be connected and unlocked
- App must be installed on the device/emulator
- Selectors are examples and may need to be updated based on the actual app structure

## 🐛 Troubleshooting

### Appium Connection Issues
- Verify Appium server is running: `appium --version`
- Check server URL in testng.xml
- Ensure device is connected: `adb devices` (Android)

### Element Not Found
- Use Appium Inspector to find correct selectors
- Update selectors in Page Object classes
- Add appropriate waits

### App Not Launching
- Verify app package and activity names
- Ensure app is installed on device
- Check app permissions

## 📦 Dependencies

- **Appium Java Client** 8.5.0
- **Selenium WebDriver** 4.15.0
- **TestNG** 7.8.0
- **Apache POI** 5.2.4 (for Excel)

## 📄 License

ISC

## 👤 Author

Junior QA Engineer

