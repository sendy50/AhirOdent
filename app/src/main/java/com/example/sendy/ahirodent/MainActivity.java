package com.example.sendy.ahirodent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.sendy.ahirodent.Helps.GifImageView;
import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.Services.RegisterApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


import gr.net.maroulis.library.EasySplashScreen;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseMessaging.getInstance().subscribeToTopic("Test");
//        FirebaseInstanceId.getInstance().getToken();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GifImageView iv = (GifImageView) findViewById(R.id.imageView);
        iv.setGifImageResource(R.drawable.animation);

//        iv = (ImageView)findViewById(R.id.imageView);
        iv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely));
        iv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alphaa);
        anim.reset();
        RelativeLayout l= (RelativeLayout) findViewById(R.id.layyyy);
        l.clearAnimation();
        l.startAnimation(anim);

//        View easySplashScreenView = new EasySplashScreen(MainActivity.this)
//                .withFullScreen()
//                .withTargetActivity(MainActivity.class)
//                .withSplashTimeOut(4000)
//                .withBackgroundResource(android.R.color.holo_red_light)
//                .withHeaderText("Header")
//                .withFooterText("Copyright 2016")
//                .withBeforeLogoText("My cool company")
//                .withLogo(R.drawable.logo)
//                .withAfterLogoText("Some more details")
//                .create();
//
//        setContentView(easySplashScreenView);


        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {

                String loginStatus = M.getLoginStatus(MainActivity.this);
                if(loginStatus.equals("patientlogin"))
                {
                    Intent intent=new Intent(MainActivity.this,PaatientActivity.class);
                    startActivity(intent);
                    Log.d("patient_id",">>>>"+M.getPatient_id(MainActivity.this));
                    finish();
                }
                else if(loginStatus.equals("drlogin"))
                {
                    String token = M.getToken(MainActivity.this);
                    RestAdapter adapter = new RestAdapter.Builder()
                            .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                            .build(); //Finally building the adapter
                    //Creating object for our interface
                    RegisterApi api = adapter.create(RegisterApi.class);

                    api.insertFcmTokendr("sandesh", token, new Callback<Response>() {
                        @Override
                        public void success(Response response, Response response2) {

                            Log.d("MainActivity","token Register Successfully....");
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Log.d("MainActivity","token Register Fail....");
                        }
                    });


                    Intent intent=new Intent(MainActivity.this,DrActivity.class);
                    startActivity(intent);
                    finish();
                }
                else  {

                    if(M.isFirstTimeUser(MainActivity.this))
                    {
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }

               /* Intent intent = new Intent(MainActivity.this, Login1.class);
                startActivity(intent);*/
            }
        }, SPLASH_TIME_OUT);

    }
}
