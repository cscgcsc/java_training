package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBaseAuth {

    @Test
    public void testGroupModification() {

        //precondition
        app.group.goToGroupPage();
        if (app.group.getCheckboxList().size() == 0) {
            app.group.CreateGroup(new Group());
        }

        int index = 1;
        List<Group> before = app.group.getGroupsList();
        Group newGroup = new Group("Text 4", "Text 5", "Text 6");
        newGroup.setId(before.get(index).getId());

        //modification
        app.group.selectGroup(index);
        app.group.initGroupModification();
        app.group.fillGroupForm(newGroup);
        app.group.submitModification();
        app.group.returnToGroupPage();

        //verification groups count
        List<Group> after = app.group.getGroupsList();
        Assert.assertEquals(after.size(), before.size(), "Number of groups after modifying differs,");

        //verification groups
        Comparator<? super Group> byId = (object1, object2) -> Integer.compare(object1.getId(), object2.getId());
        before.remove(index);
        before.add(newGroup);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before, "Group after modifying differs from expected");

        //logout
        app.auth.logout();
    }
}
