package com.example.sendy.ahirodent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sendy.ahirodent.Adapter.MAdapter;
import com.example.sendy.ahirodent.Helps.ConnectionDetector;
import com.example.sendy.ahirodent.Helps.GifImageView;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ShowMemberActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager ll;
    private List<PatientPojo> patientList;
    private MAdapter aadapter;
    private AlertDialog dialog;
    private GifImageView iv;
    private WebView view;
    private AlertDialog.Builder builder;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarrrr1);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Members Of ACFC");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        if (hasActiveInternetConnection()) {
            Log.d("internet status", "Internet Access");
            getMembers();
        } else {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops... No Internet Connection")
                    .setContentText("No internet connection on your device. Would you like to enable it?")
                    .setConfirmText("Enable Internet")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(dialogIntent);
                        }
                    })
                    .setConfirmText("Cancel")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    })
                    .show();


        }

        recyclerView = (RecyclerView) findViewById(R.id.member_recycleview);
        //ll = (LinearLayout)findViewById(R.id.lll);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        ll = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(ll);


        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }



    private void getMembers() {


        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter

//        .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter



        pDialog.show();
        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.getmemberPatient(new Callback<List<PatientPojo>>() {
            @Override
            public void success(List<PatientPojo> patientPojos, Response response) {

                patientList = new ArrayList<>();
                patientList = patientPojos;

                Collections.sort(patientList, new Comparator<PatientPojo>()
                {
                    @Override
                    public int compare(PatientPojo lhs, PatientPojo rhs) {
                        return lhs.getPatient_name().compareToIgnoreCase(rhs.getPatient_name());
                    }

                });

                Log.d("AppointmentList su>>>>",patientList.get(0).getPatient_name());
                aadapter = new MAdapter(patientList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ShowMemberActivity.this);
                //layoutManager.setReverseLayout(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(aadapter);
                pDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {

                pDialog.dismiss();
                new SweetAlertDialog(ShowMemberActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
            }
        });


    }



    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }

    public boolean hasActiveInternetConnection()
    {
        try
        {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(4000);
            urlc.setReadTimeout(4000);
            urlc.connect();
            int networkcode2 = urlc.getResponseCode();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e)
        {
            Log.i("warning", "Error checking internet connection", e);
            return false;
        }

    }


}
