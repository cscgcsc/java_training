package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("group")
@Entity
@Table(name = "group_list")
public class Group {

    @XStreamOmitField
    @Id
    @Column(name = "group_id")
    private int id;

    @Expose
    @Column(name = "group_name")
    private String name;

    @Expose
    @Column(name = "group_header")
    @Type(type = "text")
    private String header;

    @Expose
    @Column(name = "group_footer")
    @Type(type = "text")
    private String footer;

    @Column(name = "created")
    @Type(type = "timestamp")
    private Date created;

    @ManyToMany(mappedBy = "groups")
    private Set<Contact> contacts = new HashSet<>();

    public Group() {
    }

    public Group(String name, String header, String footer) {
        this.setName(name);
        this.setHeader(header);
        this.setFooter(footer);
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    public String getHeader() {
        return header;
    }

    public Group setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getFooter() {
        return footer;
    }

    public Group setFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public int getId() {
        return id;
    }

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Group setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
