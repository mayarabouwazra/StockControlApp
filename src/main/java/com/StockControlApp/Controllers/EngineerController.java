package com.StockControlApp.Controllers;

import com.StockControlApp.Entity.Engineer;
import com.StockControlApp.Exceptions.ResourceNotFoundException;
import com.StockControlApp.Services.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Engineer")
public class EngineerController {
    private final EngineerService engineerService;


    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }
    @GetMapping
    public List<Engineer> getAllEngineers() {
        return engineerService.getAllEngineers();
    }
    @PostMapping
    public ResponseEntity<Engineer> saveEngineer(@RequestBody Engineer engineer) {
        Engineer savedEngineer = engineerService.saveEngineer(engineer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEngineer);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteEngineerByEmail(@PathVariable String email) {
        try {
            engineerService.deleteEngineerByEmail(email);
            return ResponseEntity.ok("Engineer deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/{email}")
    public ResponseEntity<Engineer> getEngineerByEmail(@PathVariable String email) {
        Engineer engineer = engineerService.getEngineerByEmail(email);
        if (engineer == null) {
            throw new ResourceNotFoundException("Engineer not found with email: " + email);
        }
        return ResponseEntity.ok(engineer);
    }



}
