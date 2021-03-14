package ru.stqa.pft.addressbook.tests;

import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBaseAuth {

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("src/test/resources/groups.json"));
        String json = "";
        String line = fileReader.readLine();
        while(line != null) {
            json += line;
            line = fileReader.readLine();
        }
        Gson gson = new Gson();
        List<Group> groups = gson.fromJson(json, new TypeToken<List<Group>>(){}.getType()); //List<Group>.class
        return groups.stream().map((group) -> new Object[] {group}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("src/test/resources/groups.xml"));
        String xml = "";
        String line = fileReader.readLine();
        while(line != null) {
            xml += line;
            line = fileReader.readLine();
        }
        XStream xStream = new XStream();
        xStream.processAnnotations(Group.class);
        List<Group> groups = (List<Group>) xStream.fromXML(xml);
        return groups.stream().map((group) -> new Object[] {group}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation(Group newGroup) {
        app.group.goToGroupPage();
        Groups before = app.group.getAll();
        //Group newGroup = new Group().setName("Text 1").setHeader("Text 2").setFooter("Text 3");
        app.group.create(newGroup);
        //verification
        Groups after = app.group.getAll();
        newGroup.setId(after.stream().mapToInt((obj) -> obj.getId()).max().getAsInt());
        assertThat("Number of groups after creation differs", after.size(), equalTo(before.size() + 1));
        assertThat("Group after creation differs from expected", after, equalTo(before.withElement(newGroup)));
        //app.auth.logout();
    }
}
