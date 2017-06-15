package com.example.sendy.ahirodent;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.sendy.ahirodent.Adapter.EAdapter;
import com.example.sendy.ahirodent.Model.EventPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout ll;
    private List<EventPojo> eventList;
    private EAdapter eadapter;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_event);

        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Event");

        recyclerView = (RecyclerView)findViewById(R.id.event_recycleview);
        ll = (LinearLayout) findViewById(R.id.llll);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EventActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);


        //get data from dtabase n set to recyclerview
        getEventList();

    }

    private void getEventList() {
        pDialog.show();

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build();

        RegisterApi api = adapter.create(RegisterApi.class);

        api.getAllEvents(new Callback<List<EventPojo>>() {


            @Override
            public void success(List<EventPojo> eventPojos, Response response) {

                eventList = new ArrayList<>();
                eventList = eventPojos;



                eadapter = new EAdapter(EventActivity.this, eventList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(EventActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(eadapter);
                pDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {


                Log.d("Event","err >>>"+error.getMessage());
                pDialog.dismiss();

                new SweetAlertDialog(EventActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                EventActivity.super.onBackPressed();
                            }
                        })
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
