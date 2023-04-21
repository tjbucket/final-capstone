package com.techelevator.dao;

import com.techelevator.model.Person;

import java.util.List;

public interface PersonDao {
    int getPersonIdFromUserName(String userName);
    Person getPersonFromUserName(String userName);
    int getUserIdFromUserName(String userName);
    boolean insertPersonalInfo(Person person);
    List<Person> listTenantsWithoutRoles();

    List<String> getTenantsBrief();

    List<Person> getAllPersonnel();

    Person getTenantById(int id);

    void updatePerson(Person person);
    void removePerson(int id);
    void updatePropComment(String comment, int id);
}
