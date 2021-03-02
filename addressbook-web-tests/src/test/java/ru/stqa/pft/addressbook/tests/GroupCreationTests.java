package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBaseAuth {

    @Test
    public void testGroupCreation() {
        app.group.goToGroupPage();
        Groups before = app.group.getAll();
        Group newGroup = new Group().setName("Text 1").setHeader("Text 2").setFooter("Text 3");
        app.group.create(newGroup);
        //verification
        Groups after = app.group.getAll();
        newGroup.setId(after.stream().mapToInt((obj) -> obj.getId()).max().getAsInt());
        assertThat("Number of groups after creation differs", after.size(), equalTo(before.size() + 1));
        assertThat("Group after creation differs from expected", after, equalTo(before.withElement(newGroup)));
        //app.auth.logout();
    }
}
