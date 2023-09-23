package com.example.gymproj;



import java.time.LocalDate;
import java.util.Date;

    public class MemberModel {
        private int id;
        private String fullName;
        private LocalDate dob;
        private String gender;
        private String mobile;

        public MemberModel(int id, String fullName, LocalDate dob, String gender, String mobile) {
            this.id = id;
            this.fullName = fullName;
            this.dob = dob;
            this.gender = gender;
            this.mobile = mobile;
        }

        // Getters and setters for the properties


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public LocalDate getDob() {
            return dob;
        }

        public void setDob(LocalDate dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }


