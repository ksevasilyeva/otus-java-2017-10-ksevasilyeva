package ru.otus.java.hw10.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressDataSet extends DataSet {

    @Column(name = "street")
    private String street;

    @Column(name = "houseNum")
    private int houseNum;

    @Column(name = "flatNum")
    private int flatNum;

    public AddressDataSet() {
    }

    public AddressDataSet(String street, int houseNum, int flatNum) {
        this.street = street;
        this.houseNum = houseNum;
        this.flatNum = flatNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }

    public int getFlatNum() {
        return flatNum;
    }

    public void setFlatNum(int flatNum) {
        this.flatNum = flatNum;
    }

    @Override
    public String toString() {
        return "Address{" + "street='" + street + '\'' + ", houseNum='" + houseNum + '\'' + ", flatNum='" + flatNum + '\'' + '}';
    }
}
