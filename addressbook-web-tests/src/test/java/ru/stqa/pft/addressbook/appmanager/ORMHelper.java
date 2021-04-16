package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ORMHelper {
    public Session session;
    public SessionFactory sessionFactory;

    public ORMHelper() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            System.out.println(e.getMessage());
        }
    }

    public Groups getGroups() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Group" ).list();
        session.getTransaction().commit();
        session.close();

        return new Groups(result);
    }

    public Groups getGroupsWithContacts() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Group gr join fetch gr.contacts" ).list();
        session.getTransaction().commit();
        session.close();

        return new Groups(result);
    }

    public Group getGroupWithContacts(Group group) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Group gr join fetch gr.contacts where gr.id = :id");
        query.setParameter("id", group.getId());
        Group updGroup = (Group) query.list().iterator().next();
        session.getTransaction().commit();
        session.close();

        return updGroup;
    }


    public Groups getGroupsWithContacts(Set<Group> groups) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Group gr join fetch gr.contacts where gr.id in (:ids)");
        query.setParameterList("ids", groups.stream().map((obj)->obj.getId()).collect(Collectors.toList()).toArray());
        List result = query.list();
        session.getTransaction().commit();
        session.close();

        return new Groups(result);
    }

    public Contacts getContacts() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Contact").list();
        session.getTransaction().commit();
        session.close();

        return new Contacts(result);
    }

    public Contacts getContactsWithGroups() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Contact ct join fetch ct.groups").list();
        session.getTransaction().commit();
        session.close();

        return new Contacts(result);
    }

    public Contact getContactWithGroups(Contact contact) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Contact ct join fetch ct.groups where ct.id = :id");
        query.setParameter("id", contact.getId());
        Contact updContact = (Contact) query.list().iterator().next();
        session.getTransaction().commit();
        session.close();

        return updContact;
    }
}
