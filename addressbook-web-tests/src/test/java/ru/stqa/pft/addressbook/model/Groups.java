package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<Group> {
    private Set<Group> delegate;

    public Groups() {
        this.delegate = new HashSet<>();
    }

    public Groups(Groups groups) {
        this.delegate = new HashSet<>(groups.delegate);
    }

    @Override
    protected Set<Group> delegate() {
        return delegate;
    }

    public Groups withElement(Group group) {
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups withoutElement(Group group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }
}
