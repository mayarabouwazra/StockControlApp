package com.StockControlApp.Repositories;

import com.StockControlApp.Entity.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineerRepository extends JpaRepository <Engineer,String> {
    Engineer findByEmail(String email);

}
