package com.StockControlApp.Controllers;

import com.StockControlApp.Entity.Engineer;
import com.StockControlApp.Services.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Engineer")
public class EngineerController {
    @Autowired
    private EngineerService engineerService;
    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }
    @GetMapping
    public List<Engineer> getAllEngineers() {
        return engineerService.getAllEngineers();
    }
    @PostMapping
    public Engineer saveEngineer(@RequestBody Engineer engineer) {
        return engineerService.saveEngineer(engineer);
    }

    @DeleteMapping("/{email}")
    public void deleteEngineerByEmail(@PathVariable String email) {
        engineerService.deleteEngineerByEmail(email);
    }



}
