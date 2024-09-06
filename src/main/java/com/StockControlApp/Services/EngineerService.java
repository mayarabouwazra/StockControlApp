package com.StockControlApp.Services;

import com.StockControlApp.Entity.Engineer;
import com.StockControlApp.Exceptions.ResourceNotFoundException;
import com.StockControlApp.Repositories.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineerService {
    @Autowired
    private EngineerRepository engineerRepository;

    public List<Engineer> getAllEngineers() {
        return engineerRepository.findAll();
    }
    public Engineer getEngineerByEmail(String email) {
        return engineerRepository.findByEmail(email);
    }
    public Engineer saveEngineer(Engineer engineer) {
        return engineerRepository.save(engineer);
    }

    public void deleteEngineerByEmail(String email) {
        Engineer engineer = engineerRepository.findByEmail(email);
        if (engineer == null) {
            throw new ResourceNotFoundException("Engineer not found with email: " + email);
        }
        engineerRepository.delete(engineer);
    }

}
