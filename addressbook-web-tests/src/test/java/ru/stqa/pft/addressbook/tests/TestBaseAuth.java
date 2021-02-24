package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.Account;

public class TestBaseAuth {
    protected ApplicationManager app;

    @BeforeMethod
    public void SetUp() {
        app = ApplicationManager.getInstance(BrowserType.CHROME);
        Account account = new Account("admin", "secret");
        app.auth.login(account);
    }

    @AfterTest
    public void TearDown() {
        ApplicationManager.Stop();
    }
}
