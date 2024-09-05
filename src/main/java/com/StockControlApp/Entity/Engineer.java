package com.StockControlApp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@DiscriminatorValue("Engineer")

public class Engineer extends User{
    @ManyToMany(mappedBy = "engineers")
    private List<Project> projects;





}
