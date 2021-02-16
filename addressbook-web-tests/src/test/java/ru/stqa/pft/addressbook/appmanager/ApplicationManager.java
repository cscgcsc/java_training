package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class ApplicationManager {
    public WebDriver driver;
    public AuthorizationHelper auth;
    public GroupHelper group;
    public ContactHelper contact;

    public ApplicationManager() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get("http://localhost/addressbook/");

        auth = new AuthorizationHelper(driver);
        group = new GroupHelper(driver);
        contact = new ContactHelper(driver);
    }
}
