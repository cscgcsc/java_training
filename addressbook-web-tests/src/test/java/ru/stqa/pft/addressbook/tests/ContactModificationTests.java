package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;

public class ContactModificationTests extends TestBaseAuth {

    @Test
    public void testContactModification() {
        Contact contact = new Contact("Text 4", "Text 5");

        app.contact.goToHomePage();
        app.contact.initModification();
        app.contact.fillContactForm(contact);
        app.contact.submitModification();
        app.contact.returnToHomePage();
        app.auth.logout();
    }
}
