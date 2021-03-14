package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBaseAuth {

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("src/test/resources/contacts.json"));
        String json = "";
        String line = fileReader.readLine();
        while(line != null) {
            json += line;
            line = fileReader.readLine();
        }
        Gson gson = new Gson();
        List<Contact> contacts = gson.fromJson(json, new TypeToken<List<Contact>>(){}.getType()); //List<Contact>.class
        return contacts.stream().map((contact) -> new Object[] {contact}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"));
        String xml = "";
        String line = fileReader.readLine();
        while(line != null) {
            xml += line;
            line = fileReader.readLine();
        }
        XStream xStream = new XStream();
        xStream.processAnnotations(Contact.class);
        List<Contact> contacts = (List<Contact>) xStream.fromXML(xml);
        return contacts.stream().map((contact) -> new Object[] {contact}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validContactsFromXml")
    public void testContactCreation(Contact newContact) {
        app.contact.goToHomePage();
        Contacts before = app.contact.getAll();
        //Contact newContact = new Contact("Text 1", "Text 2").setMiddlename("Text 3").setEmail("Text 4").setEmail2("Text 5").setHomePhone("Text 6").setMobilePhone("Text 7").setAddress("Text 8").setBday("5").setBmonth("April").setByear("1990");
        app.contact.create(newContact);
        //verification
        Contacts after = app.contact.getAll();
        newContact.setId(after.stream().mapToInt((obj) -> obj.getId()).max().getAsInt());
        assertThat("Number of contacts after creation differs", after.size(), equalTo(before.size() + 1));
        assertThat("Contact after creation differs from expected", after, equalTo(before.withElement(newContact)));
        //app.auth.logout();
    }
}
