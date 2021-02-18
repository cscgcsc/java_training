package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;

public class ContactTests extends TestBaseAuth {

    @Test
    public void testContactCreation() {
        Contact contact = new Contact("Text 1", "Text 2");
        contact.setMiddlename("Text 3");
        contact.setEmail("Text 4");
        contact.setEmail2("Text 5");
        contact.setHomePhone("Text 6");
        contact.setMobilePhone("Text 7");
        contact.setAddress("Text 8");
        contact.setBday("5");
        contact.setBmonth("April");
        contact.setByear("1990");

        app.contact.goToNewContactPage();
        app.contact.fillContactForm(contact);
        app.contact.submitCreation();
        app.contact.returnToHomePage();
        app.auth.logout();
    }

    @Test
    public void testContactRemoval() {
        app.contact.goToHomePage();
        app.contact.selectContact(1);
        app.contact.submitRemoval();
        app.auth.logout();
    }

    @Test
    public void testContactModification() {
        Contact contact = new Contact("Text 4", "Text 5");

        app.contact.goToHomePage();
        app.contact.initModification(1);
        app.contact.fillContactForm(contact);
        app.contact.submitModification();
        app.contact.returnToHomePage();
        app.auth.logout();
    }
}
