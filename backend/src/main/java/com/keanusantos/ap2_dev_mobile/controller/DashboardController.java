package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.dto.DashboardDTO;
import com.keanusantos.ap2_dev_mobile.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardDTO dashboard() {
        return dashboardService.dashboard();
    }
}
