package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.*;

public class GroupHelper extends HelperBase{

    public GroupHelper(WebDriver driver) {
        super(driver);
    }

    public void create(Group group) {
        initCreation();
        fillForm(group);
        submitCreation();
        returnToGroupPage();
    }

    public void modify(Group group) {
        selectGroup(group);
        initModification();
        fillForm(group);
        submitModification();
        returnToGroupPage();
    }

    public void remove(Group group) {
        selectGroup(group);
        submitRemoval();
        returnToGroupPage();
    }

    public void fillForm(Group group) {
        Type(By.xpath("//input[@name='group_name']"), group.getName());
        Type(By.xpath("//textarea[@name='group_header']"), group.getHeader());
        Type(By.xpath("//textarea[@name='group_footer']"), group.getFooter());
    }

    public void submitCreation() {
        driver.findElement(By.xpath("//input[@name='submit']")).click();
    }

    public void initCreation() {
        driver.findElement(By.xpath("//input[@name='new']")).click();
    }

    public void selectGroup(Group group) {
        driver.findElement(By.xpath("//div[@id='content']//input[@name='selected[]' and @value='" + group.getId() + "']")).click();
    }

    public void submitRemoval() {
        driver.findElement(By.xpath("//input[@name='delete']")).click();
    }

    public void initModification() {
        driver.findElement(By.xpath("//input[@name='edit']")).click();
    }

    public void submitModification() {
        driver.findElement(By.xpath("//input[@name='update']")).click();
    }

    public void returnToGroupPage() {
        driver.findElement(By.xpath("//div[contains(@class, 'msgbox')]//a[contains(@href, 'group.php')]")).click();
    }

    public void goToGroupPage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, 'group.php')]")).click();
    }

    public List<WebElement> getCheckboxList() {
         return driver.findElements(By.xpath("//div[@id='content']//input[@name='selected[]']"));
    }

    public Groups getAll() {
        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='content']//span[contains(@class, 'group')]"));
        Groups groups = new Groups();
        for(WebElement element : elements) {
            Group newGroup = new Group();
            newGroup.setId(Integer.parseInt(element.findElement(By.xpath(".//input[@name='selected[]']")).getAttribute("value")));
            newGroup.setName(element.getText());
            groups.add(newGroup);
        }
        return groups;
    }
}
