package com.techelevator.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.techelevator.dao.JdbcPersonDao;
import com.techelevator.dao.JdbcWorkOrderDao;
import com.techelevator.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class PersonController {
    JdbcPersonDao personDao;

    public PersonController(JdbcPersonDao personDao, JdbcWorkOrderDao workOrderDao) {
        this.personDao = personDao;
    }

    @GetMapping("/person")
    public Person getUserDetails(Principal principal) {
            return personDao.getPersonFromUserName(principal.getName());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/person")
    public void insertPersonalDetails(@RequestBody Person person, Principal principal){
        person.setUserId(personDao.getUserIdFromUserName(principal.getName()));
        boolean success = personDao.insertPersonalInfo(person);
        if (!success){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User info not displayed");
        }
    }
    @GetMapping("/pending")
    public List<Person> listTenantsWithoutRoles(){
        return personDao.listTenantsWithoutRoles();
    }

    @GetMapping("/tenants")
    public List<String> getTenantsBrief() {
        return personDao.getTenantsBrief();
    }

    @GetMapping("/tenants/{id}")
    public Person getTenantById(@PathVariable int id) {
        return personDao.getTenantById(id);
    }

    @DeleteMapping("/tenants/{id}")
    public void deleteTenant(@PathVariable int id){
        personDao.removePerson(id);
    }

    @PutMapping("/pending")
    public void approvePerson(@RequestBody Person person){
        personDao.updatePerson(person);
    }

    @PutMapping("tenants/{id}/comment/")
    public void tenantPropComment(@RequestBody TextNode comment, @PathVariable int id){
        String commentAsStr = comment.asText();
        personDao.updatePropComment(commentAsStr, id);
    }

    @GetMapping("/personnel")
    public List<Person> getAllPersonnel(){
        return personDao.getAllPersonnel();
    }
}
