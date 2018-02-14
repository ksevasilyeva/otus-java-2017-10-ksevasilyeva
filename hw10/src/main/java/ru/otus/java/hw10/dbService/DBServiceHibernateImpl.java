package ru.otus.java.hw10.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.otus.java.hw10.base.DBService;
import ru.otus.java.hw10.data.UserDataSet;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {

    private SessionFactory sessionFactory;

    public DBServiceHibernateImpl() {
        sessionFactory = createSessionFactory();
    }

    @Override
    public UserDataSet load(long id) throws SQLException {
        return runInSession(session -> session.get(UserDataSet.class, id));
    }

    @Override
    public List<UserDataSet> load(Class<UserDataSet> clazz) {
        return runInSession(session -> session.createQuery("select o from "+clazz.getName()+" o")
                .getResultList());
    }

    @Override
    public void save(UserDataSet user) throws SQLException {
        runInSession(session -> session.save(user));
    }

    private static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private <R> R runInSession(Function<Session, R> function) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e){
            if (transaction != null) transaction.rollback();
        }
        return null;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
