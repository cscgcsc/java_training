package ru.stqa.pft.mantis.tests;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.*;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.Account;

public class TestBaseAuth {
    protected static ApplicationManager app;

    @BeforeSuite
    public void SetUp() throws Exception {
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        app.auth.login(new Account(app.properties.getProperty("login"), app.properties.getProperty("password")));
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
