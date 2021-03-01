package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import java.util.List;

public class ContactCreationTests extends TestBaseAuth {

    @Test
    public void testContactCreation() {

        //precondition
        app.contact.goToHomePage();
        List<Contact> before = app.contact.getContactsList();
        Contact newContact = new Contact("Text 1", "Text 2");
        newContact.setMiddlename("Text 3");
        newContact.setEmail("Text 4");
        newContact.setEmail2("Text 5");
        newContact.setHomePhone("Text 6");
        newContact.setMobilePhone("Text 7");
        newContact.setAddress("Text 8");
        newContact.setBday("5");
        newContact.setBmonth("April");
        newContact.setByear("1990");

        //creation
        app.contact.goToNewContactPage();
        app.contact.fillContactForm(newContact);
        app.contact.submitCreation();
        app.contact.returnToHomePage();

        //verification contacts count
        List<Contact> after = app.contact.getContactsList();
        Assert.assertEquals(after.size(), before.size() + 1, "Number of contacts after creation differs,");

        //verification contacts
        after.sort(Contact::compareTo);
        newContact.setId(after.get(after.size()-1).getId());
        before.add(newContact);
        before.sort(Contact::compareTo);
        Assert.assertEquals(after, before, "Contact after creation differs from expected");

        //logout
        app.auth.logout();
    }
}
