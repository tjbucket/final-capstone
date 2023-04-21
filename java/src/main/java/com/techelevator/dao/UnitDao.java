package com.techelevator.dao;

import com.techelevator.model.Unit;

import java.util.List;

public interface UnitDao {
    public List<Unit> getAllUnits();

    public Unit getUnitById(int id);

    void updatePropComment(String commentAsStr, int id);
}
