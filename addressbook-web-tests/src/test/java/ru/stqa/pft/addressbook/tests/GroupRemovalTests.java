package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import java.util.Comparator;
import java.util.List;

public class GroupRemovalTests extends TestBaseAuth {

    @Test
    public void testGroupRemoval() {

        //precondition
        app.group.goToGroupPage();
        if (app.group.getCheckboxList().size() == 0) {
            app.group.CreateGroup(new Group());
        }
        List<Group> before = app.group.getGroupsList();
        int index = 0;

        //removal
        app.group.selectGroup(index);
        app.group.submitRemoval();
        app.group.returnToGroupPage();

        //verification groups count
        List<Group> after = app.group.getGroupsList();
        Assert.assertEquals(after.size(), before.size() - 1, "Number of groups after removal differs,");

        //verification groups
        Comparator<? super Group> byId = (object1, object2) -> Integer.compare(object1.getId(), object2.getId());
        before.remove(index);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before, "Group after removal differs from expected");

        //logout
        app.auth.logout();
    }
}
