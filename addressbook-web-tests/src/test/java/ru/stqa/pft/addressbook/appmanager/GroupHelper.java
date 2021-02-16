package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.Group;

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
}
