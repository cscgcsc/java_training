package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;

public class GroupCreationTests extends TestBaseAuth {

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
}
