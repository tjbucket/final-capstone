package com.techelevator.dao;

import com.techelevator.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPersonDao implements PersonDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getPersonIdFromUserName(String userName) {
        String sql = "SELECT p.person_id FROM persons as p JOIN users AS u ON p.user_id = u.user_id " +
                "WHERE u.username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userName);
    }
    @Override
    public int getUserIdFromUserName(String userName) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, userName);
        return id;
    }

    @Override
    public Person getPersonFromUserName(String userName) {
        String sql = "SELECT p.person_id, p.user_id, r.role_description, p.unit_number, p.first_name, p.last_name, p.email, p.property_comment " +
                "FROM persons AS p JOIN users AS u ON p.user_id = u.user_id FULL OUTER JOIN roles AS r ON p.role_id = r.role_id " +
                "WHERE u.username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
        if (results.next()) {
            return mapRowToPerson(results);
        } else {
            return new Person();
        }
    }


    @Override
    public boolean insertPersonalInfo(Person person) {
        String sql = "INSERT INTO persons(user_id, first_name, last_name, email) VALUES (?,?,?,?) RETURNING person_id";
        Integer newId = jdbcTemplate.queryForObject(sql,Integer.class, person.getUserId(),person.getFirstName(),person.getLastName(), person.getEmail());
        return newId != null;
    }

    @Override
    public List<Person> listTenantsWithoutRoles() {
        String sql = "SELECT person_id, first_name, last_name, email FROM persons WHERE role_id IS NULL";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        List<Person> tenants = new ArrayList<>();
        while(results.next()){
            Person person = new Person();
            person.setPersonId(results.getInt("person_id"));
            person.setFirstName(results.getString("first_name"));
            person.setLastName(results.getString("last_name"));
            person.setEmail(results.getString("email"));
            tenants.add(person);
        }
        return tenants;
    }

    @Override
    public List<String> getTenantsBrief() {
        List<String> tenants = new ArrayList<>();
        String sql = "SELECT person_id, first_name, last_name, unit_number FROM persons WHERE unit_number > 0 ORDER BY person_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            String tenantSelectInfo = results.getInt("person_id") + ": " + results.getString("first_name") + " " +
                    results.getString("last_name") + " | Unit: " + results.getInt("unit_number");
            tenants.add(tenantSelectInfo);
        }
        return tenants;
    }

    @Override
    public List<Person> getAllPersonnel() {
        List<Person> personnel = new ArrayList<>();
        String sql = "SELECT p.person_id, p.user_id, r.role_description, p.unit_number, p.property_comment, p.first_name, p.last_name, p.email " +
                "FROM persons AS p JOIN roles AS r ON p.role_id = r.role_id " +
                "WHERE p.role_id = 2 OR p.role_id = 3 ORDER BY p.user_id ASC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            personnel.add(mapRowToPerson(results));
        }
        return personnel;
    }

    @Override
    public Person getTenantById(int id) {
        String sql = "SELECT p.person_id, p.user_id, r.role_description, p.unit_number, p.property_comment, p.first_name, p.last_name, p.email " +
                "FROM persons AS p JOIN roles AS r ON p.role_id = r.role_id " +
                "WHERE p.person_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()) {
            return mapRowToPerson(results);
        }
        return null;
    }

    @Override
    public void updatePerson(Person person) {
        String sql = "UPDATE persons SET role_id = ?, unit_number = ?, property_comment = ? WHERE person_id = ?";
        jdbcTemplate.update(sql, getRoleIdFromName(person.getRoleName()), person.getUnitNumber(), person.getPropertyComment(), person.getPersonId());
    }

    @Override
    public void removePerson(int id) {
        String sql = "DELETE FROM persons WHERE person_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updatePropComment(String comment, int id) {
        String sql = "UPDATE persons SET property_comment = ? WHERE person_id = ?";
        jdbcTemplate.update(sql, comment, id);
    }

//    Helper Methods
    private Person mapRowToPerson(SqlRowSet results){
        Person person = new Person();
        person.setFirstName(results.getString("first_name"));
        person.setLastName(results.getString("last_name"));
        person.setEmail(results.getString("email"));
        person.setPersonId(results.getInt("person_id"));
        person.setUserId(results.getInt("user_id"));
        person.setRoleName(results.getString("role_description"));
        person.setUnitNumber(results.getInt("unit_number"));
        person.setPropertyComment(results.getString("property_comment"));
        return person;
    }

    private int getRoleIdFromName(String name){
        String sql = "SELECT role_id FROM roles WHERE role_description = ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return id;
    }

}
