package com.example.sendy.ahirodent.Services;

import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Model.EventPojo;
import com.example.sendy.ahirodent.Model.PatientPojo;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Sendy on 23-Oct-16.
 */
public interface RegisterApi {

    @FormUrlEncoded
    @POST("/insertuser.php?insert")
    public void insertUser(
            @Field("fb_id") String fb_id,
            @Field("case_type") String case_type,
            @Field("patient_name") String patient_name,
            @Field("patient_number") String patient_number,
            @Field("patient_email") String patient_email,
            @Field("patient_dob") String patient_dob,
            @Field("gender") String gender,
            @Field("password") String password,
            Callback<List<PatientPojo>> patientList);

    @FormUrlEncoded
    @POST("/registerfcm.php")
    public void insertFcmToken(
            @Field("name") String name,
            @Field("Token") String Token,
            Callback<Response> token);

    @FormUrlEncoded
    @POST("/push_notification.php?patient")
    public void sendMessage(
            @Field("message") String message,
            @Field("title") String title,
            Callback<Response> massage);


    @FormUrlEncoded
    @POST("/insertevent.php?insert")
    public void insertEvent(
            @Field("event_title") String event_title,
            @Field("event_message") String event_message,
            @Field("event_startdate") String event_startdate,
            @Field("event_lastdate") String event_lastdate,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/insertevent.php?delete")
    public void deleteEvent(
            @Field("event_title") String event_title,
            Callback<Response> callback);

    @GET("/insertevent.php?fatch")
    public void getAllEvents(Callback<List<EventPojo>> response);



    @FormUrlEncoded
    @POST("/insertAppointments.php")
    public void insertAppointments(
            @Field("patient_id") int patient_id,
            @Field("case_type") int case_type,
            @Field("patient_name") String patient_name,
            @Field("patient_number") String patient_number,
            @Field("patient_email") String patient_email,
            @Field("appointment_date") String appointment_date,
            @Field("appointment_time") String appointment_time,
            @Field("gender") String gender,
            @Field("status") String status,
            Callback<List<AppointmentPojo>> appointmentlist);


    @GET("/getAllAppointmentsList.php?all")
    public void getAllAppointments(Callback<List<AppointmentPojo>> response);

    @FormUrlEncoded
    @POST("/getAllAppointmentsList.php?update")
    public void updateAppointments(
            @Field("appointment_id") int appointment_id,
            Callback<Response> callback);


    @GET("/getAllPatient.php?patient")
    public void getAllPatient(Callback<List<PatientPojo>> response);


    @GET("/getAllPatient.php?member")
    public void getmemberPatient(Callback<List<PatientPojo>> response);

    @FormUrlEncoded
    @POST("/getAllAppointmentsList.php?selected")
    public void getSelectedAppointment(
            @Field("patient_id") int patient_id,
            Callback<List<AppointmentPojo>> response);

    @FormUrlEncoded
    @POST("/insertuser.php?update")
    public void updatepatient(
            @Field("patient_id") int patient_id,
            @Field("member") String member,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/getAllPatient.php?selected")
    public void getSelectedPatient(
            @Field("patient_id") int patient_id,
            Callback<List<PatientPojo>> patientList);

    @FormUrlEncoded
    @POST("/insertuser.php?login")
    public void CheckLogin(@Field("patient_email") String patient_email,
                           @Field("password") String password,
                           Callback<List<PatientPojo>> response);

    @FormUrlEncoded
    @POST("/push_notification.php?dr")
    public void sendMessagee(
            @Field("message") String message,
            @Field("title") String title,
            @Field("name") String name,
            Callback<Response> massage);

    @FormUrlEncoded
    @POST("/registerfcm.php?dr")
    public void insertFcmTokendr(
            @Field("name") String name,
            @Field("Token") String Token,
            Callback<Response> token);

}
