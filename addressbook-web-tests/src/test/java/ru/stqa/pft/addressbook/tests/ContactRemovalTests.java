package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import java.util.List;

public class ContactRemovalTests extends TestBaseAuth {

    @Test
    public void testContactRemoval() {

        //precondition
        app.contact.goToHomePage();
        if (app.contact.getCheckboxList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.CreateContact(contactNew);
        }
        List<Contact> before = app.contact.getContactsList();
        int index = 0;

        //removal
        app.contact.selectContact(index);
        app.contact.submitRemoval();

        //verification message text
        Assert.assertEquals(app.contact.getMessageText(), "Record successful deleted", "Message text doesn't match");
        app.contact.waitUpdatingPage();

        //verification contacts count
        List<Contact> after = app.contact.getContactsList();
        Assert.assertEquals(after.size(), before.size() - 1, "Number of contacts after removal differs,");

        //verification contacts
        before.remove(index);
        before.sort(Contact::compareTo);
        after.sort(Contact::compareTo);
        Assert.assertEquals(after, before, "Contact after removal differs from expected");

        //logout
        app.auth.logout();
    }
}
