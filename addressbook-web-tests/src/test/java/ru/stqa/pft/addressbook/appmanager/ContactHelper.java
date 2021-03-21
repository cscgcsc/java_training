package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import java.util.List;


public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void create(Contact contact) {
        goToNewContactPage();
        fillForm(contact);
        submitCreation();
        returnToHomePage();
    }

    public void modify(Contact contact) {
        initModification(contact);
        fillForm(contact);
        submitModification();
        returnToHomePage();
    }

    public void remove(Contact contact) {
        selectContact(contact);
        submitRemoval();
    }

    public void fillForm(Contact contact) {
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

    public Contact getInformationFromForm() {
        Contact contact = new Contact();
        contact.setFirstname(driver.findElement(By.xpath("//input[@name='firstname']")).getAttribute("value"));
        contact.setLastname(driver.findElement(By.xpath("//input[@name='lastname']")).getAttribute("value"));
        contact.setMiddlename(driver.findElement(By.xpath("//input[@name='middlename']")).getAttribute("value"));
        contact.setEmail(driver.findElement(By.xpath("//input[@name='email']")).getAttribute("value"));
        contact.setEmail2(driver.findElement(By.xpath("//input[@name='email2']")).getAttribute("value"));
        contact.setEmail3(driver.findElement(By.xpath("//input[@name='email3']")).getAttribute("value"));
        contact.setHomePhone(driver.findElement(By.xpath("//input[@name='home']")).getAttribute("value"));
        contact.setMobilePhone(driver.findElement(By.xpath("//input[@name='mobile']")).getAttribute("value"));
        contact.setWorkPhone(driver.findElement(By.xpath("//input[@name='work']")).getAttribute("value"));
        contact.setAddress(driver.findElement(By.xpath("//textarea[@name='address']")).getAttribute("value"));
        contact.setBday(driver.findElement(By.xpath("//select[@name='bday']")).getAttribute("value"));
        contact.setBmonth(driver.findElement(By.xpath("//select[@name='bmonth']")).getAttribute("value"));
        contact.setByear(driver.findElement(By.xpath("//input[@name='byear']")).getAttribute("value"));
        return contact;
    }

    public void selectContact(Contact contact) {
        driver.findElement(By.xpath("//table[@id='maintable']//input[@name='selected[]' and @value='" + contact.getId() + "']")).click();
    }

    public void submitRemoval() {
        driver.findElement(By.xpath("//input[@value='Delete']")).click();
        driver.switchTo().alert().accept();
    }

    public void initModification(Contact contact) {
        driver.findElement(By.xpath("//table[@id='maintable']//a[contains(@href,'edit.php?id=" + contact.getId() + "')]")).click();
    }

    public void submitModification() {
        List<WebElement> elements = driver.findElements(By.xpath("//input[@value='Update']"));
        elements.get(0).click();
    }

    public void submitCreation() {
        driver.findElement(By.xpath("//input[@name='submit']")).click();
    }

    public void goToNewContactPage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, 'edit.php')]")).click();
    }

    public void returnToHomePage() {
        driver.findElement(By.xpath("//div[contains(@class, 'msgbox')]//a[contains(@href, 'index.php')]")).click();
    }
    public void goToHomePage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, './')]")).click();
    }

    public List<WebElement> getCheckboxList() {
        return driver.findElements(By.xpath("//table[@id='maintable']//input[@name='selected[]']"));
    }

    public Contacts getAll() {
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='maintable']//tr"));
        Contacts contacts = new Contacts();
        for(WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            if (cells.size() == 0)
                continue;
            Contact newContact = new Contact();
            newContact.setId(Integer.parseInt(cells.get(0).findElement(By.xpath(".//input[@name='selected[]']")).getAttribute("value")));
            newContact.setFirstname(cells.get(2).getText());
            newContact.setLastname(cells.get(1).getText());
            newContact.setAddress(cells.get(3).getText());
            newContact.setAllEmails(cells.get(4).getText());
            newContact.setAllPhones(cells.get(5).getText());
            contacts.add(newContact);
        }
        return contacts;
    }

    public List<WebElement> getMessage() {
        WaitForElementPresent(By.xpath("//div[contains(@class, 'msgbox')]"));
        return driver.findElements(By.xpath("//div[contains(@class, 'msgbox')]"));
    }

    public String getMessageText() {
        List<WebElement> elements = getMessage();
        if (elements.size() == 0) {
            return "Message box doesn't exist";
        } else {
            String text = elements.get(0).getText();
            wait.until(d -> !IsElementPresent(By.xpath("//div[contains(@class, 'msgbox')]")));
            return text;
        }
    }
}
