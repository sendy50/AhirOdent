package com.example.sendy.ahirodent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.password;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView info;
    private String patient_email;
    private TextView tvsignUp;
    private Button loginbtn;
    private TextView tvUsername;
    private TextView tvPassword;
    public static Activity fa;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.demo);

        changeStatusBarColor();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginbtn = (Button) findViewById(R.id.btnLogin);
        tvsignUp = (TextView) findViewById(R.id.tvSignup);
        tvUsername = (TextView) findViewById(R.id.etUsername);
        tvPassword = (TextView) findViewById(R.id.etPassword);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        info = (TextView) findViewById(R.id.info);


        fa=this;

        //dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
//
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity>>>>>>", response.toString());

                                        // Application code
                                        try {
                                            patient_email = object.getString("email"); // 01/31/1980 format
                                            String patient_name = object.getString("name");
                                            String fb_id = object.getString("id");
                                            String dob = object.getString("birthday");
                                            String gender = object.getString("gender");
                                            M.setLoginData(LoginActivity.this, patient_email, patient_name, dob, fb_id);

                                            insertPatientData(fb_id, patient_name, object.getString("email"), dob, gender, "123");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,picture");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "fb cancle>>>>");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, error.getMessage() + "fb >>>>>");
                    }

                });


        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String patient_email = tvUsername.getText().toString();
                final String password = tvPassword.getText().toString();

                if (!(patient_email.equals(""))) {
                    if (!(password.equals(""))) {

                        RestAdapter adapter = new RestAdapter.Builder()
                                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                                .build(); //Finally building the adapter
                        //Creating object for our interface
                        //dialog
                        pDialog.show();
                        RegisterApi api = adapter.create(RegisterApi.class);

                        api.CheckLogin(patient_email, password, new Callback<List<PatientPojo>>() {
                            @Override
                            public void success(List<PatientPojo> patientPojos, Response response) {

                                if (patientPojos != null && !patientPojos.isEmpty()) {
                                    Log.d(TAG, "id >>>" + patientPojos.get(0).getPatient_id());
                                    M.setPatient_id(LoginActivity.this, patientPojos.get(0).getPatient_id());

                                    if (!(M.getPatient_id(LoginActivity.this) == 0)) {

                                        if(patientPojos.get(0).getPatient_email().equals(patient_email) && patientPojos.get(0).getPassword().equals(password))
                                        {
                                            if(patientPojos.get(0).getPatient_email().equals("ahir.sandesh@gmail.com"))
                                            {
                                                pDialog.dismiss();
                                                M.setLoginStatus(LoginActivity.this,"drlogin");
                                                Intent iintent = new Intent(LoginActivity.this,DrActivity.class);
                                                startActivity(iintent);
                                                finish();
                                            }
                                            else
                                            {
                                                pDialog.dismiss();
                                                M.setLoginStatus(LoginActivity.this,"patientlogin");
                                                Intent intent = new Intent(LoginActivity.this,PaatientActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                        }
                                        else
                                        {
                                            pDialog.dismiss();
                                            tvUsername.setError("Your Email May be Wrong");
                                            tvPassword.setError("Your Password May be Wrong");
                                            Toast.makeText(LoginActivity.this, "Email & Password Are Wrong", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else
                                    {
                                        pDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Please Input Valid email and password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    pDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                pDialog.dismiss();
                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Something went wrong!")
                                        .show();
                                Log.d(TAG, "err >>>" + error.getMessage());
                            }
                        });

                    } else {
                        tvPassword.setError("Enter password");
                    }
                } else {
                    tvUsername.setError("enter valid email");
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK) {
//            M.setLoginStatus(LoginActivity.this, "patientlogin");
//            Intent intent = new Intent(LoginActivity.this, PaatientActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }

    private void insertPatientData(String id, String name, String email, String dob, String gender, String number)
    {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build(); //Finally building the adapter
        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.insertUser(id,null, name,number, email, dob, gender,number, new Callback<List<PatientPojo>>() {
            @Override
            public void success(List<PatientPojo> patientPojos, Response response) {

                if(patientPojos != null && !patientPojos.isEmpty() )
                {
                    Log.d(TAG,"id >>>"+patientPojos.get(0).getPatient_id());
                    M.setPatient_id(LoginActivity.this,patientPojos.get(0).getPatient_id());
                    Log.d(TAG,"fb insert >>>"+"success");

                    if(patientPojos.get(0).getPatient_email().equals("ahir.sandesh@gmail.com"))
                    {
                        M.setLoginStatus(LoginActivity.this,"drlogin");
                        Intent iintent = new Intent(LoginActivity.this,DrActivity.class);
                        startActivity(iintent);
                        finish();
                    }
                    else
                    {
                        M.setLoginStatus(LoginActivity.this,"patientlogin");
                        Intent intent = new Intent(LoginActivity.this,PaatientActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                else
                {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong!")
                            .show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.d(TAG,"fb insert err >>>"+error.getMessage());
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
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


}
