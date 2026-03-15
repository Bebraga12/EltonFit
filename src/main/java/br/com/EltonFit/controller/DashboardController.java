package br.com.EltonFit.controller;

import br.com.EltonFit.dto.dashboard.AdminDashboardSummaryDTO;
import br.com.EltonFit.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin-summary")
    public AdminDashboardSummaryDTO adminSummary() {
        return dashboardService.getAdminSummary();
    }
}
