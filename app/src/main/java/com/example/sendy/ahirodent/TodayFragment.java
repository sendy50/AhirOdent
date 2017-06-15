package com.example.sendy.ahirodent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sendy.ahirodent.Adapter.AAdapter;
import com.example.sendy.ahirodent.Adapter.TAAdapter;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TodayFragment extends Fragment {
    private RecyclerView recyclerView;
    //private List<AppointmentPojo> appointmentList;
    private SweetAlertDialog pDialog;
    private ArrayList<AppointmentPojo> appointmentList;
    private TAAdapter tadapter;

    public static TodayFragment newInstance(ArrayList<AppointmentPojo> appointmentList) {
        TodayFragment fragment = new TodayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listdata", appointmentList);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.today_activity, container, false);
        // Inflate the layout for this fragment



        //dialog
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);


        recyclerView = (RecyclerView) view.findViewById(R.id.today_appointment_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        appointmentList = (ArrayList<AppointmentPojo>) getArguments().getSerializable("listdata");

        ArrayList<AppointmentPojo> finalList = new ArrayList<>();


        for (int i = 0 ; i < appointmentList.size() ; i++)
        {
            if (appointmentList.get(i).getStatus().equals("pending"))
            {
                Log.d("today","Pending");
            }
            else
            {
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    Date date = sdf.parse(appointmentList.get(i).getAppointment_date());

                    DateTime day = new DateTime(date.getTime());
                    if(isToday(day))
                    {
                        finalList.add(appointmentList.get(i));
                    }
                    else
                    {
                        Log.d("NNNTAAA","no today");
                    }

                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }



        tadapter = new TAAdapter(finalList);
        recyclerView.setAdapter(tadapter);

        //getTodaysAppointment(appointmentList);

        return view;
    }

    private static boolean isToday(DateTime day) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isEqual(today);
    }

    public boolean allowBackPressed() {
        return true;
    }


    //private void getTodaysAppointment(ArrayList<AppointmentPojo> appointmentList) {

//        RestAdapter adapter = new RestAdapter.Builder()
//                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
//                .build(); //Finally building the adapter
//
////                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
////                .build(); //Finally building the adapter
//        pDialog.show();
//
//        //Creating object for our interface
//        RegisterApi api = adapter.create(RegisterApi.class);
//
//        api.getAllAppointments(new Callback<List<AppointmentPojo>>() {
//            public TAAdapter tadapter;
//
//            @Override
//            public void success(List<AppointmentPojo> appointmentPojos, Response response) {
//
//
//
//                TodayFragment.this.appointmentList = new ArrayList<>();
//                TodayFragment.this.appointmentList = appointmentPojos;
//
//                Log.d("AppointList Today>>>>", TodayFragment.this.appointmentList.get(0).getPatient_name());
////                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
////                //layoutManager.setReverseLayout(true);
////                recyclerView.setLayoutManager(layoutManager);
//                Collections.sort(TodayFragment.this.appointmentList, new Comparator<AppointmentPojo>() {
//
//                    @Override
//                    public int compare(AppointmentPojo lhs, AppointmentPojo rhs) {
//                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//                        int compareResult = 0;
//                        try {
//                            Date arg0Date = format.parse(lhs.getAppointment_date());
//                            Date arg1Date = format.parse(rhs.getAppointment_date());
//                            compareResult = arg0Date.compareTo(arg1Date);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                            compareResult = lhs.getAppointment_date().compareTo(rhs.getAppointment_date());
//                        }
//                        return compareResult;
//                    }
//                });
//                pDialog.dismiss();
//
//                Collections.reverse(TodayFragment.this.appointmentList);
//                tadapter = new TAAdapter(TodayFragment.this.appointmentList);
//                recyclerView.setAdapter(tadapter);
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//                pDialog.dismiss();
//
//                Log.d("AppointmentList err>>>>",error.getMessage());
//
//                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Oops...")
//                        .setContentText("Something went wrong!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                //finish();
//                            }
//                        })
//                        .show();
//                //finish();
//            }
//        });
//


//        Collections.sort(TodayFragment.this.appointmentList, new Comparator<AppointmentPojo>() {
//
//                    @Override
//                    public int compare(AppointmentPojo lhs, AppointmentPojo rhs) {
//                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//                        int compareResult = 0;
//                        try {
//                            Date arg0Date = format.parse(lhs.getAppointment_date());
//                            Date arg1Date = format.parse(rhs.getAppointment_date());
//                            compareResult = arg0Date.compareTo(arg1Date);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                            compareResult = lhs.getAppointment_date().compareTo(rhs.getAppointment_date());
//                        }
//                        return compareResult;
//                    }
//                });
//
//        Collections.reverse(TodayFragment.this.appointmentList);
//        tadapter = new TAAdapter(appointmentList);
//        recyclerView.setAdapter(tadapter);
   // }
}