package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;

public class GroupTests extends TestBaseAuth {

    @Test
    public void testGroupCreation() {
        Group group = new Group("Text 1", "Text 2", "Text 3");

        app.group.goToGroupPage();
        app.group.initGroupCreation();
        app.group.fillGroupForm(group);
        app.group.submitCreation();
        app.group.returnToGroupPage();
        app.auth.logout();
    }

    @Test
    public void testGroupRemoval() {
        app.group.goToGroupPage();
        app.group.selectGroup(1);
        app.group.submitRemoval();
        app.group.returnToGroupPage();
        app.auth.logout();
    }

    @Test
    public void testGroupModification() {
        Group group = new Group("Text 4", "Text 5", "Text 6");

        app.group.goToGroupPage();
        app.group.selectGroup(1);
        app.group.initGroupModification();
        app.group.fillGroupForm(group);
        app.group.submitModification();
        app.group.returnToGroupPage();
        app.auth.logout();
    }
}
