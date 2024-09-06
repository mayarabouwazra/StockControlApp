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
        return projectService.addItemToProject(projectId, newItem);
    }
    @DeleteMapping("/{projectId}/deleteItem")
    public  Project deleteItem (@PathVariable Long projectId,@RequestBody Item item){
        return projectService.deleteItem(item,projectId);
    }
}