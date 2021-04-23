package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.stqa.pft.mantis.model.Account;
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

    public List<Account> getAccounts() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Account" ).list();
        session.getTransaction().commit();
        session.close();

        return result;
    }
}
