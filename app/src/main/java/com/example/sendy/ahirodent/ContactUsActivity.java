package com.example.sendy.ahirodent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactUsActivity extends AppCompatActivity {

    private TextView tvCall;
    private LinearLayout tvmap;
    private ImageView fbIcon;
    private ImageView jdIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_contact_us);

        changeStatusBarColor();
        tvCall = (TextView)findViewById(R.id.callintocontactus);
        tvmap = (LinearLayout)findViewById(R.id.mapll);
        fbIcon = (ImageView)findViewById(R.id.fb_icon);
        jdIcon = (ImageView)findViewById(R.id.justdial_icon);

        tvmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String geoUri = "https://www.google.co.in/maps/place/AHIRODENT+Complete+Oral+Care+Clinic/@19.3816818,72.8248648,17z/data=!3m1!4b1!4m5!3m4!1s0x3be7aebf9b5cd927:0x153d706d344e1cb8!8m2!3d19.3816818!4d72.8270535";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);

            }
        });


        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9699664106" ));
                            if (ActivityCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(intentt);
            }
        });

        fbIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/ahirodent/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        jdIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.justdial.com/Thane/Ahirodent-Complete-Oral-Care-Clinic-%3Cnear%3E-Opposite-SAI-Nagar-Ground-Vasai-Road-West/022PXX22-XX22-140412122911-L2C5_BZDET";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
