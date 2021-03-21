package ru.stqa.pft.addressbook.appmanager;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

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
    private void printException(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
}
