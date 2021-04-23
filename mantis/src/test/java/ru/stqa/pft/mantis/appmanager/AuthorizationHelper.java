package ru.stqa.pft.mantis.appmanager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.model.Account;

public class AuthorizationHelper extends HelperBase {

    public AuthorizationHelper(ApplicationManager app) {
        super(app);
    }

    public void login(Account account) {
        type(By.xpath("//input[@id='username']"), account.getLogin());
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        waitForElementPresent(By.xpath("//input[@name='password']"));
        type(By.xpath("//input[@name='password']"), account.getPassword());
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        waitForElementPresent(By.xpath("//a[contains(@href, 'logout_page.php')]"));
    }

    public void logout() {

    }
}
