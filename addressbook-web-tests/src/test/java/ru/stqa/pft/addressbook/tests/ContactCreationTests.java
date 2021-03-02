package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBaseAuth {

    @Test
    public void testContactCreation() {
        app.contact.goToHomePage();
        Contacts before = app.contact.getAll();
        Contact newContact = new Contact("Text 1", "Text 2").setMiddlename("Text 3").setEmail("Text 4").setEmail2("Text 5").setHomePhone("Text 6").setMobilePhone("Text 7").setAddress("Text 8").setBday("5").setBmonth("April").setByear("1990");
        app.contact.create(newContact);
        //verification
        Contacts after = app.contact.getAll();
        newContact.setId(after.stream().mapToInt((obj) -> obj.getId()).max().getAsInt());
        assertThat("Number of contacts after creation differs", after.size(), equalTo(before.size() + 1));
        assertThat("Contact after creation differs from expected", after, equalTo(before.withElement(newContact)));
        //app.auth.logout();
    }
}
