package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Contacts extends ForwardingSet<Contact> {
    private Set<Contact> delegate;

    public Contacts() {
        this.delegate = new HashSet<>();
    }

    public Contacts(Contacts contacts) {
        this.delegate = new HashSet<>(contacts.delegate);
    }

    public Contacts(List contacts) {
        this.delegate = new HashSet<>(contacts);
    }

    @Override
    protected Set<Contact> delegate() {
        return delegate;
    }

    public Contacts withElement(Contact contact) {
        Contacts contacts = new Contacts(this);
        contacts.add(contact);
        return contacts;
    }

    public Contacts withoutElement(Contact contact) {
        Contacts contacts = new Contacts(this);
        contacts.remove(contact);
        return contacts;
    }
}
