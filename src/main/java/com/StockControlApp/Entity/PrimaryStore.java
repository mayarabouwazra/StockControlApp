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


    @OneToMany(mappedBy = "primaryStore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    public PrimaryStore() {}

    public PrimaryStore(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}
