package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupRemovalTests extends TestBaseAuth {

    @BeforeMethod
    public void ensurePreconditions() {
        app.group.goToGroupPage();
        if (app.db.getGroupsCount() == 0) {
            app.group.create(new Group());
        }
    }

    @Test
    public void testGroupRemoval() {
        Groups before = app.group.getAll();
        Group removalGroup = before.iterator().next();
        app.group.remove(removalGroup);
        //verification groups count
        Groups after = app.group.getAll();
        assertThat("Number of groups after removal differs,", after.size(), equalTo(before.size() - 1));
        assertThat("Group after removal differs from expected", after, equalTo(before.withoutElement(removalGroup)));
    }

    @Test
    public void testGroupRemovalDB() {
        Groups before = app.db.getGroups();
        Integer beforeCount = app.db.getGroupsCount();
        Group removalGroup = before.iterator().next();
        app.group.remove(removalGroup);
        //verification groups count
        Groups after = app.db.getGroups();
        assertThat("Number of groups after removal differs,", app.db.getGroupsCount(), equalTo(beforeCount - 1));
        assertThat("Group after removal differs from expected", after, equalTo(before.withoutElement(removalGroup)));
    }
}
