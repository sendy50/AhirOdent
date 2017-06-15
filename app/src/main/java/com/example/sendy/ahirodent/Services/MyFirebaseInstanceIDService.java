package com.example.sendy.ahirodent.Services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Config;
import android.util.Log;
import android.widget.Toast;

import com.example.sendy.ahirodent.Model.M;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Cybertron on 13-Mar-17.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        registerToken(refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: >>>>>>>" + refreshedToken);


    }

    private void registerToken(final String refreshedToken) {


        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter
        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.insertFcmToken("",refreshedToken, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                M.setToken(getApplication(),refreshedToken);
                Log.d(TAG,"token Register Successfully....");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"token Register Fail....");
            }
        });
    }

    private void sendRegistrationToServer(String token) {

        Log.e(TAG, "sendRegistrationToServer: " + token);
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
