package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactRemovalTests extends TestBaseAuth {

    @Test
    public void testContactRemoval() {
        app.contact.goToHomePage();
        app.contact.selectContact();
        app.contact.submitRemoval();
        app.auth.logout();
    }
}
