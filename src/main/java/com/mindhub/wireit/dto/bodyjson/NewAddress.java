package com.mindhub.wireit.dto.bodyjson;

public class NewAddress {
    private String address, phone, country, province, city;
    private int zipCode;

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }
}
