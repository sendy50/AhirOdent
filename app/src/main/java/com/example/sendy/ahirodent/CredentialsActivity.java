package com.example.sendy.ahirodent;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

public class CredentialsActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }



        setContentView(R.layout.activity_credentials);

        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Credentials");

        BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi1));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi2));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi3));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi4));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi5));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi6));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.certi7));

        bannerSlider.setDefaultIndicator(IndicatorShape.ROUND_SQUARE);


        }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }

}

