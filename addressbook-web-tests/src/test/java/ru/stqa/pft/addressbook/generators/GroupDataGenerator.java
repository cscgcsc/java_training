package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.Group;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {
    @Parameter(names="-c", description = "Data count", required = true)
    public int count;
    @Parameter(names="-p", description = "File path", required = true)
    public String filePath;
    @Parameter(names="-f", description = "File format", required = true)
    public String format;

    public static void main(String args[]) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
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
        List<Group> groups = generateGroups();
        if(format.equals("xml")) {
            saveAsXml(groups);
        } else if(format.equals("json")) {
            saveAsJson(groups);
        }
        else {
            System.out.println("Unrecognized format: " + format);
        }
    }

    private List<Group> generateGroups() {
       List<Group> groups = new ArrayList<>();
       for(int i =0; i<count; i++) {
           groups.add(new Group().setName(String.format("Name %s", i)).setHeader(String.format("Header %s", i)).setFooter(String.format("Footer %s", i)));
       }
       return groups;
    }

    private void saveAsXml(List<Group> groups) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(Group.class);
        String xml = xStream.toXML(groups);
        Writer writer = new FileWriter(filePath);
        writer.write(xml);
        writer.close();
    }

    private void saveAsJson(List<Group> groups) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        Writer writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();
    }
}
