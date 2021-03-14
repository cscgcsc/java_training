package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.Contact;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names="-c", description = "Data count", required = true)
    public int count;
    @Parameter(names="-p", description = "File path", required = true)
    public String filePath;
    @Parameter(names="-f", description = "File format", required = true)
    public String format;

    public static void main(String args[]) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jcommander = new JCommander(generator);
        try {
            jcommander.parse(args);
        } catch (ParameterException ex) {
           jcommander.usage();
           return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<Contact> contacts = generateContacts();
        if(format.equals("xml")) {
            saveAsXml(contacts);
        } else if(format.equals("json")) {
            saveAsJson(contacts);
        }
        else {
            System.out.println("Unrecognized format: " + format);
        }
    }

    private List<Contact> generateContacts() {
       List<Contact> contacts = new ArrayList<>();
       for(int i =0; i<count; i++) {
           contacts.add(new Contact().setFirstname(String.format("Firstname %s", i)).setLastname(String.format("Lastname %s", i)).setMiddlename(String.format("Middlename %s", i)));
       }
       return contacts;
    }

    private void saveAsXml(List<Contact> contacts) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(Contact.class);
        String xml = xStream.toXML(contacts);
        Writer writer = new FileWriter(filePath);
        writer.write(xml);
        writer.close();
    }

    private void saveAsJson(List<Contact> contacts) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();
    }
}
