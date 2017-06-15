package com.example.sendy.ahirodent;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sendy.ahirodent.Adapter.AAdapter;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.joda.time.DateTime;

import java.io.IOException;
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

public class DrActivity extends AppCompatActivity {

    private CircularImageView DrProfile;
    private CircularImageView DrAppointments;
    private CircularImageView DrMembers;
    private CircularImageView DrAllPatients;
    private TextView event;
    private LinearLayout L1;
    private LinearLayout L2;
    private LinearLayout L3;
    private LinearLayout fragment;
    private CircularProgressBar PendingProgress;
    private CircularProgressBar TodayProgress;
    private CircularProgressBar UpcomingProgress;
    private SweetAlertDialog pDialog;
    private List<AppointmentPojo> appointmentList;
    private ArrayList<AppointmentPojo> lisst;
    private int count=0;
    private int count2=0;
    private int count3=0;
    private TextView tvv2;
    private TextView tvv1;
    private TextView tvv3;
    private UpcomingFragment ufrag;
    private TodayFragment tfrag;
    private PandingFragment pfrag;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_dr);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        changeStatusBarColor();
        //dialog
        pDialog = new SweetAlertDialog(DrActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        if (hasActiveInternetConnection()) {
            getTodaysAppointment();
        }
        else {
            new SweetAlertDialog(DrActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops... No Internet Connection")
                    .setContentText("No internet connection on your device. Would you like to enable it?")
                    .setConfirmText("Enable Internet")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(dialogIntent);
                            DrActivity.super.onCreate(savedInstanceState);
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


        DrProfile = (CircularImageView)findViewById(R.id.iv_dr_profile);
        DrAppointments = (CircularImageView)findViewById(R.id.iv_dr_appointment);
        DrMembers = (CircularImageView)findViewById(R.id.iv_dr_members);
        DrAllPatients = (CircularImageView)findViewById(R.id.iv_dr_all_patient);
        event = (TextView)findViewById(R.id.event);
        L1 = (LinearLayout)findViewById(R.id.ll1);
        L2 = (LinearLayout)findViewById(R.id.ll2);
        L3 = (LinearLayout)findViewById(R.id.ll3);
        fragment = (LinearLayout)findViewById(R.id.fragmentll);
        PendingProgress = (CircularProgressBar)findViewById(R.id.circularProgressBar3);
        TodayProgress = (CircularProgressBar)findViewById(R.id.circularProgressBar2);
        UpcomingProgress = (CircularProgressBar)findViewById(R.id.circularProgressBar1);

        tvv2 =(TextView)findViewById(R.id.tvv2);
        tvv1 =(TextView)findViewById(R.id.tvv1);
        tvv3 =(TextView)findViewById(R.id.tvv3);




//        int animationDuration = 2500; // 2500ms = 2,5s
//        UpcomingProgress.setProgressWithAnimation(65, animationDuration);

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrActivity.this,CreateEventActivity.class);
                startActivity(intent);
            }
        });



        DrProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrActivity.this,DrProfileActivity.class);
                startActivity(intent);
            }
        });

        DrAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if((new ConnectionDetector(DrActivity.this)).isConnectingToInternet()){
//                    showSnack(true);
                    Intent intent = new Intent(DrActivity.this,ShowAppointmentActivity.class);
                    startActivity(intent);
//                    Log.d("internet status","Internet Access");
//                }else{
//                    showSnack(false);
//                    Log.d("internet status","no Internet Access");
//                }

            }
        });

        DrMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//               if((new ConnectionDetector(DrActivity.this)).isConnectingToInternet()){
//                    showSnack(true);
                    Intent intent = new Intent(DrActivity.this,ShowMemberActivity.class);
                    startActivity(intent);
