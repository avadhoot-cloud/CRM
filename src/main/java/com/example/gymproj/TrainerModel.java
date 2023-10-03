package com.example.gymproj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TrainerModel {
    private int trainerid;
    private int memberid;
    private String firstName;
    private String lastName;
    private String gender;
    private String contact;
    private LocalDate joiningDate;
    private double salary;
    private String shift;

    // Constructors, getters, and setters
    // ...

    public TrainerModel() {
        // Default constructor
    }

    public TrainerModel(int trainerid, int memberid, String firstName, String lastName, String gender, String contact, LocalDate joiningDate, double salary, String shift) {
        this.trainerid = trainerid;
        this.memberid = memberid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.contact = contact;
        this.joiningDate = joiningDate;
        this.salary = salary;
        this.shift = shift;
    }

    // Getter and setter methods for each field
    public int getTrainerId() {
        return trainerid;
    }

    public int getMemberId() {
        return memberid;
    }

    public void setMemberId(int memberid) {
        this.memberid = memberid;
    }

    public void setTrainerId(int id) {
        this.trainerid = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void deleteFromDatabase() {
        try {
            Connection connection = DbConnect.getConnection();
            String sql = "DELETE FROM trainer WHERE trainer_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.getTrainerId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL errors here
        }
    }
}

