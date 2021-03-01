package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Group;

import java.util.*;

public class GroupHelper extends HelperBase{

    public GroupHelper(WebDriver driver) {
        super(driver);
    }

    public void returnToGroupPage() {
        driver.findElement(By.xpath("//div[contains(@class, 'msgbox')]//a[contains(@href, 'group.php')]")).click();
    }

    public void submitCreation() {
        driver.findElement(By.xpath("//input[@name='submit']")).click();
    }

    public void fillGroupForm(Group group) {
        Type(By.xpath("//input[@name='group_name']"), group.getName());
        Type(By.xpath("//textarea[@name='group_header']"), group.getHeader());
        Type(By.xpath("//textarea[@name='group_footer']"), group.getFooter());
    }

    public void goToGroupPage() {
        driver.findElement(By.xpath("//div[@id='nav']//a[contains(@href, 'group.php')]")).click();
    }

    public void initGroupCreation() {
        driver.findElement(By.xpath("//input[@name='new']")).click();
    }

    public void selectGroup(int i) {
        List<WebElement> elements = getCheckboxList();
        elements.get(i).click();
    }

    public void submitRemoval() {
        driver.findElement(By.xpath("//input[@name='delete']")).click();
    }

    public void initGroupModification() {
        driver.findElement(By.xpath("//input[@name='edit']")).click();
    }

    public void submitModification() {
        driver.findElement(By.xpath("//input[@name='update']")).click();
    }

    public List<WebElement> getCheckboxList() {
         return driver.findElements(By.xpath("//div[@id='content']//input[@name='selected[]']"));
    }

    public List<Group> getGroupsList() {
        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='content']//span[contains(@class, 'group')]"));
        List<Group> groups = new ArrayList<>();
        for(WebElement element : elements) {
            Group newGroup = new Group();
            newGroup.setId(Integer.parseInt(element.findElement(By.xpath(".//input[@name='selected[]']")).getAttribute("value")));
            newGroup.setName(element.getText());
            groups.add(newGroup);
        }
        return groups;
    }

    public void CreateGroup(Group group) {
        goToGroupPage();
        initGroupCreation();
        fillGroupForm(group);
        submitCreation();
        returnToGroupPage();
    }
}
