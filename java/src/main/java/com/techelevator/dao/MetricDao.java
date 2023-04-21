package com.techelevator.dao;

import com.techelevator.model.Metric;

import java.util.List;

public interface MetricDao {
    Metric getLastSixMonths();
    Metric getThisMonth();
    Metric getRatingsSixMonths();
}
