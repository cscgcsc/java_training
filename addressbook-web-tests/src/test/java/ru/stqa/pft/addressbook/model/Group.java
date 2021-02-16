package ru.stqa.pft.addressbook.model;

public class Group {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}
