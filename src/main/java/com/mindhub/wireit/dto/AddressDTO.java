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
}
