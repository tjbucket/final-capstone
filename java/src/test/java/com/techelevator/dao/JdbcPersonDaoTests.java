package com.techelevator.dao;

import com.techelevator.model.Person;
import com.techelevator.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcPersonDaoTests extends BaseDaoTests {

    private static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER");
    private static final User USER_2 = new User(2, "user2", "user2", "ROLE_USER");
    private static final User USER_3 = new User(3, "user3", "user3", "ROLE_USER");
    private static final User USER_4 = new User(4, "user4", "user4", "ROLE_USER");

//    private static final Person PERSON_1 = new Person(1, "Tenant", 1001, "John", "Doe");

//    INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
//    VALUES (1, 1, 1001, 'John', 'Doe');

//    INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
//    VALUES (2, 1, 1001, 'Mary', 'Smith');
//    INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
//    VALUES (3, 1, 2001, 'Doug', 'Brown');
//    INSERT INTO persons (user_id, role_id, unit_number, first_name, last_name)
//    VALUES (4, 2, 3001, 'Bob', 'Jones');

    private JdbcPersonDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcPersonDao(jdbcTemplate);
    }

    @Test
    public void getPersonIdFromUserName_given_valid_username_returns_person_id() {

    }

}
