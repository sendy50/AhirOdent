package com.example.sendy.ahirodent.Model;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit.Callback;
import retrofit.client.*;
import retrofit.client.Response;

/**
 * Created by Sendy on 24-Sep-16.
 */
public class M
{

    public static String getLoginData(Context ctxt, String image) {

        SharedPreferences sharedPref = ctxt.getSharedPreferences("login_data",Context.MODE_PRIVATE);
        String name = sharedPref.getString(image,"Sendy");
        return name;
    }


    public static void setLoginData(Context context, String email, String name, String profilePicUrl, String id) {
        SharedPreferences p = context.getSharedPreferences("login_data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("email",email);
        editor.putString("name",name);
        editor.putString("profilepic",profilePicUrl);
        editor.putString("userid",id);
        editor.apply();
        editor.commit();
    }

    public static void setAppointment_id(Context context, String appointment_id) {

        SharedPreferences pe = context.getSharedPreferences("appointment",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pe.edit();
        editor.putString("appointment_id",appointment_id);
        editor.apply();
        editor.commit();
    }

    public static String getAppointment_id(Context ctxt) {

        SharedPreferences sharedPref = ctxt.getSharedPreferences("appointment",Context.MODE_PRIVATE);
        String aid = sharedPref.getString("appointment_id","0");
        return aid;
    }

    public static void setLoginStatus(Context context, String login) {

        SharedPreferences pe = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pe.edit();
        editor.putString("loginstatus",login);
        editor.apply();
        editor.commit();
    }

    public static String getLoginStatus(Context ctxt) {

        SharedPreferences sharedPref = ctxt.getSharedPreferences("login",Context.MODE_PRIVATE);
        String aid = sharedPref.getString("loginstatus","logout");
        return aid;
    }

    public static void setPatient_id(Context context, int patient_id) {

        SharedPreferences pe = context.getSharedPreferences("patient",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pe.edit();
        editor.putInt("patient_id",patient_id);
        editor.apply();
        editor.commit();
    }

    public static int getPatient_id(Context ctxt) {

        SharedPreferences pe = ctxt.getSharedPreferences("patient",Context.MODE_PRIVATE);
        int aid = pe.getInt("patient_id",0);
        return aid;
    }

    public static void setMember(Context context,String yes) {

        SharedPreferences pe = context.getSharedPreferences("patient_member",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pe.edit();
        editor.putString("member",yes);
        editor.apply();
        editor.commit();
    }

    public static String getMember(Context ctxt) {

        SharedPreferences pe = ctxt.getSharedPreferences("patient_member",Context.MODE_PRIVATE);
        String aid = pe.getString("member","no");
        return aid;
    }

    public static void setToken(Context ctxt, String refreshedToken) {
        SharedPreferences pe = ctxt.getSharedPreferences("Firebase_Token",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pe.edit();
        editor.putString("tokan",refreshedToken);
        editor.apply();
        editor.commit();
    }

    public static String getToken(Context ctxt)
    {
        SharedPreferences pe = ctxt.getSharedPreferences("Firebase_Token",Context.MODE_PRIVATE);
        String token = pe.getString("tokan","0");
        return token;
    }

    public static boolean isFirstTimeUser(Context ctxt) {

        SharedPreferences pe = ctxt.getSharedPreferences("welcomUser",Context.MODE_PRIVATE);
        boolean id = pe.getBoolean("welcom",true);
        return id;
    }

    public static void setFirstTimeUser(Context context, boolean id) {

        SharedPreferences pe = context.getSharedPreferences("welcomUser",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pe.edit();
        editor.putBoolean("welcome",id);
        editor.apply();
        editor.commit();
    }


}
