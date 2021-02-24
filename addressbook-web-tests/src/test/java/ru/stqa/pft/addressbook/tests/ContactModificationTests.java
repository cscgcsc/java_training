package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;

public class ContactModificationTests extends TestBaseAuth {

    @Test
    public void testContactModification() {
        if (app.contact.getContactsList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.CreateContact(contactNew);
        }
        Contact contactEdited = new Contact("Text 4", "Text 5");
        app.contact.goToHomePage();
        app.contact.initModification();
        app.contact.fillContactForm(contactEdited);
        app.contact.submitModification();
        app.contact.returnToHomePage();
        app.auth.logout();
    }
}
