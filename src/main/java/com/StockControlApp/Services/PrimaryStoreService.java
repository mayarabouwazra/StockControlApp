package com.StockControlApp.Services;

import com.StockControlApp.Entity.Item;
import com.StockControlApp.Entity.PrimaryStore;
import com.StockControlApp.Repositories.ItemRepository;
import com.StockControlApp.Repositories.PrimaryStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimaryStoreService {
    @Autowired
    private PrimaryStoreRepository primaryStoreRepository;

    @Autowired
    private ItemRepository itemRepository;

    public PrimaryStore getPrimaryStoreWithItems(Long id) {
        return primaryStoreRepository.findById(id).orElse(null);
    }

    public Item addItemToPrimaryStore(Item newItem) {
        Long primaryStoreId = 1L;
        PrimaryStore primaryStore = primaryStoreRepository.findById(primaryStoreId)
                .orElseThrow(() -> new RuntimeException("Primary Store not found"));

        newItem.setPrimaryStore(primaryStore);

        return itemRepository.save(newItem);
    }
}
