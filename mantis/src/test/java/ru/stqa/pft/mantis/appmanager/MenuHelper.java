package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class MenuHelper extends HelperBase {

    public MenuHelper(ApplicationManager app) {
        super(app);
    }

    public void manage() {
        if(!driver.findElement(By.xpath("//div[@id='sidebar']")).isDisplayed()) {
            driver.findElement(By.xpath("//button[@id='menu-toggler']")).click();
            wait.until(el->driver.findElement(By.xpath("//div[@id='sidebar']")).isDisplayed());
        }
        driver.findElement(By.xpath("//a[contains(@href, 'manage_overview_page.php')]")).click();
    }

    public void manageUser() {
        driver.findElement(By.xpath("//a[contains(@href, 'manage_user_page.php')]")).click();
    }
}
