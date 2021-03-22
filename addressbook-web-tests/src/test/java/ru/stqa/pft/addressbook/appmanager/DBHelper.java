package ru.stqa.pft.addressbook.appmanager;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.invoke.MethodHandle;
import java.sql.*;
import java.util.Arrays;

public class DBHelper {
    public Connection conn = null;

    public DBHelper() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook?user=root&password=");
        } catch (SQLException ex) {
            printException(ex);
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            printException(ex);
        }
    }

    public Groups getGroups() {
        Groups groups = new Groups();
        try {
            Statement stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM group_list ORDER BY group_id DESC LIMIT 10");
            ResultSet rs = stmt.executeQuery("SELECT * FROM group_list");
            while(rs.next()) {
                groups.add(new Group()
                        .setId(rs.getInt("group_id"))
                        .setName(rs.getString("group_name"))
                        .setHeader(rs.getString("group_header"))
                        .setFooter(rs.getString("group_footer"))
                        .setCreated(rs.getString("created")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return groups;
    }

    public int getGroupsCount() {
        int count = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS group_count FROM group_list");
            while(rs.next()) {
                count = rs.getInt("group_count");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }
        return count;
    }

    public Contacts getContacts() {
        Contacts contacts = new Contacts();
        try {
            Statement stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT * FROM addressbook ORDER BY id DESC LIMIT 10");
            ResultSet rs = stmt.executeQuery("SELECT * FROM addressbook");
            while(rs.next()) {
                contacts.add(new Contact()
                        .setId(rs.getInt("id"))
                        .setFirstname(rs.getString("firstname"))
                        .setMiddlename(rs.getString("middlename"))
                        .setLastname(rs.getString("lastname"))
                        .setNotes(rs.getString("notes"))
                        .setEmail(rs.getString("email")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return contacts;
    }

    public int getContactsCount() {
        int count = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS contact_count FROM addressbook");
            while(rs.next()) {
                count = rs.getInt("contact_count");
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }
        return count;
    }

    public Contacts getContactsForAddingToGroup() {
        Contacts contacts = new Contacts();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT addressbook.id, addressbook.firstname, addressbook.lastname" +
                    " FROM addressbook" +
                    " LEFT JOIN address_in_groups" +
                    " ON addressbook.id = address_in_groups.id" +
                    " GROUP BY addressbook.id" +
                    " HAVING COUNT(address_in_groups.group_id) < (SELECT COUNT(*) FROM group_list)" +
                    " OR COUNT(address_in_groups.group_id) = 0");
            while(rs.next()) {
                contacts.add(new Contact()
                        .setId(rs.getInt("id"))
                        .setFirstname(rs.getString("firstname"))
                        .setLastname(rs.getString("lastname")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return contacts;
    }

    public Groups getGroupsForAddingTo(int id) {
        Groups groups = new Groups();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT group_list.group_id, group_list.group_name" +
                    " FROM group_list" +
                    " WHERE group_list.group_id NOT IN" +
                    "   (SELECT address_in_groups.group_id" +
                    "   FROM address_in_groups" +
                    "   WHERE address_in_groups.id = %s)", id));
            while(rs.next()) {
                groups.add(new Group()
                        .setId(rs.getInt("group_id"))
                        .setName(rs.getString("group_name")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return groups;
    }

    public Contacts getContactsInGroup(Group group) {
        Contacts contacts = new Contacts();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT addressbook.id, addressbook.firstname, addressbook.lastname" +
                    " FROM address_in_groups " +
                    " INNER JOIN addressbook " +
                    " ON address_in_groups.id = addressbook.id " +
                    " WHERE address_in_groups.group_id = %s", group.getId()));
            while(rs.next()) {
                contacts.add(new Contact()
                        .setId(rs.getInt("id"))
                        .setFirstname(rs.getString("firstname"))
                        .setLastname(rs.getString("lastname")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return contacts;
    }

    public Groups getContactGroups(Contact contact) {
        Groups groups = new Groups();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT group_list.group_id, group_list.group_name, group_list.group_header, group_list.group_footer" +
                    " FROM address_in_groups " +
                    " INNER JOIN group_list " +
                    " ON address_in_groups.group_id = group_list.group_id " +
                    " WHERE address_in_groups.id = %s", contact.getId()));
            while(rs.next()) {
                groups.add(new Group()
                        .setId(rs.getInt("group_id"))
                        .setName(rs.getString("group_name"))
                        .setHeader(rs.getString("group_header"))
                        .setFooter(rs.getString("group_footer")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return groups;
    }

    public Contacts getContactsInGroups() {
        Contacts contacts = new Contacts();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT addressbook.id, addressbook.firstname, addressbook.lastname" +
                    " FROM address_in_groups" +
                    " INNER JOIN addressbook" +
                    " ON address_in_groups.id = addressbook.id");
            while(rs.next()) {
                contacts.add(new Contact()
                        .setId(rs.getInt("id"))
                        .setFirstname(rs.getString("firstname"))
                        .setLastname(rs.getString("lastname")));
            }
            rs.close();
            stmt.close();
        }
        catch (SQLException ex) {
            printException(ex);
        }

        return contacts;
    }

    private void printException(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
}
