package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import java.util.List;

public class ContactModificationTests extends TestBaseAuth {

    @Test
    public void testContactModification() {

        //precondition
        app.contact.goToHomePage();
        if (app.contact.getCheckboxList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.CreateContact(contactNew);
        }

        int index = 0;
        List<Contact> before = app.contact.getContactsList();
        Contact newContact = new Contact("Text 4", "Text 5");
        newContact.setId(before.get(index).getId());

        //modification
        app.contact.initModification(index);
        app.contact.fillContactForm(newContact);
        app.contact.submitModification();
        app.contact.returnToHomePage();

        //verification contacts count
        List<Contact> after = app.contact.getContactsList();
        Assert.assertEquals(after.size(), before.size(), "Number of contacts after modifying differs,");

        //verification contacts
        before.remove(index);
        before.add(newContact);
        before.sort(Contact::compareTo);
        after.sort(Contact::compareTo);
        Assert.assertEquals(after, before, "Contact after modifying differs from expected");

        //logout
        app.auth.logout();
    }
}
