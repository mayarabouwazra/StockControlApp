package com.StockControlApp.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity

@DiscriminatorValue("Admin")
public class Admin extends User{
    public Admin() {
    }

    public Admin(String email, String password) {
        super(email, password);
    }
}

