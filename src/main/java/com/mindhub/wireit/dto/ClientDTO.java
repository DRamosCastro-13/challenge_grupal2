package com.mindhub.wireit.dto;

import com.mindhub.wireit.models.Address;
import com.mindhub.wireit.models.Client;
import com.mindhub.wireit.models.PurchaseOrder;
import com.mindhub.wireit.models.enums.Role;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private Long id;

    private String firstName, lastName, email;

    private int dni;

    private Role role = Role.CLIENT;

    private Set<AddressDTO> addresses;

    private Set<PurchaseOrderDTO> purchaseOrders;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.dni = client.getDni();
        this.role = client.getRole();
        this.addresses = client.getAddresses().stream().map(AddressDTO :: new).collect(Collectors.toSet());
        this.purchaseOrders = client.getOrders().stream().map(PurchaseOrderDTO ::new).collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getDni() {
        return dni;
    }

    public Role getRole() {
        return role;
    }

    public Set<AddressDTO> getAddresses() {
        return addresses;
    }

    public Set<PurchaseOrderDTO> getPurchaseOrders() {
        return purchaseOrders;
    }
}
