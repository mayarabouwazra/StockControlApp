package com.StockControlApp.Repositories;

import com.StockControlApp.Entity.PrimaryStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryStoreRepository extends JpaRepository<PrimaryStore,Long> {
}
