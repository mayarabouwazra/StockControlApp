package com.StockControlApp.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class PrimaryStore {
    @Id
    private Long id; // Assuming a fixed ID for the single primary store

    private String storeName;

    @OneToMany(mappedBy = "primaryStore", cascade = CascadeType.ALL)
    private List<Item> items;

    public PrimaryStore() {}

    public PrimaryStore(Long id, String storeName) {
        this.id = id;
        this.storeName = storeName;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}
