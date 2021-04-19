package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
    public WebDriver driver;
    public AuthorizationHelper auth;
    public Properties properties;

    public ApplicationManager(String browser) throws IOException {

        if(browser.equals(BrowserType.CHROME)) {
            driver = new ChromeDriver();
        } else if(browser.equals(BrowserType.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if(browser.equals(BrowserType.IE)) {
            driver = new InternetExplorerDriver();
        } else {
            driver = new ChromeDriver();
        }

        properties = new Properties();
        String settings = System.getProperty("settings", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", settings)));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get(properties.getProperty("baseUrl"));

        auth = new AuthorizationHelper(driver);
    }

    public void stop() {
        driver.quit();
    }
}