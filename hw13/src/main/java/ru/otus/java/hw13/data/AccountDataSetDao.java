package ru.otus.java.hw13.data;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;

public class AccountDataSetDao {

    private Session session;

    public AccountDataSetDao setSession(Session session) {
        this.session = session;
        return this;
    }

    public AccountDataSet loadByLogin(String login) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<AccountDataSet> criteria = builder.createQuery(AccountDataSet.class);
        criteria.where(builder.equal(criteria.from(AccountDataSet.class).get("login"), login));
        return session.createQuery(criteria)
            .uniqueResult();
    }

    public Serializable save(AccountDataSet account) {
        return session.save(account);
    }
}
