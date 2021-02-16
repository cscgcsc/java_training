package ru.stqa.pft.addressbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class ContactCreationTests {
    ChromeDriver driver;

    @BeforeMethod
    public void SetUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost/addressbook/");

        Account account = new Account("admin", "secret");
        login(account);
    }

    private void login(Account user) {
        Type(By.xpath("//input[@name='user']"), user.getName());
        Type(By.xpath("//input[@name='pass']"), user.getPassword());
        driver.findElement(By.xpath("//input[@value='Login']")).click();
    }

    private void goToNewContactPage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, 'edit.php')]")).click();
    }

    public void Type(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        }
    }

    public void SelectByValue(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            Select select = new Select(element);
            select.selectByValue(value);
        }
    }

    @Test
    public void testContactCreation() {
        goToNewContactPage();
        Contact contact = new Contact("Text 1", "Text 2");
        contact.setMiddlename("Text 3");
        contact.setEmail("Text 4");
        contact.setEmail2("Text 5");
        contact.setHomePhone("Text 6");
        contact.setMobilePhone("Text 7");
        contact.setAddress("Text 8");
        contact.setBday("5");
        contact.setBmonth("April");
        contact.setByear("1990");
        fillContactForm(contact);
        submitCreation();
        returnToHomePage();
        logout();
    }

    private void logout() {
        driver.findElement(By.xpath("//form[@name='logout']//a[contains(text(), 'Logout')]")).click();
    }

    private void returnToHomePage() {
        driver.findElement(By.xpath("//div[contains(@class, 'msgbox')]//a[contains(@href, 'index.php')]")).click();
    }

    private void submitCreation() {
        driver.findElement(By.xpath("//input[@name='submit']")).click();
    }

    private void fillContactForm(Contact contact) {
        Type(By.xpath("//input[@name='firstname']"), contact.getFirstname());
        Type(By.xpath("//input[@name='lastname']"), contact.getLastname());
        Type(By.xpath("//input[@name='middlename']"), contact.getMiddlename());
        Type(By.xpath("//input[@name='email']"), contact.getEmail());
        Type(By.xpath("//input[@name='email2']"), contact.getEmail2());
        Type(By.xpath("//input[@name='home']"), contact.getHomePhone());
        Type(By.xpath("//input[@name='mobile']"), contact.getMobilePhone());
        Type(By.xpath("//textarea[@name='address']"), contact.getAddress());
        SelectByValue(By.xpath("//select[@name='bday']"), contact.getBday());
        SelectByValue(By.xpath("//select[@name='bmonth']"), contact.getBmonth());
        Type(By.xpath("//input[@name='byear']"), contact.getByear());
    }

    @AfterMethod
    public void TearDown() {
        driver.quit();
    }
}
