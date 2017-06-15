package com.example.sendy.ahirodent.Model;

import java.util.Map;
import java.util.HashMap;


public class generalpojo {

    int patient_id;
    int appointment_id;

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }





    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }


    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The patientId
     */
    public int getPatientId() {
        return patient_id;
    }

    /**
     *
     * @param patientId
     * The patient_id
     */
    public void setPatientId(int patientId) {
        this.patient_id = patientId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
