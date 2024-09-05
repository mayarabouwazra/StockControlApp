package com.StockControlApp.Repositories;

import com.StockControlApp.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Project,Long> {
}
