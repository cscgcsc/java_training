package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;

public class ContactRemovalTests extends TestBaseAuth {

    @Test
    public void testContactRemoval() {
        if (app.contact.getContactsList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.CreateContact(contactNew);
        }
        app.contact.goToHomePage();
        app.contact.selectContact();
        app.contact.submitRemoval();
        app.auth.logout();
    }
}
