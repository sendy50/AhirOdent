package com.example.sendy.ahirodent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendy.ahirodent.Adapter.TAAdapter;
import com.example.sendy.ahirodent.Model.AppointmentPojo;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UpcomingFragment extends Fragment {


    private ArrayList<AppointmentPojo> appointmentList;
    private TAAdapter tadapter;
    private RecyclerView recyclerView;

    public static UpcomingFragment newInstance(ArrayList<AppointmentPojo> appointmentList) {
        UpcomingFragment fragment = new UpcomingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listdata", appointmentList);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.upcoming_activity, container, false);
        // Inflate the layout for this fragment


        recyclerView = (RecyclerView) view.findViewById(R.id.upcoming_appointment_recycleview);
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
            else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    Date date = sdf.parse(appointmentList.get(i).getAppointment_date());

                    DateTime day = new DateTime(date.getTime());
                    if (isTodayBefore(day)) {
                        finalList.add(appointmentList.get(i));
                    } else {
                        Log.d("NNNTAAA", "no before today");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();

                }
            }
        }



        tadapter = new TAAdapter(finalList);
        recyclerView.setAdapter(tadapter);

        return view;
    }

    private boolean isTodayBefore(DateTime day) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime inputDay = day.withTimeAtStartOfDay();
        return today.isBefore(inputDay);
    }

}