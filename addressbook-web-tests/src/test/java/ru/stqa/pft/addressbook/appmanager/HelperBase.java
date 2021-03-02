package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HelperBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void Type(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        }
    }

    protected void SelectByValue(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            Select select = new Select(element);
            select.selectByValue(value);
        }
    }

    protected boolean IsElementPresent(By by)
    {
        return driver.findElements(by).size() > 0;
    }
}
