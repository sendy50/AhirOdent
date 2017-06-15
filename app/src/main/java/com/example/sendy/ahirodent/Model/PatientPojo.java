package com.example.sendy.ahirodent.Model;

/**
 * Created by Sendy on 25-Oct-16.
 */
public class PatientPojo
{
    int patient_id;
    int appointment_id;
    int case_type;
    String fb_id;
    String patient_name;
    String patient_email;
    String patient_number;
    String patient_dob;
    String gender;
    String member;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getCase_type() {
        return case_type;
    }

    public void setCase_type(int case_type) {
        this.case_type = case_type;
    }


    public String getPatient_dob() {
        return patient_dob;
    }

    public void setPatient_dob(String patient_dob) {
        this.patient_dob = patient_dob;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getPatient_number() {
        return patient_number;
    }

    public void setPatient_number(String patient_number) {
        this.patient_number = patient_number;
    }

    public int getPatient_id() {
        return patient_id;

    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

}
