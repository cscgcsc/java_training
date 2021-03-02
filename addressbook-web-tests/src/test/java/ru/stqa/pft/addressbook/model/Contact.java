package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class Contact implements Comparable {
    private int id;
    private String firstname;
    private String lastname;
    private String middlename;
    private String address;
    private String email;
    private String email2;
    private String homePhone;
    private String mobilePhone;
    private String bday;
    private String bmonth;
    private String byear;
    private String file;

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
        return bday;
    }

    public Contact setBday(String bday) {
        this.bday = bday;
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

    public String getFile() {
        return file;
    }

    public Contact setFile(String file) {
        this.file = file;
        return this;
    }

    public int getId() {
        return id;
    }

    public Contact setId(int id) {
        this.id = id;
        return this;
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

