package ru.stqa.pft.addressbook.model;

import java.util.Comparator;

public class ContactNameComparator implements Comparator<Contact> {

    public int compare(Contact obj1, Contact obj2) {
        int result = obj1.getFirstname().compareTo(obj2.getFirstname());
        if (result != 0)
            return result;
        return obj1.getLastname().compareTo(obj2.getLastname());
    }
}
