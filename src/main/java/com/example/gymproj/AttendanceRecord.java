package com.example.gymproj;



import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class AttendanceRecord {

    private final ObjectProperty<LocalDate> date;
    private final StringProperty status;

    public AttendanceRecord(LocalDate date, String status) {
        this.date = new SimpleObjectProperty<>(date);
        this.status = new SimpleStringProperty(status);
    }

    // Add getters and setters for date and status properties

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public StringProperty statusProperty() {
        return status;
    }
}
