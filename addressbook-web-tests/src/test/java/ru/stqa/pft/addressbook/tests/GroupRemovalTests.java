package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;

public class GroupRemovalTests extends TestBaseAuth {

    @Test
    public void testGroupRemoval() {
        if (app.group.getGroupsList().size() == 0) {
            app.group.CreateGroup(new Group());
        }
        app.group.goToGroupPage();
        app.group.selectGroup();
        app.group.submitRemoval();
        app.group.returnToGroupPage();
        app.auth.logout();
    }
}
