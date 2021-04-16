package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
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

public class ContactsRemovalFromGroupTestsORM extends TestBaseAuth {

    @BeforeMethod()
    public void ensurePreconditions() {
        Contacts contacts = app.orm.getContactsWithGroups();
        Groups groups = app.orm.getGroups();
        if (app.orm.getContacts().size() == 0) {
            app.contact.goToHomePage();
            app.contact.create(new Contact("Text 1", "Text 2"));
            contacts = app.orm.getContactsWithGroups();
        }
        if (groups.size() == 0) {
            app.group.goToGroupPage();
            app.group.create(new Group().setName("Text"));
            groups = app.orm.getGroups();
        }
        Set contactsInGroups = contacts.stream().filter((obj)->obj.getGroups().size() > 0).collect(Collectors.toSet());
        if(contactsInGroups.size() == 0) {
            app.contact.goToHomePage();
            app.contact.addToGroup(contacts.iterator().next(), groups.iterator().next());
        }
    }

    @Test()
    public void testRemovalContactFromGroup() throws InterruptedException {
        Set contactsInGroups = app.orm.getContactsWithGroups()
                .stream().filter((obj)->obj.getGroups().size() > 0)
                .collect(Collectors.toSet());
        Contact contact = (Contact) contactsInGroups.iterator().next();
        Set<Group> beforeGroups = contact.getGroups();
        Group group = beforeGroups.iterator().next();
        group = app.orm.getGroupWithContacts(group);
        Set<Contact> beforeContacts = group.getContacts();

        app.contact.goToHomePage();
        app.contact.removeFromGroup(contact, group);
        beforeGroups.remove(group);
        beforeContacts.remove(contact);
        //verification message text
        assertThat("Message text doesn't match", app.contact.getMessageTextAndReturn(), CoreMatchers.containsString("Users removed."));
        //verification contacts in group
        Thread.sleep(20000);
        Set<Contact> afterContacts = app.orm.getGroupWithContacts(group).getContacts();
        assertThat(String.format("Number of contacts after removal from group with id '%s' differs,", group.getId()), afterContacts.size(), equalTo(beforeContacts.size()));
        assertThat("Contacts after removal from group differs from expected", afterContacts, equalTo(beforeContacts));
        //verification groups for contact
        Set<Group> afterGroups = app.orm.getContactWithGroups(contact).getGroups();
        assertThat(String.format("Number of groups for contact '%s' after removal from group with id '%s' differs,", contact.getFirstname(), group.getId()), afterGroups.size(), equalTo(beforeGroups.size()));
        assertThat("Contacts group differs from expected", afterGroups, equalTo(beforeGroups));
    }
}
