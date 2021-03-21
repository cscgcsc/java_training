package ru.stqa.pft.addressbook.appmanager;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class HelperBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void type(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        }
    }

    protected void attach(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            element.sendKeys(new File(value).getAbsolutePath());
        }
    }

    protected void selectByValue(By by, String value) {
        if(value != null) {
            WebElement element = driver.findElement(by);
            Select select = new Select(element);
            select.selectByValue(value);
        }
    }

    protected boolean isElementPresent(By by)
    {
        return driver.findElements(by).size() > 0;
    }

    protected void waitForElementPresent(By by)  {
        Sleep element = new Sleep();
        for (int second = 0;; second++) {
            if (second >= 10)
                break;
            if (isElementPresent(by))
                break;
            element.doSleep(1000);
        }
    }
}
