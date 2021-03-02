package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBaseAuth {

    @BeforeMethod
    public void ensurePreconditions() {
        app.group.goToGroupPage();
        if (app.group.getCheckboxList().size() == 0) {
            app.group.create(new Group());
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.group.getAll();
        Group modifiedGroup = before.iterator().next();
        Group newGroup = new Group().setName("Text 4").setHeader("Text 5").setFooter("Text 6").setId(modifiedGroup.getId());
        app.group.modify(newGroup);
        //verification
        Groups after = app.group.getAll();
        assertThat("Number of groups after modifying differs", after.size(), equalTo(before.size()));
        assertThat("Group after modifying differs from expected", after, equalTo(before.withoutElement(modifiedGroup).withElement(newGroup)));
        //app.auth.logout();
    }
}
