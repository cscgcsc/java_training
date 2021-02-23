package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupRemovalTests extends TestBaseAuth {

    @Test
    public void testGroupRemoval() {
        app.group.goToGroupPage();
        app.group.selectGroup();
        app.group.submitRemoval();
        app.group.returnToGroupPage();
        app.auth.logout();
    }
}
