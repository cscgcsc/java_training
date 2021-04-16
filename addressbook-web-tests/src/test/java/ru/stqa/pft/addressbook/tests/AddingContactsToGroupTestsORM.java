package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddingContactsToGroupTestsORM extends TestBaseAuth {

    @BeforeMethod()
    public void ensurePreconditions() {
        app.contact.goToHomePage();
        Contacts contacts = app.orm.getContactsWithGroups();
        Groups groups = app.orm.getGroups();
        Set contactsForAddingToGroup = contacts.stream().filter((obj)->obj.getGroups().size() < groups.size() || obj.getGroups().size() == 0).collect(Collectors.toSet());
        if (app.orm.getContacts().size() == 0 || contactsForAddingToGroup.size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.create(contactNew);
        }
        app.group.goToGroupPage();
        if (groups.size() == 0) {
            app.group.create(new Group().setName("Text"));
        }
    }

    @Test
    public void testAddingContactToGroup(){
        Contacts contacts = app.orm.getContacts();
        Groups groups = app.orm.getGroupsWithContacts();
        Set<Contact> availableContacts = contacts.stream().filter((obj)->obj.getGroups().size() < groups.size()).collect(Collectors.toSet());
        Contact contact = availableContacts.iterator().next();
        Set<Group> availableGroups = groups.stream().filter((obj)->!contact.getGroups().contains(obj)).collect(Collectors.toSet());
        Group group = availableGroups.iterator().next();

        app.contact.goToHomePage();
        Set<Contact> beforeContacts = group.getContacts();
        Set<Group> beforeGroups = contact.getGroups();
        app.contact.addToGroup(contact, group);
        //getting objects from DB
        Group updGroup = app.orm.getGroupWithContacts(group);
        Contact updContact = app.orm.getContactWithGroups(contact);
        beforeContacts.add(contact);
        beforeGroups.add(group);
        //verification contacts in group
        Set<Contact> afterContacts = updGroup.getContacts();
        assertThat(String.format("Number of contacts after adding to group '%s' differs,", updGroup.getId()), afterContacts.size(), equalTo(beforeContacts.size()));
        assertThat("Contacts after adding to group differs from expected", afterContacts, equalTo(beforeContacts));
        //verification groups for contact
        Set<Group> afterGroups = updContact.getGroups();
        assertThat(String.format("Number of groups for contact '%s' after adding to group '%s' differs,", updContact.getFirstname(), updGroup.getId()), afterGroups.size(), equalTo(beforeGroups.size()));
        assertThat("Contacts group differs from expected", afterGroups, equalTo(beforeGroups));
    }
}

