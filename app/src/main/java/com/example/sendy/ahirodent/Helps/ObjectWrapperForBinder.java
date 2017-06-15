package com.example.sendy.ahirodent.Helps;

import android.os.Binder;

import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Model.PatientPojo;

/**
 * Created by Cybertron on 05-Jun-17.
 */

public class ObjectWrapperForBinder extends Binder {

    private AppointmentPojo mData;


    public ObjectWrapperForBinder(AppointmentPojo data)
    {
        mData = data;
    }

    public AppointmentPojo getData() {
        return mData;
    }

    private PatientPojo mmData;


    public ObjectWrapperForBinder(PatientPojo data)
    {
        mmData = data;
    }

    public PatientPojo getDdata() {
        return mmData;
    }
}
