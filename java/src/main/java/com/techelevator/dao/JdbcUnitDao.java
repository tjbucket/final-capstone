package com.techelevator.dao;

import com.techelevator.model.Unit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUnitDao implements UnitDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcUnitDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT * FROM units WHERE unit_number > 0";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            units.add(mapRowToUnit(results));
        }
        return units;
    }

    @Override
    public Unit getUnitById(int id) {
        String sql = "SELECT * FROM units WHERE unit_number = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            return mapRowToUnit(results);
        }
        return null;
    }

    @Override
    public void updatePropComment(String comment, int id) {
        String sql = "UPDATE units SET property_comment = ? WHERE unit_number = ?";
        jdbcTemplate.update(sql, comment, id);
    }

    private Unit mapRowToUnit(SqlRowSet rs) {
        Unit unit = new Unit();
        unit.setUnitNumber(rs.getInt("unit_number"));
        unit.setPropertyComment(rs.getString("property_comment"));
        unit.setUnitDescription(rs.getString("unit_description"));
        return unit;
    }
}
