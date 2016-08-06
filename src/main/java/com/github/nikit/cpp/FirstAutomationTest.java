package com.github.nikit.cpp;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.MarionetteDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by nik on 06.08.16.
 */
public class FirstAutomationTest {

    public static final String PROPERTY_BROWSER = "browser";
    public static final String PROPERTY_BROWSER_FIREFOX = "";
    public static final String PROPERTY_BROWSER_CHROME = "chrome";
    public static final String PROPERTY_BROWSER_PHANTOM = "phantomjs"; // headless browser based on webKit as Chrome
    public static final String PROPERTY_BROWSER_PHANTOM_LOG = "phantomjs.log";

    static WebDriver driver;

    @BeforeClass
    public static void init(){
        // http://www.slf4j.org/legacy.html#jul-to-slf4j
        // http://www.slf4j.org/api/org/slf4j/bridge/SLF4JBridgeHandler.html
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        // System.setProperty("webdriver.gecko.driver", "/home/nik/bin/geckodriver");

        String propBrowser = System.getProperty(PROPERTY_BROWSER);
        if (PROPERTY_BROWSER_PHANTOM.equals(propBrowser)){
            PhantomJsDriverManager.getInstance().setup();

            // not works properly for PhantomJs
//            LoggingPreferences logs = new LoggingPreferences();
//            logs.enable(LogType.BROWSER, Level.ALL);
//            logs.enable(LogType.CLIENT, Level.ALL);
//            logs.enable(LogType.DRIVER, Level.ALL);
//            logs.enable(LogType.PERFORMANCE, Level.ALL);
//            logs.enable(LogType.PROFILER, Level.ALL);
//            logs.enable(LogType.SERVER, Level.ALL);
//            DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
//            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
//            driver = new PhantomJSDriver(desiredCapabilities);

            String phantomLogProp = System.getProperty(PROPERTY_BROWSER_PHANTOM_LOG);
            File phantomLog = null;
            if (phantomLogProp != null){
                phantomLog = new File(phantomLogProp);
            }
            String phantomjspath = System.getProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY); // setted by PhantomJsDriverManager.getInstance().setup();
            PhantomJSDriverService phantomJSDriverService = new PhantomJSDriverService.Builder()
                    .usingPhantomJSExecutable(new File(phantomjspath))
                    .withLogFile(phantomLog)
                    .usingAnyFreePort()
                    .build();
            driver = new PhantomJSDriver(phantomJSDriverService, DesiredCapabilities.phantomjs());
            //driver = new PhantomJSDriver();
        } else if(PROPERTY_BROWSER_CHROME.equals(propBrowser)) {
            ChromeDriverManager.getInstance().setup();
            driver = new ChromeDriver();
        } else {
            // firefox
            MarionetteDriverManager.getInstance().setup();
            //Step 1- Driver Instantiation: Instantiate driver object as FirefoxDriver

            // http://stackoverflow.com/questions/13204820/how-to-obtain-native-logger-in-selenium-webdriver
//            LoggingPreferences logs = new LoggingPreferences();
//            logs.enable(LogType.BROWSER, Level.ALL);
//            logs.enable(LogType.CLIENT, Level.ALL);
//            logs.enable(LogType.DRIVER, Level.ALL);
//            logs.enable(LogType.PERFORMANCE, Level.ALL);
//            logs.enable(LogType.PROFILER, Level.ALL);
//            logs.enable(LogType.SERVER, Level.ALL);
//
//            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
//            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
//            driver = new FirefoxDriver(desiredCapabilities);

            driver = new FirefoxDriver();
        }
    }

    @AfterClass
    public static void drop(){
        //Step 4- Close Driver
        driver.close();

        //Step 5- Quit Driver
        driver.quit();
    }

    //We should add @Test annotation that JUnit will run below method
    @Test
    //Start to write our test method. It should ends with "Test"
    public void teknosaTest(){

        //Step 2- Navigation: Open a website
        driver.navigate().to("https://www.teknosa.com/");

        //Step 3- Assertion: Check its title is correct
        //assertEquals method Parameters: Message, Expected Value, Actual Value
        Assert.assertEquals("Title check failed!", "Teknosa Alışveriş Sitesi - Herkes İçin Teknoloji", driver.getTitle());

//        Logs logs = driver.manage().logs();
//        LogEntries logEntries = logs.get(LogType.DRIVER);
//
//        for (LogEntry logEntry : logEntries) {
//            System.out.println(logEntry.getMessage());
//        }
    }
}
