package com.example.sendy.ahirodent;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendy.ahirodent.Adapter.AAdapter;
import com.example.sendy.ahirodent.Helps.ObjectWrapperForBinder;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

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

import static com.example.sendy.ahirodent.R.id.member;

public class PatientProfileActivity extends AppCompatActivity {

    private static final String TAG = "PatientProfileActivity";
    private TextView name,name1,email,number,gender;
    private TextView Member;
    private TextView Ctype;
    private TextView dob;
    private int pid;
    private SweetAlertDialog pDialog;
    private ImageView call;
    private TextView apoDate;
    private TextView apoTime;
    private TextView email1;
    private AppointmentPojo list;
    private String pcaseType;
    private String datte;
    private String timme;
    private String pnumber;
    private PatientPojo listt;
    private String pname;
    private String pemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        changeStatusBarColor();
        name=(TextView)findViewById(R.id.patient_detail_name);
        name1=(TextView)findViewById(R.id.patient_detail_name1);
        email=(TextView)findViewById(R.id.patient_detail_email);
        email1=(TextView)findViewById(R.id.patient_detail_emaill);
        number=(TextView)findViewById(R.id.patient_detail_number);
        call=(ImageView)findViewById(R.id.call);
//        gender=(TextView)findViewById(R.id.patient_detail_gender);
//        dob=(TextView)findViewById(R.id.patient_detail_dob);

        //dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        apoDate = (TextView)findViewById(R.id.apo_date);
        apoTime = (TextView)findViewById(R.id.apo_time);

        Member=(TextView)findViewById(member);
        Ctype=(TextView)findViewById(R.id.patient_detail_casetype);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String loginStatus = M.getLoginStatus(PatientProfileActivity.this);
            if(loginStatus.equals("patientlogin"))
            {
                //patient pojo for patient click
                listt =((ObjectWrapperForBinder)getIntent().getExtras().getBinder("object_value")).getDdata();
                getAppointment(listt.getPatient_id());


            }
            else if(loginStatus.equals("drlogin")) {

                //Appointment pojo for dr click
                list =  ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("object_value")).getData();

                pname = list.getPatient_name();
                pnumber = list.getPatient_email();
                pemail = list.getPatient_email();
                if(list.getCase_type().equals("1"))
                {
                    pcaseType = "( New Case )";
                }
                else {

                    pcaseType = "( Old Case )";
                }
                pid = Integer.valueOf(list.getPatient_id());

                if(!(list.getPatient_name() == null))
                {
                    datte = list.getAppointment_date();
                    timme = list.getAppointment_time();

                }

        }


//        String pname = (String) list.get("patient_name");
//        final String pnumber = (String) b.get("patient_number");
//        String pemail = (String) b.get("patient_email");
//       // String pgender = (String) b.get("patient_gender");
//        String pcaseType = (String) b.get("case_type");
//        //String pdob = (String) b.get("patient_dob");
//        String member = (String) b.get("member");
//        pid = b.getInt("patient_id");
//       // pid = Integer.valueOf(ppid);
//        int position = Integer.parseInt(b.getString("position"));


        }







        getMemberStatus(pid);

        name.setText(pname);
        name1.setText(pname);
        email.setText(pemail);
        email1.setText(pemail);
        number.setText(pnumber);
       // gender.setText(pgender);
        Ctype.setText(pcaseType);
        //dob.setText(pdob);

        apoDate.setText(datte);
        apoTime.setText(timme);





        String loginStatus = M.getLoginStatus(PatientProfileActivity.this);
        if(loginStatus.equals("patientlogin"))
        {
           Member.setBackgroundColor(Color.parseColor("#CCFF0000"));
        }
        else if(loginStatus.equals("drlogin"))
        {

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + pnumber ));
                    if (ActivityCompat.checkSelfPermission(PatientProfileActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intentt);
                }
            });


            Member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String memberr;

                    //Dialoge
                    if(Member.getText().equals("You are not Member of AhirOdent"))
                    {
                        memberr = "yes";
                        Member.setText("Member");
                        updatePatient(memberr);
                    }
                    else
                    {
                        memberr = "no";
                        Member.setText("You are not Member of AhirOdent");
                        updatePatient(memberr);
                    }
                }

                private void updatePatient(String memberr) {

                    pDialog.show();
                    RestAdapter adapter = new RestAdapter.Builder()
                            .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                            .build(); //Finally building the adapter



                    //Creating object for our interface
                    RegisterApi api = adapter.create(RegisterApi.class);


                    //put patient_id
                    api.updatepatient(pid,memberr,new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {

                            Log.d(TAG,">>> member");
                            pDialog.dismiss();
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            pDialog.dismiss();

                            new SweetAlertDialog(PatientProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Something went wrong!")
                                    .show();
                            Log.d(TAG,">>>> fail");
                        }
                    });
                }
            });
        }

    }


    private void getMemberStatus(final int pid) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter

//                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter



        pDialog.show();

        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);
        api.getSelectedPatient(pid, new Callback<List<PatientPojo>>() {
            @Override
            public void success(List<PatientPojo> patientPojos, Response response) {

                if("yes".equals(patientPojos.get(0).getMember()))
                {
                    Member.setText("Member");
                }
                else
                {
                    Member.setText("You are not Member of AhirOdent");

                }

                pDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {

                pDialog.dismiss();
                Log.d("ppAct","getMember fail"+error.getMessage());
            }
        });


    }


    private void getAppointment(int pid) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter

//                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter



        pDialog.show();

        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.getSelectedAppointment(pid, new Callback<List<AppointmentPojo>>() {

            @Override
            public void success(List<AppointmentPojo> appointmentPojos, Response response) {

                if(!(appointmentPojos.size() == 0))
                {
                    pname = appointmentPojos.get(0).getPatient_name();
                    pnumber = appointmentPojos.get(0).getPatient_email();
                    pemail = appointmentPojos.get(0).getPatient_email();
                    if(appointmentPojos.get(0).getCase_type().equals("1"))
                    {
                        pcaseType = "( New Case )";
                    }
                    else {

                        pcaseType = "( Old Case )";
                    }

                    datte = appointmentPojos.get(0).getAppointment_date();
                    timme = appointmentPojos.get(0).getAppointment_time();

                }

                pDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {

                pDialog.dismiss();

                Log.d("AppointmentList err>>>>",error.getMessage());

                new SweetAlertDialog(PatientProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
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


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }


}
