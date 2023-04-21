package com.techelevator.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.techelevator.dao.JdbcPersonDao;
import com.techelevator.dao.JdbcUnitDao;
import com.techelevator.model.Person;
import com.techelevator.model.Unit;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class UnitController {
    JdbcUnitDao unitDao;

    public UnitController(JdbcUnitDao unitDao) {
        this.unitDao = unitDao;
    }


    @GetMapping("/units")
    public List<Unit> getUnits() {
        return unitDao.getAllUnits();
    }

    @GetMapping("/units/{id}")
    public Unit getUnitById(@PathVariable int id) {
        return unitDao.getUnitById(id);
    }

    @PutMapping("/units/{id}/comment/")
    public void tenantPropComment(@RequestBody TextNode comment, @PathVariable int id){
        String commentAsStr = comment.asText();
        unitDao.updatePropComment(commentAsStr, id);
    }
}
