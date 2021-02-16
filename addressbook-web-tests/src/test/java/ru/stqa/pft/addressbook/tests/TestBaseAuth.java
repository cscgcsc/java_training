package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.Account;

public class TestBaseAuth {
    protected ApplicationManager app;

    @BeforeMethod
    public void SetUp() {
        app = new ApplicationManager();
        Account account = new Account("admin", "secret");
        app.auth.login(account);
    }

    @AfterMethod
    public void TearDown() {
        app.driver.quit();
    }
}
