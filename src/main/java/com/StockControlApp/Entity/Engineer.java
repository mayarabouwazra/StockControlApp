package com.StockControlApp.Entity;

import jakarta.persistence.*;

import java.util.HashSet;

@Entity

@DiscriminatorValue("Engineer")

public class Engineer extends User{
    @ManyToMany(mappedBy = "engineers")
    private HashSet<Project> projects;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Engineer() {
    }

    public Engineer(String email, String password) {
        super(email, password);
    }

    public Engineer(HashSet<Project> projects) {
        this.projects = projects;
    }

    public Engineer(String email, String password, HashSet<Project> projects) {
        super(email, password);
        this.projects = projects;
    }

    public HashSet<Project> getProjects() {
        return projects;
    }

    public void setProjects(HashSet<Project> projects) {
        this.projects = projects;
    }
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
