package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddingContactsToGroupTests extends TestBaseAuth {

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact.goToHomePage();
        if (app.db.getContactsCount() == 0 || app.db.getContactsForAddingToGroup().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.create(contactNew);
        }
        app.group.goToGroupPage();
        if (app.db.getGroupsCount() == 0) {
            app.group.create(new Group());
        }
    }

    @Test
    public void testAddingContactToGroup() {
        app.contact.goToHomePage();
        Contacts availableContacts = app.db.getContactsForAddingToGroup();
        Contact contact = availableContacts.iterator().next();
        Groups availableGroups = app.db.getGroupsForAddingTo(contact.getId());
        Group group = availableGroups.iterator().next();
        Contacts beforeContacts = app.db.getContactsInGroup(group);
        Groups beforeGroups = app.db.getContactGroups(contact);
        app.contact.addToGroup(contact, group);
        //verification contacts in group
        Contacts afterContacts = app.db.getContactsInGroup(group);
        assertThat(String.format("Number of contacts after adding to group '%s' differs,", group.getId()), afterContacts.size(), equalTo(beforeContacts.size() + 1));
        assertThat("Contacts after adding to group differs from expected", afterContacts, equalTo(beforeContacts.withElement(contact)));
        //verification groups for contact
        Groups afterGroups = app.db.getContactGroups(contact);
        assertThat(String.format("Number of groups for contact '%s' after adding to group '%s' differs,", contact.getFirstname(), group.getId()), afterGroups.size(), equalTo(beforeGroups.size() + 1));
        assertThat("Contacts group differs from expected", afterGroups, equalTo(beforeGroups.withElement(group)));
    }
}
