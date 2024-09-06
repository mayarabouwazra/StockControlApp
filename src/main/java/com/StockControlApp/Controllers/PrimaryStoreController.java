package com.StockControlApp.Controllers;


import com.StockControlApp.Entity.Item;
import com.StockControlApp.Entity.PrimaryStore;
import com.StockControlApp.Services.PrimaryStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/primaryStore")
public class PrimaryStoreController {

    @Autowired
    private PrimaryStoreService primaryStoreService;
    @GetMapping("/{id}/items")
    public PrimaryStore getPrimaryStoreWithItems(@PathVariable Long id) {
        return primaryStoreService.getPrimaryStoreWithItems(id);
    }
    @PostMapping("/addItem")
    public Item addItemToPrimaryStore(@RequestBody Item newItem) {
        return primaryStoreService.addItemToPrimaryStore(newItem);
    }
    //don't forget delete item

}
