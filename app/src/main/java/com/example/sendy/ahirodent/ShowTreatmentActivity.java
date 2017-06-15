package com.example.sendy.ahirodent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class ShowTreatmentActivity extends AppCompatActivity {

    //private static final double PIC_WIDTH = ;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_treatment);

        WebView myWebView = (WebView) findViewById(R.id.webview1);

        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);


        changeStatusBarColor();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarrrr2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Treatment");

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

       // Toast.makeText(this, b.getString("link1"), Toast.LENGTH_SHORT).show();
        myWebView.loadUrl(b.getString("link1"));
        myWebView.requestFocus();


    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }
}
