package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.Account;

public class AuthorizationHelper extends HelperBase {

    public AuthorizationHelper(WebDriver driver) {
        super(driver);
    }

    public void login(Account user) {
        Type(By.xpath("//input[@name='user']"), user.getName());
        Type(By.xpath("//input[@name='pass']"), user.getPassword());
        driver.findElement(By.xpath("//input[@value='Login']")).click();
    }

    public void logout() {
        driver.findElement(By.xpath("//form[@name='logout']//a[contains(text(), 'Logout')]")).click();
    }
}
