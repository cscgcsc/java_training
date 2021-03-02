package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemovalTests extends TestBaseAuth {

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact.goToHomePage();
        if (app.contact.getCheckboxList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.create(contactNew);
        }
    }

    @Test
    public void testContactRemoval() {
        Contacts before = app.contact.getAll();
        Contact removalContact = before.iterator().next();
        app.contact.remove(removalContact);
        //verification message text
        assertThat("Message text doesn't match", app.contact.getMessageText(), equalTo("Record successful deleted"));
        //verification contacts
        Contacts after = app.contact.getAll();
        assertThat("Number of contacts after removal differs,", after.size(), equalTo(before.size() - 1));
        assertThat("Contact after removal differs from expected", after, equalTo(before.withoutElement(removalContact)));
        //app.auth.logout();
    }
}
