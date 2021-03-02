package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBaseAuth {

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact.goToHomePage();
        if (app.contact.getCheckboxList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.create(contactNew);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact.getAll();
        Contact modifiedContact = before.iterator().next();
        Contact newContact = new Contact("Text 4", "Text 5").setId(modifiedContact.getId());
        app.contact.modify(newContact);
        //verification
        Contacts after = app.contact.getAll();
        assertThat("Number of contacts after modifying differs", after.size(), equalTo(before.size()));
        assertThat("Contact after modifying differs from expected", after, equalTo(before.withoutElement(modifiedContact).withElement(newContact)));
        //app.auth.logout();
    }


}