//                    Log.d("internet status","Internet Access");
//                }else{
//                    showSnack(false);
//                    Log.d("internet status","no Internet Access");
//                }

            }
        });

        DrAllPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if((new ConnectionDetector(DrActivity.this)).isConnectingToInternet()){
//                    showSnack(true);
                    Intent intent = new Intent(DrActivity.this,ShowAllPatientsActivity.class);
                    startActivity(intent);

//                    Log.d("internet status","Internet Access");
//                }else{
//                    showSnack(false);
//                    Log.d("internet status","no Internet Access");
//                }
            }
        });

        //
        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ufrag = new UpcomingFragment().newInstance(lisst);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentll, ufrag, "closepfrag").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("u").commit();
                getSupportFragmentManager().beginTransaction().remove(tfrag).commit();
                getSupportFragmentManager().beginTransaction().remove(pfrag).commit();

            }
        });

        L2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                tfrag = new TodayFragment().newInstance(lisst);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentll, tfrag, "closepfrag").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("t").commit();

               getSupportFragmentManager().beginTransaction().remove(ufrag).commit();
               getSupportFragmentManager().beginTransaction().remove(pfrag).commit();

            }
        });

        L3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pfrag = new PandingFragment().newInstance(lisst);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentll, pfrag,"closepfrag").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("p").commit();
                getSupportFragmentManager().beginTransaction().remove(ufrag).commit();
                getSupportFragmentManager().beginTransaction().remove(tfrag).commit();



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

    private void getTodaysAppointment() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter

//                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter
        pDialog.show();


        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.getAllAppointments(new Callback<List<AppointmentPojo>>() {
            public AAdapter tadapter;

            @Override
            public void success(List<AppointmentPojo> appointmentPojos, Response response) {




                    appointmentList = new ArrayList<>();
                    appointmentList = appointmentPojos;
                    lisst = new ArrayList<>(appointmentPojos);

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

                    for (int i = 0; i < appointmentList.size(); i++) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        String dd = appointmentList.get(i).getAppointment_date();
                        Date date = null;

                        try {

                            if (appointmentList.get(i).getStatus().equals("pending")) {
                                count3 = count3 + 1;
                            } else {
                                date = sdf.parse(dd);
                                DateTime day = new DateTime(date.getTime());
                                if (isToday(day)) {
                                    count = count + 1;

                                } else if (isTodayBefore(day)) {
                                    count2 = count2 + 1;
                                    Log.d("dr", "istodayb4");
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                    String abc = "" + appointmentList.size();
                    int sum1 = count * 100 / appointmentList.size();
                    tvv2.setText(count + "/" + abc);//today
                    int sum2 = count2 * 100 / appointmentList.size();
                    tvv1.setText(count2 + "/" + abc);//upcoming
                    int sum3 = count3 * 100 / appointmentList.size();
                    tvv3.setText(count3 + "/" + abc);//pending

                    int animationDuration = 2500; // 2500ms = 2,5s
                    TodayProgress.setProgressWithAnimation(sum1, animationDuration);
                    PendingProgress.setProgressWithAnimation(sum3, animationDuration);
                    UpcomingProgress.setProgressWithAnimation(sum2, animationDuration);

                }

                @Override
                public void failure (RetrofitError error){

                    pDialog.dismiss();

                    Log.d("AppointmentList err>>>>", error.getMessage());

                    new SweetAlertDialog(DrActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    //finish();
                                }
                            })
                            .show();

                    finish();
                    Intent i = new Intent(DrActivity.this, DrActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0,0);
                    startActivity(i);
                    //finish();
                }

        });



    }

    private boolean isTodayBefore(DateTime day) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime inputDay = day.withTimeAtStartOfDay();
        return today.isBefore(inputDay);
    }

    private static boolean isToday(DateTime day) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isEqual(today);
    }

    @Override
    public void onBackPressed()
    {

        //final TodayFragment fragment = (TodayFragment) getSupportFragmentManager().findFragmentByTag("closepfrag");
        //if (fragment.allowBackPressed()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
            super.onBackPressed();
        //}




    }

    public static boolean hasActiveInternetConnection()
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
