package com.example.sendy.ahirodent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Button btnSubmit;
    private EditText etEmail;
    private EditText etName;
    private EditText etNumber;
    private TextView etdob;
    private Spinner Gender;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myToolbar.setTitle("Register Here");


        btnSubmit = (Button)findViewById(R.id.btnrSubmit);
        etEmail =(EditText)findViewById(R.id.etr_email);
        etPassword =(EditText)findViewById(R.id.etr_password);
        etName =(EditText)findViewById(R.id.etr_name);
        etNumber = (EditText)findViewById(R.id.etr_number);
        etdob = (TextView)findViewById(R.id.etr_date);
        Gender = (Spinner)findViewById(R.id.rspinner);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(etName.getText().toString().equals("")) && !(etNumber.getText().toString().equals("")) && !(etEmail.getText().toString().equals(""))
                        && !(etdob.getText().toString().equals(""))  && !(etPassword.getText().toString().equals("")))
                {

                    if (isValidEmail11(etEmail.getText().toString()))
                    {
                        if(isValidNumber(etNumber.getText().toString())) {

                            if (hasActiveInternetConnection()) {

                                insertUsers("123",etName.getText().toString(),
                                        etNumber.getText().toString(),
                                        etEmail.getText().toString(),
                                        etdob.getText().toString(),
                                        Gender.getSelectedItem().toString(),
                                        etPassword.getText().toString());
                            }
                            else
                            {
                                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toast.makeText(RegisterActivity.this,"fill all the field", Toast.LENGTH_LONG).show();
                }

            }
        });


        etdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate=Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                    {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */

                        int a =selectedday+1;
                        etdob.setText(a+"/"+selectedmonth+"/"+selectedyear);
                        etdob.setTextColor(Color.BLACK);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }

        });


    }

    private void insertUsers(String ss,String s, String s1, String s2, String s3, String s4, String password) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter
        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.insertUser(ss,null,s,s1, s2, s3, s4,password, new Callback<List<PatientPojo>>() {

            @Override
            public void success(List<PatientPojo> patientPojos, Response response) {

                if(patientPojos != null && !patientPojos.isEmpty() )
                {
                    Log.d(TAG,"id >>>"+patientPojos.get(0).getPatient_id());
                    M.setPatient_id(RegisterActivity.this,patientPojos.get(0).getPatient_id());
                    M.setLoginStatus(RegisterActivity.this,"patientlogin");

                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("You Register Successfully")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(RegisterActivity.this,PaatientActivity.class);
                                    startActivity(intent);
                                    LoginActivity.fa.finish();
                                    finish();
                                }
                            })
                            .show();
                }
                else
                {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong!")
                            .show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"err >>>"+error.getMessage());

                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show();
            }
        });

    }

    private boolean isValidNumber(String s) {
        if(s!=null && s.length()== 10)
            return true;
        else
            return false;
    }

    private boolean isValidEmail11(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
