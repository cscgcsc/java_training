package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;

public class GroupModificationTests extends TestBaseAuth {

    @Test
    public void testGroupModification() {
        Group group = new Group("Text 4", "Text 5", "Text 6");

        app.group.goToGroupPage();
        app.group.selectGroup();
        app.group.initGroupModification();
        app.group.fillGroupForm(group);
        app.group.submitModification();
        app.group.returnToGroupPage();
        app.auth.logout();
    }
}
