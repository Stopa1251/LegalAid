package com.example.lawtest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> ordersCreated;


    public User setClient(Client client) {
        this.setUser(client);
        this.setOrdersCreated(client.getOrdersCreated());

        return this;
    }

    public List<Order> getOrdersCreated() {
        return ordersCreated;
    }

    public void setOrdersCreated(List<Order> ordersCreated) {
        this.ordersCreated = ordersCreated;
    }
}



