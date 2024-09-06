package com.StockControlApp.Services;

import com.StockControlApp.Entity.Item;
import com.StockControlApp.Entity.Project;
import com.StockControlApp.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project addItemToProject(Long projectId, Item newItem) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.getItems().add(newItem);
            newItem.setProject(project);
            return projectRepository.save(project);
        } else {
            throw new RuntimeException("Project with ID " + projectId + " not found.");
        }
    }
    public Project deleteItem(Item item, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            if (project.getItems().contains(item)) {
                project.getItems().remove(item);
                // Set the item's project to null (unlink it)
                item.setProject(null);
                return projectRepository.save(project);
            } else {
                throw new RuntimeException("Item not found in the project with ID " + projectId);
            }
        } else {
            throw new RuntimeException("Project with ID " + projectId + " not found.");
        }
    }

}
