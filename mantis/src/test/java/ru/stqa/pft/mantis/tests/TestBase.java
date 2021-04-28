package ru.stqa.pft.mantis.tests;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class TestBase {
    protected static ApplicationManager app;

    @BeforeSuite
    public void SetUp() throws Exception {
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    }

    @AfterSuite
    public void TearDown() {
        app.stop();
    }

    public void skipIfNotFixed(int issueId) {
        if (app.soapClient().isIssueFixed(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    protected void TestWait() {
        Sleep element = new Sleep();
        element.doSleep(5000);
    }
}
