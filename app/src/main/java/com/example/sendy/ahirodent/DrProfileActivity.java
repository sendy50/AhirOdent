package com.example.sendy.ahirodent;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrProfileActivity extends AppCompatActivity {
    private LinearLayout ocareLink;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_dr_profile);

        changeStatusBarColor();

        ocareLink = (LinearLayout)findViewById(R.id.ocarelink);
        tv1 = (TextView)findViewById(R.id.dr_profe_tv1);
        tv2 = (TextView)findViewById(R.id.dr_profe_tv2);

        ocareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.jio-ocare.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MECHANI_.ttf");

        tv1.setTypeface(custom_font);
        tv2.setTypeface(custom_font);


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }
}
