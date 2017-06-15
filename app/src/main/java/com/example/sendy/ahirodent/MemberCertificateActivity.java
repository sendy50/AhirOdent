package com.example.sendy.ahirodent;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.sendy.ahirodent.Model.M;

public class MemberCertificateActivity extends AppCompatActivity {

    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_certificate);

        changeStatusBarColor();
        tvName = (TextView)findViewById(R.id.tvcerti);

        tvName.setText(M.getLoginData(MemberCertificateActivity.this,"name"));
        Log.d("certificate_________",M.getLoginData(MemberCertificateActivity.this,"name"));
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }
}
