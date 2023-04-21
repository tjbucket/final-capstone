package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

public class Metric {
    List<String> labels;
    List<Double> values;

    public Metric() {
        this.labels = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public Metric(List<String> labels, List<Double> values) {
        this.labels = labels;
        this.values = values;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public void addValue(double value){
        this.values.add(value);
    }

    public void addLabel(int label){
        switch (label){
            case 1: this.labels.add("January"); break;
            case 2: this.labels.add("February");break;
            case 3: this.labels.add("March");break;
            case 4: this.labels.add("April");break;
            case 5: this.labels.add("May");break;
            case 6: this.labels.add("June");break;
            case 7: this.labels.add("July");break;
            case 8: this.labels.add("August");break;
            case 9: this.labels.add("September");break;
            case 10: this.labels.add("October");break;
            case 11: this.labels.add("November");break;
            case 12: this.labels.add("December");break;
        }
    }
}


