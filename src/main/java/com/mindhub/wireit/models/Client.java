package com.mindhub.wireit.models;

import com.mindhub.wireit.models.enums.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName, lastName, email, password;

    private int dni;

    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<PurchaseOrder> PurchaseOrders = new ArrayList<>();

    public Client() {
    }

    public Client(String firstName, String lastName, String email, int dni, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dni = dni;
        this.password = password;
    }

    public void addAddress(Address address) {
        address.setClient(this);
        this.addresses.add(address);
    }

    public void addOrders(PurchaseOrder purchaseOrder) {
        purchaseOrder.setClient(this);
        this.PurchaseOrders.add(purchaseOrder);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return PurchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        PurchaseOrders = purchaseOrders;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
