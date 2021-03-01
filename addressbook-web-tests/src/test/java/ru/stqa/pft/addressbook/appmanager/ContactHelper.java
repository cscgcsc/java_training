package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Contact;
import java.util.ArrayList;
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

    public void selectContact(int index) {
        List<WebElement> elements = getCheckboxList();
        elements.get(index).click();
    }

    public void submitRemoval() {
        driver.findElement(By.xpath("//input[@value='Delete']")).click();
        driver.switchTo().alert().accept();
    }

    public void initModification(int index) {
        List<WebElement> elements = driver.findElements(By.xpath("//table[@id='maintable']//a[contains(@href,'edit.php?id=')]"));
        elements.get(index).click();
    }

    public void submitModification() {
        List<WebElement> elements = driver.findElements(By.xpath("//input[@value='Update']"));
        elements.get(0).click();
    }

    public List<WebElement> getCheckboxList() {
        return driver.findElements(By.xpath("//table[@id='maintable']//input[@name='selected[]']"));
    }

    public List<Contact> getContactsList() {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='maintable']//tr"));
        List<Contact> contacts = new ArrayList<>();
        for(WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            if (cells.size() == 0)
                continue;
            Contact newContact = new Contact();
            newContact.setId(Integer.parseInt(cells.get(0).findElement(By.xpath(".//input[@name='selected[]']")).getAttribute("value")));
            newContact.setFirstname(cells.get(2).getText());
            newContact.setLastname(cells.get(1).getText());
            contacts.add(newContact);
        }
        return contacts;
    }

    public void CreateContact(Contact contact) {
        goToNewContactPage();
        fillContactForm(contact);
        submitCreation();
        returnToHomePage();
    }

    public List<WebElement> getMessage() {
        return driver.findElements(By.xpath("//div[contains(@class, 'msgbox')]"));
    }

    public String getMessageText() {
        List<WebElement> elements = getMessage();
        if(elements.size() > 0)
            return elements.get(0).getText();
        else
            return "";
    }

    public void waitUpdatingPage() {
        wait.until(d -> !IsElementPresent(By.xpath("//div[contains(@class, 'msgbox')]")));
    }
}
