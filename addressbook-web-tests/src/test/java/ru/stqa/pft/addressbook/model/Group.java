package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private String header;
    private String footer;

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
