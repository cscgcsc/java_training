package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.time.Duration;

public class ApplicationManager {
    public static WebDriver driver;
    public AuthorizationHelper auth;
    public GroupHelper group;
    public ContactHelper contact;
    private static ApplicationManager applicationManager;

    private ApplicationManager(String browser) {
        if(browser.equals(BrowserType.CHROME)) {
            driver = new ChromeDriver();
        } else if(browser.equals(BrowserType.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if(browser.equals(BrowserType.IE)) {
            driver = new InternetExplorerDriver();
        } else {
            driver = new ChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost/addressbook/");

        auth = new AuthorizationHelper(driver);
        group = new GroupHelper(driver);
        contact = new ContactHelper(driver);
    }

    public static ApplicationManager getInstance(String browser) {
        if(applicationManager != null) {
            return applicationManager;
        } else {
            applicationManager = new ApplicationManager(browser);
            return applicationManager;
        }
    }

    public static void Stop() {
        driver.quit();
    }
}
