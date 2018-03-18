package ru.otus.java.hw12.data;

import ru.otus.java.hw10.data.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountDataSet extends DataSet {

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    public AccountDataSet() {
    }

    public AccountDataSet(String login, String password) {
        this.setId(-1);
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountDataSet{" + "login='" + login + '\'' + ", password='" + password + '\'' + '}';
    }
}
