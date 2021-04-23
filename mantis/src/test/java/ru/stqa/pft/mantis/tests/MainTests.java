package ru.stqa.pft.mantis.tests;
import org.subethamail.wiser.WiserMessage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Account;

import java.util.List;
import java.util.stream.Collectors;


public class MainTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.smtpServer().start();
    }

    @Test
    public void testMail() throws Exception {
        //get accounts
        List<Account> accounts = app.orm().getAccounts();
        List<Account> properAccounts = accounts.stream().filter(a->!a.getLogin().equalsIgnoreCase("Administrator") && !a.getEmail().equals("")).collect(Collectors.toList());
        Assert.assertTrue(properAccounts.size() > 0, "There is no proper account. Please create it.");
        Account account = properAccounts.iterator().next();

        //reset password
        app.navigation().goToLoginPage();
        app.auth().login(new Account(app.properties.getProperty("adminLogin"), app.properties.getProperty("adminPassword")));
        app.account().resetPassword(account.getId());
        Assert.assertTrue(app.account().isSuccessAlertPresent(), "Success message is not present");

        //receive mail
        List<WiserMessage> messages = app.smtpServer().waitForMail(1, 10);
        Assert.assertTrue(messages.size() > 0, "Confirmation mail didn't come to " + account.getEmail());
        List<String> urlList = app.smtpServer().findURL(
                app.smtpServer().readMessage(messages.iterator().next()));
        Assert.assertTrue(urlList.size() > 0, "URL a't found in mail");

        //change password
        Account newAccount = new Account(account.getLogin(), "newPassword1");
        app.account().changePassword(urlList.iterator().next(), newAccount);
        Assert.assertTrue(app.account().isSuccessAlertPresent(), "Success message is not present");

        //login with new password
        app.httpSession().login(newAccount);
        Assert.assertTrue(app.httpSession().isLoggedInAs(newAccount), String.format("Authorization failed: %s (%s)", newAccount.getLogin(), newAccount.getPassword()));
    }

    @AfterMethod
    public void stop() {
        app.smtpServer().stop();
    }
}
