package com.StockControlApp.Services;

import com.StockControlApp.Entity.Item;
import com.StockControlApp.Entity.Project;
import com.StockControlApp.Exceptions.ItemNotFoundException;
import com.StockControlApp.Exceptions.ProjectNotFoundException;
import com.StockControlApp.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    private Project findProjectById(Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            return projectOptional.get();
        } else {
            throw new ProjectNotFoundException("Project with ID " + projectId + " not found.");
        }
    }

    public Project addItemToProject(Long projectId, Item newItem) {
        Project project = findProjectById(projectId);
        project.getItems().add(newItem);
        newItem.setProject(project);
        return projectRepository.save(project);
    }

    public Project deleteItem(Item item, Long projectId) {
        Project project = findProjectById(projectId);
        if (project.getItems().contains(item)) {
            project.getItems().remove(item);
            item.setProject(null);
            return projectRepository.save(project);
        } else {
            throw new ItemNotFoundException("Item not found in the project with ID " + projectId);
        }
    }
}