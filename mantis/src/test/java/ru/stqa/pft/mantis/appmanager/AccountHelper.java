package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.mantis.model.Account;

import java.util.List;

public class AccountHelper extends HelperBase{

    public AccountHelper(ApplicationManager app) {
        super(app);
    }

    public void resetPassword(Integer id) {
        app.menu().manage();
        app.menu().manageUser();
        selectAccount(id);
        reset();
    }

    private void selectAccount(Integer id) {
        driver.findElement(By.xpath("//div[contains(@class, 'table-responsive')]//a[contains(@href, 'manage_user_edit_page.php?user_id=" + id + "')]")).click();
    }

    private void reset() {
        driver.findElement(By.xpath("//form[@id='manage-user-reset-form']//input[@type='submit']")).click();
    }

    public boolean isSuccessAlertPresent() {
        return isElementPresent(By.xpath("//div[contains(@class, 'alert-success')]"));
    }


    public void changePassword(String url, Account newAccount) {
        driver.get(url);
        waitForElementPresent(By.xpath("//form[@id='account-update-form']"));
        type(By.xpath("//input[@id='realname']"), newAccount.getLogin());
        type(By.xpath("//input[@id='password']"), newAccount.getPassword());
        type(By.xpath("//input[@id='password-confirm']"), newAccount.getPassword());
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        //waitForElementPresent(By.xpath("//form[@id='login-form']"));
    }
}
