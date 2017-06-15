package com.example.sendy.ahirodent;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

public class GalleryActivity extends AppCompatActivity {


    ImageView Img1,Img2 ,Img3,Img4,Img5,Img6,Img7,Img8,Img9,Img10;
    private BannerSlider bannerSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_gallery);
        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Gallery");
        //myToolbar.setTitle("Gallery");



        Img1 = (ImageView) findViewById(R.id.im1);
        Img2 = (ImageView) findViewById(R.id.im2);
        Img3 = (ImageView) findViewById(R.id.im3);
        Img4 = (ImageView) findViewById(R.id.im4);
        Img5 = (ImageView) findViewById(R.id.im5);
        Img6 = (ImageView) findViewById(R.id.im6);
        Img7 = (ImageView) findViewById(R.id.im7);
        Img8 = (ImageView) findViewById(R.id.im8);
        Img9 = (ImageView) findViewById(R.id.im9);
        Img10 = (ImageView) findViewById(R.id.im10);


        bannerSlider = (BannerSlider) findViewById(R.id.banner_slider3);

        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_one));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_two));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_three));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_four));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_five));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_six));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_seven));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_eight));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_nine));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallary_ten));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallery_elevn));
        bannerSlider.addBanner(new DrawableBanner(R.drawable.gallery_twelw));

        bannerSlider.setDefaultIndicator(IndicatorShape.ROUND_SQUARE);

        Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getSupportFragmentManager().beginTransaction().replace(R.id.framee,f).commit();
                setSlider(3);

            }
        });

        Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(2);


            }
        });

        Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(4);

            }
        });

        Img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(5);

            }
        });

        Img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(6);

            }
        });

        Img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(7);

            }
        });

        Img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(8);

            }
        });

        Img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(9);

            }
        });

        Img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(11);
            }
        });

        Img10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setSlider(12);

            }
        });


    }

    private void setSlider(int a) {

        bannerSlider.setVisibility(View.VISIBLE);
        bannerSlider.setCurrentSlide(a);
    }

    @Override
    public void onBackPressed() {
        if(bannerSlider.getVisibility() == View.VISIBLE)
        {
            bannerSlider.setVisibility(View.GONE);
        }
        else
        {
            super.onBackPressed();
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
