package com.techelevator.dao;

import com.techelevator.model.Metric;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcMetricDao implements MetricDao{
    JdbcTemplate jdbcTemplate;

    public JdbcMetricDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Metric getLastSixMonths() {
        Metric metric = new Metric();
        for (int i = 6; i > 0; i--) {
            String sql = "SELECT avg(extract(epoch from(when_accepted - when_posted)/3600)) as avg_time, cast(EXTRACT(MONTH FROM LOCALTIMESTAMP - interval '" +
                    i + " months') AS int) AS month FROM work_orders WHERE when_accepted IS NOT NULL AND cast(EXTRACT(MONTH FROM when_posted)as int) = cast(EXTRACT(MONTH FROM LOCALTIMESTAMP - interval '" +
                    i + " months')AS int)";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.first()){
                metric.addValue(results.getDouble("avg_time"));
                metric.addLabel(results.getInt("month"));
            }
        }
        return metric;
    }

    @Override
    public Metric getThisMonth() {
        Metric metric = new Metric();
        String sql = "SELECT avg(rating) as avg_rating, avg(extract(epoch from(when_accepted - when_posted)/3600)) as avg_time, count(order_id) as count " +
                "FROM work_orders WHERE when_accepted IS NOT NULL AND CAST(EXTRACT(MONTH FROM when_posted)AS int) = CAST(EXTRACT(MONTH FROM LOCALTIMESTAMP)AS int)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        if (results.first()){
            metric.addValue(results.getDouble("avg_rating"));
            metric.addValue(results.getDouble("avg_time"));
            metric.addValue(results.getDouble("count"));
        }
        return metric;
    }

    @Override
    public Metric getRatingsSixMonths() {
        Metric metric = new Metric();
        for (int i = 1; i < 6; i++) {
            String sql = "SELECT count(rating) AS number_of_reviews FROM work_orders WHERE rating = ? AND when_accepted BETWEEN LOCALTIMESTAMP - interval '6 months' AND LOCALTIMESTAMP";
            Double value = jdbcTemplate.queryForObject(sql, Double.class, i);
            metric.addValue(value);
        }
        return metric;
    }
}
