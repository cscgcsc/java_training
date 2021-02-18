package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Contact;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void returnToHomePage() {
        driver.findElement(By.xpath("//div[contains(@class, 'msgbox')]//a[contains(@href, 'index.php')]")).click();
    }

    public void submitCreation() {
        driver.findElement(By.xpath("//input[@name='submit']")).click();
    }

    public void fillContactForm(Contact contact) {
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

    public void goToNewContactPage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, 'edit.php')]")).click();
    }

    public void goToHomePage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, './')]")).click();
    }

    public void selectContact(int i) {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='maintable']//input[@name='selected[]']"));
        if(i >= 0 && i < elements.size())
            elements.get(i).click();
    }

    public void submitRemoval() {
        driver.findElement(By.xpath("//input[@value='Delete']")).click();
        driver.switchTo().alert().accept();
    }

    public void initModification(int i) {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='maintable']//a[contains(@href,'edit.php?id=')]"));
        if(i >= 0 && i < elements.size())
            elements.get(i).click();
    }

    public void submitModification() {
        List<WebElement> elements = driver.findElements(By.xpath("//input[@value='Update']"));
        elements.get(0).click();
    }
}
