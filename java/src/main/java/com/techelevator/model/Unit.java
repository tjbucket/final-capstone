package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Unit {
    @JsonProperty("number")
    private int unitNumber;
    @JsonProperty("description")
    private String unitDescription;
    private String propertyComment;

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public String getPropertyComment() {
        return propertyComment;
    }

    public void setPropertyComment(String propertyComment) {
        this.propertyComment = propertyComment;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }
}
