package com.mindhub.wireit.dto;

import com.mindhub.wireit.models.Address;
import com.mindhub.wireit.models.Client;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AddressDTO {
    private Long id;

    private String address, phone, country, province, city;

    private int zipCode;

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.phone = address.getPhone();
        this.country = address.getCountry();
        this.province = address.getProvince();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
    }

    public Long getId() {
        return id;
    }

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
