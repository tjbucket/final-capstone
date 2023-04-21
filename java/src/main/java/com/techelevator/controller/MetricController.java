package com.techelevator.controller;

import com.techelevator.dao.JdbcMetricDao;
import com.techelevator.model.Metric;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class MetricController {
    JdbcMetricDao metricDao;

    public MetricController(JdbcMetricDao metricDao) {
        this.metricDao = metricDao;
    }

    @GetMapping("/metric/6m")
    public Metric getLastSixMonths(){
        return metricDao.getLastSixMonths();
    }

    @GetMapping("/metric")
    public Metric getThisMonth() {return metricDao.getThisMonth();}

    @GetMapping("/ratings")
    public Metric getSixMonthsRatings(){return metricDao.getRatingsSixMonths();}
}
