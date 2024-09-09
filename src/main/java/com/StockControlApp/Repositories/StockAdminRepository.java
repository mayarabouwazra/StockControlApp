package com.StockControlApp.Repositories;

import com.StockControlApp.Entity.StockAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAdminRepository extends JpaRepository<StockAdmin,String> {
    StockAdmin findByEmail(String email);
}
