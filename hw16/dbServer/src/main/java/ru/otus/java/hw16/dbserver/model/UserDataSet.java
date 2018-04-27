package ru.otus.java.hw16.dbserver.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

//    @OneToOne(cascade = CascadeType.ALL)
//    private AddressDataSet address;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<PhoneDataSet> phones = new HashSet<>();

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.setId(-1);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User: " + "name='" + name + '\'' + ", age=" + age + ", address=" + "" + ", phones=" + "" + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public AddressDataSet getAddress() {
//        return address;
//    }
//
//    public void setAddress(AddressDataSet address) {
//        this.address = address;
//    }
//
//    public Set<PhoneDataSet> getPhones() {
//        return phones;
//    }
//
//    public void setPhones(Set<PhoneDataSet> phones) {
//        this.phones = phones;
//    }
//
//    public void addPhone(PhoneDataSet phone){
//        phones.add(phone);
//        phone.setUser(this);
//    }
}
