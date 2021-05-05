package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
    public WebDriver driver;
    public AuthorizationHelper auth;
    public GroupHelper group;
    public ContactHelper contact;
    public DBHelper db;
    public ORMHelper orm;
    public Properties properties;

    public ApplicationManager(String browser) throws IOException {

        db = new DBHelper();
        orm = new ORMHelper();

        properties = new Properties();
        String settings = System.getProperty("settings", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", settings)));
        if(properties.getProperty("seleniumServer").equals("")) {
            if (browser.equals(BrowserType.CHROME)) {
                driver = new ChromeDriver();
            } else if (browser.equals(BrowserType.FIREFOX)) {
                driver = new FirefoxDriver();
            } else if (browser.equals(BrowserType.IE)) {
                driver = new InternetExplorerDriver();
            } else {
                driver = new ChromeDriver();
            }
        }
        else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            //capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "WIN10")));
            driver = new RemoteWebDriver(new URL(properties.getProperty("seleniumServer")),capabilities);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get(properties.getProperty("baseUrl"));

        auth = new AuthorizationHelper(driver);
        group = new GroupHelper(driver);
        contact = new ContactHelper(driver);
    }

    public void stop() {
        driver.quit();
        db.closeConnection();
    }
}
