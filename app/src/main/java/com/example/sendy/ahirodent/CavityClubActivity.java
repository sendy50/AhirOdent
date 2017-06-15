package com.example.sendy.ahirodent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CavityClubActivity extends AppCompatActivity {

    private TextView tvRateJd;
    private TextView tvRateFb;
    private TextView emo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cavity_club);

        tvRateJd = (TextView)findViewById(R.id.rate_jd);
        tvRateFb = (TextView)findViewById(R.id.rate_fb);
        emo = (TextView)findViewById(R.id.emogis);

        tvRateFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/ahirodent/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        tvRateJd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.justdial.com/Thane/Ahirodent-Complete-Oral-Care-Clinic-%3Cnear%3E-Opposite-SAI-Nagar-Ground-Vasai-Road-West/022PXX22-XX22-140412122911-L2C5_BZDET";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        int a = 0x0001F44D;
        String unicode = new String(Character.toChars(a));
        //emo.setText(R.string.clubstring2 + unicode);


    }
}
