package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInformationTests extends TestBaseAuth {

    @BeforeMethod
    public void ensurePreconditions() {
        app.contact.goToHomePage();
        if (app.contact.getCheckboxList().size() == 0) {
            Contact contactNew = new Contact("Text 1", "Text 2");
            app.contact.create(contactNew);
        }
    }

    @Test
    public void testInformationFromTableAndForm() {
        app.contact.goToHomePage();
        Contact contactFromTable = app.contact.getAll().iterator().next();
        app.contact.initModification(contactFromTable);
        Contact contactFromForm = app.contact.getInformationFromForm();
        String allPhones = Stream.of(contactFromForm.getHomePhone(), contactFromForm.getMobilePhone(), contactFromForm.getWorkPhone())
                .filter((str) -> !isNullOrEmpty(str))
                .map(ContactInformationTests::cleanUpPhone)
                .collect(Collectors.joining("\n"));

        String allEmails = Stream.of(contactFromForm.getEmail(), contactFromForm.getEmail2(), contactFromForm.getEmail3())
                .filter((str) -> !isNullOrEmpty(str))
                .map(ContactInformationTests::cleanUpEmail)
                .collect(Collectors.joining("\n"));
        //verification
        assertThat("Phones in form and table don't match", allPhones, equalTo(contactFromTable.getAllPhones()));
        assertThat("Address in form and table don't match", cleanUpAddress(contactFromForm.getAddress()), equalTo(contactFromTable.getAddress()));
        assertThat("Emails in form and table don't match", allEmails, equalTo(contactFromTable.getAllEmails()));
    }

    public static boolean isNullOrEmpty(String text) {
        return (text == null || text.replaceAll("[\s\n]*", "").equals(""));
    }

    public static String cleanUpPhone(String text){
        return text.replaceAll("[-()\s.]*", "");
    }

    public static String cleanUpEmail(String text){
        return text.replaceAll("\s{2,}", "\s")
                .replaceAll("^\s*|\s*$", "");
    }

    public String cleanUpAddress(String text){
        return text.replaceAll("\s{2,}", "\s")
                .replaceAll("\s*\n+\s*|\n{2,}", "\n")
                .replaceAll("^[\n\s]|[\n\s]$", "");
    }
}