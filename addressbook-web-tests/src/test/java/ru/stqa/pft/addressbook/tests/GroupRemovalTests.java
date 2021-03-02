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
        if (app.group.getCheckboxList().size() == 0) {
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
        //app.auth.logout();
    }
}
