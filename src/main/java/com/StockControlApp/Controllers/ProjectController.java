package com.StockControlApp.Controllers;

import com.StockControlApp.Entity.Item;
import com.StockControlApp.Entity.Project;
import com.StockControlApp.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/{projectId}/addItem")
    public Project addItemToProject(@PathVariable Long projectId, @RequestBody Item newItem) {
        if (projectId == null) {
            throw new RuntimeException("Project ID is required.");
        }
        if (newItem == null) {
            throw new RuntimeException("Item is required.");
        }
        return projectService.addItemToProject(projectId, newItem);
    }

    @DeleteMapping("/{projectId}/deleteItem")
    public Project deleteItem(@PathVariable Long projectId, @RequestBody Item item) {
        if (projectId == null) {
            throw new RuntimeException("Project ID is required.");
        }
        if (item == null) {
            throw new RuntimeException("Item is required.");
        }
        return projectService.deleteItem(item, projectId);
    }
}
