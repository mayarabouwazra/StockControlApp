package com.StockControlApp.Controllers;

import com.StockControlApp.Services.StockAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stockadmin")
public class StockAdminController {
    @Autowired
    private StockAdminService stockAdminService;

    @PostMapping("/update-stock-status")
    public String updateStockStatus(@RequestParam Long projectId, @RequestParam boolean isComplete, @RequestParam(required = false) String missingItems) {
        stockAdminService.updateStockStatus(projectId, isComplete, missingItems != null ? missingItems : "");
        return "Stock status updated and engineer notified.";
    }

    @PostMapping("/confirm-destination")
    public String confirmDestination(@RequestParam Long projectId) {
        stockAdminService.confirmDestination(projectId);
        return "Destination confirmed.";
    }
}
