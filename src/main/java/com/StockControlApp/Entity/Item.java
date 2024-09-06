package com.StockControlApp.Entity;

import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String description;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;



    @ManyToOne
    @JoinColumn(name = "primary_store_id")
    private PrimaryStore primaryStore;

    public Item() {}

    public Item(String itemName, String description, int quantity) {
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public PrimaryStore getPrimaryStore() {
        return primaryStore;
    }

    public void setPrimaryStore(PrimaryStore primaryStore) {
        this.primaryStore = primaryStore;
    }


}
