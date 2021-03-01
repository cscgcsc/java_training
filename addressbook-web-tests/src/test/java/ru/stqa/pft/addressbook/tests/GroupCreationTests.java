package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBaseAuth {

    @Test
    public void testGroupCreation() {

        //precondition
        app.group.goToGroupPage();
        List<Group> before = app.group.getGroupsList();
        Group newGroup = new Group("Text 1", "Text 2", "Text 3");

        //creation
        app.group.initGroupCreation();
        app.group.fillGroupForm(newGroup);
        app.group.submitCreation();
        app.group.returnToGroupPage();

        //verification groups count
        List<Group> after = app.group.getGroupsList();
        Assert.assertEquals(after.size(), before.size() + 1, "Number of groups after creation differs,");

        //verification groups
        Comparator<? super Group> byId = (object1, object2) -> Integer.compare(object1.getId(), object2.getId());
        after.sort(byId);
        newGroup.setId(after.get(after.size()-1).getId());
        before.add(newGroup);
        before.sort(byId);
        Assert.assertEquals(after, before, "Group after creation differs from expected");

        //logout
        app.auth.logout();
    }
}
