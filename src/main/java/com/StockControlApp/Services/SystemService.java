package com.StockControlApp.Services;


import com.StockControlApp.Entity.MobileStore;
import com.StockControlApp.Entity.Project;
import com.StockControlApp.Repositories.MobileStoreRepository;
import com.StockControlApp.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MobileStoreRepository mobileStoreRepository;

    public void assignMobileStoreToProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            MobileStore mobileStore = selectMobileStore();
            project.setMobileStore(mobileStore);
            project.setDestinationConfirmed(false);
            projectRepository.save(project);

            System.out.println("MobileStore assigned to project " + project.getProjectName() + ". Awaiting confirmation.");
        }
    }

    private MobileStore selectMobileStore() {
        // For simplicity, i'll just pick the first available one
        return mobileStoreRepository.findAll().get(0);
    }
}
