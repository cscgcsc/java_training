package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Group;

public class GroupModificationTests extends TestBaseAuth {

    @Test
    public void testGroupModification() {
        if (app.group.getGroupsList().size() == 0) {
            app.group.CreateGroup(new Group());
        }
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
