package com.example.sendy.ahirodent;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Adapter.AAdapter;
import com.example.sendy.ahirodent.Helps.GifImageView;
import com.example.sendy.ahirodent.Helps.ObjectWrapperForBinder;
import com.example.sendy.ahirodent.Helps.RecyclerItemClick;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowAppointmentActivity extends AppCompatActivity implements Serializable{

    private List<AppointmentPojo> appointmentList;
    private RecyclerView recyclerView;
    public LinearLayout ll;
    public ImageView call;
    private LinearLayout loadingLayout;
    private TextView loadigText;
    private GifImageView iv;
    private AnimationDrawable loadingViewAnim;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointment);
        changeStatusBarColor();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarrrr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Appointments");

        recyclerView = (RecyclerView) findViewById(R.id.appointment_recycleview);
        call = (ImageView)findViewById(R.id.a_list_call);

        ll = (LinearLayout) findViewById(R.id.ll);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //for Loading
        //dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);


        RecyclerItemClick.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClick.OnItemClickListener() {
                 @Override
                 public void onItemClicked(RecyclerView recyclerView, int position, View v) {

//                     if (v.getId() == call.getId())
//                     {
                         Log.d("ShowAppoi Activity"+v.getId(),"oncl");
                         // TODO Handle item click

                         AppointmentPojo patientlist = appointmentList.get(position);

                         //String ctype;
                         Intent intent=new Intent(ShowAppointmentActivity.this,PatientProfileActivity.class);

//                         if(patientlist.getCase_type().equals("1"))
//                         {
//                             ctype = "( New Case )";
//                         }
//                         else {
//
//                             ctype = "( Old Case )";
//                         }
//                         intent.putExtra("patient_id", patientlist.getPatient_id());
//                         intent.putExtra("patient_name", patientlist.getPatient_name());
//                         intent.putExtra("patient_number",patientlist.getPatient_number());
//                         intent.putExtra("patient_email", patientlist.getPatient_email());
//                         intent.putExtra("patient_gender", patientlist.getGender());
//                         intent.putExtra("case_type", ctype);


                             final Bundle bundle = new Bundle();
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                             bundle.putBinder("object_value", new ObjectWrapperForBinder(patientlist));

                         }

                         intent.putExtras(bundle);
                         intent.putExtra("position", position);


                         startActivity(intent);

                         //call
//                        if (true)
//                        {
//                            Log.d("ShowAppoi Activity","call");
//                            Intent intentt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + appointmentList.get(position).getPatient_number()));
//                            if (ActivityCompat.checkSelfPermission(ShowAppointmentActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
//                            startActivity(intentt);
//                        }
                   //  }

                 }
             });



        if(hasActiveInternetConnection())
        {
            Log.d("internet status","Internet Access");
            getAppointments();
        }
        else
        {

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


    }

    private void getAppointments() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter

//                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter



        pDialog.show();

        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.getAllAppointments(new Callback<List<AppointmentPojo>>() {
            AAdapter aadapter;

            @Override
            public void success(List<AppointmentPojo> appointmentPojos, Response response) {



                appointmentList = new ArrayList<>();
                appointmentList = appointmentPojos;

                Log.d("AppointmentList su>>>>",appointmentList.get(0).getPatient_name());
                LinearLayoutManager layoutManager = new LinearLayoutManager(ShowAppointmentActivity.this);
                //layoutManager.setReverseLayout(true);
                recyclerView.setLayoutManager(layoutManager);
                Collections.sort(appointmentList, new Comparator<AppointmentPojo>() {

                    @Override
                    public int compare(AppointmentPojo lhs, AppointmentPojo rhs) {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        int compareResult = 0;
                        try {
                            Date arg0Date = format.parse(lhs.getAppointment_date());
                            Date arg1Date = format.parse(rhs.getAppointment_date());
                            compareResult = arg0Date.compareTo(arg1Date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            compareResult = lhs.getAppointment_date().compareTo(rhs.getAppointment_date());
                        }
                        return compareResult;
                    }
                });
                pDialog.dismiss();

                Collections.reverse(appointmentList);
                aadapter = new AAdapter(ShowAppointmentActivity.this, appointmentList);
                recyclerView.setAdapter(aadapter);

            }

            @Override
            public void failure(RetrofitError error) {

                pDialog.dismiss();

                Log.d("AppointmentList err>>>>",error.getMessage());

                new SweetAlertDialog(ShowAppointmentActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        }).show();
                //finish();
            }
        });

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

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }
}
