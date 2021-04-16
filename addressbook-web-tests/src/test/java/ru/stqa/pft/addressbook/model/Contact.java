package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class Contact implements Comparable {

    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id;

    @Expose
    @Column(name = "firstname")
    private String firstname;

    @Expose
    @Column(name = "lastname")
    private String lastname;

    @Expose
    @Column(name = "middlename")
    private String middlename;

    @Column(name = "address")
    @Type(type = "text")
    private String address;

    @Column(name = "email")
    @Type(type = "text")
    private String email;

    @Column(name = "email2")
    @Type(type = "text")
    private String email2;

    @Column(name = "email3")
    @Type(type = "text")
    private String email3;

    @Transient
    private String allEmails;

    @Column(name = "home")
    @Type(type = "text")
    private String homePhone;

    @Column(name = "mobile")
    @Type(type = "text")
    private String mobilePhone;

    @Column(name = "work")
    @Type(type = "text")
    private String workPhone;

    @Transient
    private String allPhones;

    @Column(name = "bday")
    @Type(type = "byte")
    private Byte bday;

    @Column(name = "bmonth")
    @Type(type = "text")
    private String bmonth;

    @Column(name = "byear")
    @Type(type = "text")
    private String byear;

    @Transient
    private String filePath;

    @Column(name = "notes")
    @Type(type = "text")
    private String notes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    public Contact() {
    }

    public Contact(String firstname, String lastname) {
        this.setFirstname(firstname);
        this.setLastname(lastname);
    }

    public String getFirstname() {
        return firstname;
    }

    public Contact setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public Contact setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getMiddlename() {
        return middlename;
    }

    public Contact setMiddlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Contact setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail2() {
        return email2;
    }

    public Contact setEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public Contact setHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Contact setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public String getBday() {
        if(bday != null) return Byte.toString(bday);
        return "0";
    }

    public Contact setBday(String bday) {
        this.bday = Byte.parseByte(bday);
        return this;
    }

    public String getBmonth() {
        return bmonth;
    }

    public Contact setBmonth(String bmonth) {
        this.bmonth = bmonth;
        return this;
    }

    public String getByear() {
        return byear;
    }

    public Contact setByear(String byear) {
        this.byear = byear;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Contact setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getEmail3() {
        return email3;
    }

    public Contact setEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public Contact setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public Contact setAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public Contact setAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public int getId() {
        return id;
    }

    public Contact setId(int id) {
        this.id = id;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Contact setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return getId() == contact.getId() && Objects.equals(firstname, contact.firstname) && Objects.equals(lastname, contact.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), firstname, lastname);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object obj) {
        if (obj == null) return 1;
        Contact otherContact = (Contact) obj;
        return Integer.compare(this.getId(), otherContact.getId());
    }
}

//https://docs.jboss.org/hibernate/orm/3.5/api/org/hibernate/type/package-summary.html