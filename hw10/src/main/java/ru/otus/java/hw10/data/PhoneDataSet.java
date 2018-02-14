package ru.otus.java.hw10.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {

    @Column(name = "phoneNumber")
    private String phoneNum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Phone{" + "Phone Number='" + phoneNum + '\'' + '}';
    }
}
