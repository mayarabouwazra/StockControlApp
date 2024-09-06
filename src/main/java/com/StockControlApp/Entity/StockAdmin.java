package com.StockControlApp.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

@DiscriminatorValue("StockAdmin")

public class StockAdmin extends User{
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public StockAdmin() {
    }

    public StockAdmin(String email, String password) {
        super(email, password);
    }

    public StockAdmin(String email, String password, Profile profile) {
        super(email, password);
        this.profile = profile;
    }

    public StockAdmin(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
