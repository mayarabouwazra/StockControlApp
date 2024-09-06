package com.StockControlApp.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

     private String projectName;
    private String projectDescription;
     private String projectManager;
    private String location;
    private String engineerEmail;
    private boolean stockComplete;
    private boolean destinationConfirmed;
//items
    @ManyToMany
    @JoinTable(
            name = "project_engineer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_id")
    )
    private List<Engineer> engineers;

    public Project() {
    }

    public Project(String projectName, String projectDescription, String projectManager, String location) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectManager = projectManager;
        this.location = location;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public List<Engineer> getEngineers() {
        return engineers;
    }

    public void setEngineers(List<Engineer> engineers) {
        this.engineers = engineers;
    }

    public boolean isStockComplete() {
        return stockComplete;
    }

    public void setStockComplete(boolean stockComplete) {
        this.stockComplete = stockComplete;
    }

    public boolean isDestinationConfirmed() {
        return destinationConfirmed;
    }

    public void setDestinationConfirmed(boolean destinationConfirmed) {
        this.destinationConfirmed = destinationConfirmed;
    }

    public String getEngineerEmail() {
        return engineerEmail;
    }

    public void setEngineerEmail(String engineerEmail) {
        this.engineerEmail = engineerEmail;
    }
}
