package com.StockControlApp.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MobileStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plateNb;
    private boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "mobileStore")
    private List<Item> items;


    public MobileStore(){

    }

    public MobileStore(int plateNb, boolean isAvailable) {
        this.plateNb = plateNb;
        this.isAvailable = isAvailable;
    }

    public int getPlateNb() {
        return plateNb;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
