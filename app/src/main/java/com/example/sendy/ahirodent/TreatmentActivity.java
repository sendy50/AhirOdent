package com.example.sendy.ahirodent;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class TreatmentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
    private TextView t13, t14,t15, t16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);


        changeStatusBarColor();
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Treatment");

        t1 = (TextView)findViewById(R.id.t1);
        t2 = (TextView)findViewById(R.id.t2);
        t3 = (TextView)findViewById(R.id.t3);
        t4 = (TextView)findViewById(R.id.t4);
        t5 = (TextView)findViewById(R.id.t5);
        t6 = (TextView)findViewById(R.id.t6);
        t7 = (TextView)findViewById(R.id.t7);
        t8 = (TextView)findViewById(R.id.t8);
        t9 = (TextView)findViewById(R.id.t9);
        t10 = (TextView)findViewById(R.id.t10);
        t11 = (TextView)findViewById(R.id.t11);
        t12= (TextView)findViewById(R.id.t12);
        t13= (TextView)findViewById(R.id.t13);
        t14= (TextView)findViewById(R.id.t14);
        t15= (TextView)findViewById(R.id.t15);
        t16= (TextView)findViewById(R.id.t16);



        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        t9.setOnClickListener(this);
        t10.setOnClickListener(this);
        t11.setOnClickListener(this);
        t12.setOnClickListener(this);
        t13.setOnClickListener(this);
        t14.setOnClickListener(this);
        t15.setOnClickListener(this);
        t16.setOnClickListener(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/MontereyFLF-Bold.ttf");

        t1.setTypeface(custom_font);
        t2.setTypeface(custom_font);
        t3.setTypeface(custom_font);
        t4.setTypeface(custom_font);
        t5.setTypeface(custom_font);
        t6.setTypeface(custom_font);
        t7.setTypeface(custom_font);
        t8.setTypeface(custom_font);
        t9.setTypeface(custom_font);
        t10.setTypeface(custom_font);
        t11.setTypeface(custom_font);
        t12.setTypeface(custom_font);
        t13.setTypeface(custom_font);
        t14.setTypeface(custom_font);
        t15.setTypeface(custom_font);
        t16.setTypeface(custom_font);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.t1) {

            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment1.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t2) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment1.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t3) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment9.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t4) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment2.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t5) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment3.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t6) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment4.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t7) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment1.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t8) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment5.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t9) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment1.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t10) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment1.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t11) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment1.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t12) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment6.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t13) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment7.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t14) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment8.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t15) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment6.html");
            i.putExtras(b);
            startActivity(i);

        }
        else if (id == R.id.t16) {
            Intent i = new Intent(TreatmentActivity.this,ShowTreatmentActivity.class);
            Bundle b = new Bundle();
            b.putString("link1","https://ahirodentt.000webhostapp.com/treatment6.html");
            i.putExtras(b);
            startActivity(i);

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
