package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.pft.mantis.model.Account;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
    private WebDriver driver;
    private String browser;
    private AuthorizationHelper auth;
    public Properties properties;
    private HttpSession httpSession;
    private SmtpServer smtpServer;
    private WebDriverWait wait;
    private NavigationHelper navigation;
    private MenuHelper menu;
    private ORMHelper orm;
    private AccountHelper account;

    public ApplicationManager(String browserName) throws IOException {
        browser = browserName;
        loadProperties();
    }

    public void stop() {
        if(driver != null) driver.quit();
        if(httpSession != null) httpSession.close();
    }

    private void loadProperties() throws IOException {
        properties = new Properties();
        String settings = System.getProperty("settings", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", settings)));
    }

    public HttpSession httpSession() {
       if(httpSession == null) httpSession = new HttpSession(this);
       return httpSession;
    }

    public SmtpServer smtpServer() {
        if(smtpServer == null) smtpServer = new SmtpServer();
        return smtpServer;
    }

    public ORMHelper orm() {
        if(orm == null) orm = new ORMHelper();
        return orm;
    }

    public AuthorizationHelper auth() {
        if(auth == null) auth = new AuthorizationHelper(this);
        return auth;
    }

    public NavigationHelper navigation() {
        if(navigation == null) navigation = new NavigationHelper(this);
        return navigation;
    }

    public AccountHelper account() {
        if(account == null) account = new AccountHelper(this);
        return account;
    }

    public MenuHelper menu() {
        if(menu == null) menu = new MenuHelper(this);
        return menu;
    }

    public WebDriver startDriver() {
        if(driver == null) {
            if(browser.equals(BrowserType.CHROME)) {
                driver = new ChromeDriver();
            } else if(browser.equals(BrowserType.FIREFOX)) {
                driver = new FirefoxDriver();
            } else if(browser.equals(BrowserType.IE)) {
                driver = new InternetExplorerDriver();
            } else {
                driver = new ChromeDriver();
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            driver.get(properties.getProperty("baseUrl"));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        return driver;
    }

    public WebDriverWait driverWait() {
        return wait;
    }

    public String getBaseURL() {
        return properties.getProperty("baseUrl");
    }
}
