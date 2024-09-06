package com.StockControlApp.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity

@DiscriminatorValue("Engineer")

public class Engineer extends User{
    @ManyToMany(mappedBy = "engineers")
    private List<Project> projects;

    public Engineer() {
    }

    public Engineer(String email, String password) {
        super(email, password);
    }

    public Engineer(List<Project> projects) {
        this.projects = projects;
    }

    public Engineer(String email, String password, List<Project> projects) {
        super(email, password);
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
