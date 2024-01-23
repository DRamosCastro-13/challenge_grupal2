package com.mindhub.wireit.models;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addres, phone, country, province, city;

    private int zipCode;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Address() {
    }

    public Address(String addres, String phone, String country, String province, String city, int zipCode, Client client) {
        this.addres = addres;
        this.phone = phone;
        this.country = country;
        this.province = province;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
