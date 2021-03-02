package ru.stqa.pft.addressbook.tests;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.Account;

public class TestBaseAuth {
    protected static ApplicationManager app;

    @BeforeSuite
    public void SetUp() {
        app = new ApplicationManager(BrowserType.CHROME);
        app.auth.login(new Account("admin", "secret"));
    }

    @AfterSuite
    public void TearDown() {
        app.auth.logout();
        app.stop();
    }

    protected void TestWait() {
        Sleep element = new Sleep();
        element.doSleep(10000);
    }
}
