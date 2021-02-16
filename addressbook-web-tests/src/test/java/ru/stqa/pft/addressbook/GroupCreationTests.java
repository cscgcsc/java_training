package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class GroupCreationTests {
    ChromeDriver driver;

    @BeforeMethod
    public void SetUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.get("http://localhost/addressbook/");

        Account account = new Account("admin", "secret");
        login(account);
    }

    private void login(Account user) {
        Type(By.xpath("//input[@name='user']"), user.getName());
        Type(By.xpath("//input[@name='pass']"), user.getPassword());
        driver.findElement(By.xpath("//input[@value='Login']")).click();
    }

    private void goToGroupPage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, 'group.php')]")).click();
    }

    private void initGroupCreation() {
        driver.findElement(By.xpath("//input[@name='new']")).click();
    }

    public void Type(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        }
    }
    @Test
    public void testGroupCreation() {
        goToGroupPage();
        initGroupCreation();
        Group group = new Group("Text 1", "Text 2", "Text 3");
        fillGroupForm(group);
        submitCreation();
        returnToGroupPage();
        logout();
    }

    private void logout() {
        driver.findElement(By.xpath("//form[@name='logout']//a[contains(text(), 'Logout')]")).click();
    }

    private void returnToGroupPage() {
        driver.findElement(By.xpath("//div[contains(@class, 'msgbox')]//a[contains(@href, 'group.php')]")).click();
    }

    private void submitCreation() {
        driver.findElement(By.xpath("//input[@name='submit']")).click();
    }

    private void fillGroupForm(Group group) {
        Type(By.xpath("//input[@name='group_name']"), group.getName());
        Type(By.xpath("//textarea[@name='group_header']"), group.getHeader());
        Type(By.xpath("//textarea[@name='group_footer']"), group.getFooter());
    }

    @AfterMethod
    public void TearDown() {
        driver.quit();
    }
}
