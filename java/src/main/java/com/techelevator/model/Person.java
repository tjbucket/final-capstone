package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    @JsonProperty("id")
    int personId;
    int userId;
    @JsonProperty("role")
    String roleName;
    @JsonProperty("unit")
    int unitNumber;
    @JsonProperty("first")
    String firstName;
    @JsonProperty("last")
    String lastName;
    String email;
    String propertyComment;

    public Person() {
    }

    public Person(int userId, String roleName, int unitNumber, String firstName, String lastName) {
        this.userId = userId;
        this.roleName = roleName;
        this.unitNumber = unitNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getPropertyComment() {
        return propertyComment;
    }

    public void setPropertyComment(String propertyComment) {
        this.propertyComment = propertyComment;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
