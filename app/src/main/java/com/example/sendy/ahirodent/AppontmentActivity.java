package com.example.sendy.ahirodent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.id.message;

public class AppontmentActivity extends AppCompatActivity {

    private static final String TAG = "AppointmentActivity";
    private FloatingActionButton btnSubmit;
    private EditText etEmail;
    private EditText etNumber;
    private RadioButton rbOld;
    private RadioButton rbNew;
    private int caseType;
    private EditText etName;
    private TextView etTime;
    private TextView etDate;
    private Spinner Gender;
    private List<AppointmentPojo> appointmentlist;
    private String massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_appontment);

        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Appointment");



        btnSubmit = (FloatingActionButton)findViewById(R.id.btnSubmit);
        etEmail =(EditText)findViewById(R.id.et_email);
        etName =(EditText)findViewById(R.id.et_name);
        etNumber = (EditText)findViewById(R.id.et_number);
        rbOld = (RadioButton)findViewById(R.id.rb_old);
        rbNew = (RadioButton)findViewById(R.id.rb_new);
        etDate = (TextView)findViewById(R.id.et_date);
        etTime = (TextView)findViewById(R.id.et_time);
        Gender = (Spinner)findViewById(R.id.spinner);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        if((M.getAppointment_id(this) != 0))
//        {
//            getAppointmentData(M.getAppointment_id(this));
//        }

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AppontmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate=Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(AppontmentActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */


                    int a = selectedmonth+1;
                        etDate.setText(selectedday+"/"+a+"/"+selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }

        });

        rbOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbNew.setChecked(false);
                caseType = 0;
            }
        });


        rbNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbOld.setChecked(false);
                caseType = 1;
            }
        });




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    //validation email and number
                    if(!(etName.getText().toString().equals("")) && !(etNumber.getText().toString().equals("")) && !(etEmail.getText().toString().equals(""))
                            && !(etDate.getText().toString().equals("")) && !(etTime.getText().toString().equals("")))
                    {

                        if (isValidEmail11(etEmail.getText().toString()))
                        {
                            if(isValidNumber(etNumber.getText().toString())) {

                                if (hasActiveInternetConnection()) {

                                    insertAppointments(caseType,etName.getText().toString(),
                                            etNumber.getText().toString(),
                                            etEmail.getText().toString(),
                                            etDate.getText().toString(),
                                            etTime.getText().toString(),
                                            Gender.getSelectedItem().toString());

                                    massage = "Name :" + etName.getText().toString() + System.getProperty("line.separator") +
                                            "Date :" + etDate.getText().toString();

                                }
                                else
                                {
                                    new SweetAlertDialog(AppontmentActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                            else
                            {
                                etNumber.setError("Invalid Number..");
                            }

                        }
                        else
                        {
                            etEmail.setError("Invalid Email..");
                        }

                    }
                    else
                    {
                        Toast.makeText(AppontmentActivity.this,"fill all the field", Toast.LENGTH_LONG).show();
                    }






            }
        });

    }

    private void sendMessage(String appointMent, String massage) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter
        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.sendMessagee(appointMent, massage,"sandesh", new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Toast.makeText(AppontmentActivity.this, "successfully Broadcast Your Event", Toast.LENGTH_SHORT).show();
                Log.d("Event","successfully broadcast");
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(AppontmentActivity.this, "Can't Broadcast Your Event", Toast.LENGTH_SHORT).show();
                Log.d("Event","err >>>"+error.getMessage());
            }
        });

    }
//    private void getAppointmentData(int appointment_id) {
//
//        RestAdapter adapter = new RestAdapter.Builder()
//                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter
//
//
//        //Creating object for our interface
//        RegisterApi api = adapter.create(RegisterApi.class);
//
//        //Defining the method insertuser of our interface
//        api.getSelectedAppointment(appointment_id, new Callback<List<AppointmentPojo>>() {
//
//            @Override
//            public void success(List<AppointmentPojo> appointmentPojo, Response response) {
//
//                appointmentlist = new ArrayList<>();
//                appointmentlist = appointmentPojo;
//
//                etName.setText(appointmentlist.get(0).getPatient_name());
//                etNumber.setText(appointmentlist.get(0).getPatient_number());
//                etEmail.setText(appointmentlist.get(0).getPatient_email());
//                etDate.setText(appointmentlist.get(0).getAppointment_date());
//                etTime.setText(appointmentlist.get(0).getAppointment_time());
//
//                //Displaying the output as a toast
//                Toast.makeText(AppontmentActivity.this,"Successfully add your Appointment", Toast.LENGTH_LONG).show();
//                Log.i("Appointment success >>>","");
//            }
//
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.i("Appointments error >>>>",error.getMessage());
//
//            }
//        });
//
//    }

    private void insertAppointments(int case_type, String patient_name, String patient_number, String patient_email, String appointment_date, String appointment_time, String gender)
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com").build();
                //.setEndpoint("http://10.0.2.2/ahirodent").build();

        RegisterApi api = adapter.create(RegisterApi.class);

        int patient_id = M.getPatient_id(AppontmentActivity.this);

        api.insertAppointments(patient_id,case_type, patient_name, patient_number, patient_email, appointment_date, appointment_time, gender,"pending", new Callback<List<AppointmentPojo>>() {
            @Override
            public void success(List<AppointmentPojo> appointmentPojos, Response response) {
                M.setAppointment_id(AppontmentActivity.this, appointmentPojos.get(0).getAppointment_id());

                sendMessage("AppointMent",massage);
                new SweetAlertDialog(AppontmentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("Your Appointment has been confirmed")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
                Log.d("Appointment success >>>","");

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Appointments error >>>>",error.getMessage());
                new SweetAlertDialog(AppontmentActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show();
            }
        });

    }

    private boolean isValidNumber(String s) {
       if (s != null && s.length()== 10)
        return true;
        else {
           return  false;
       }
    }

    private boolean isValidEmail11(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
