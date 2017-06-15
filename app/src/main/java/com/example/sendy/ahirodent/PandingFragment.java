package com.example.sendy.ahirodent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendy.ahirodent.Adapter.PAAdapter;
import com.example.sendy.ahirodent.Helps.RecyclerItemClick;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PandingFragment extends Fragment {


    private ArrayList<AppointmentPojo> appointmentList;
    private RecyclerView recyclerView;
    private PAAdapter padapter;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog ppDialog;
    Context mActivity;
    private TextView txt;
    ArrayList<AppointmentPojo> pendingList = null;
    private ImageView icon;


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//            this.mActivity = (mActivity) context;
//            setRetainInstance(true);
//    }

    public static PandingFragment newInstance(ArrayList<AppointmentPojo> appointmentList) {
        PandingFragment fragment = new PandingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listdata", appointmentList);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

        View view = inflater.inflate(R.layout.panding_activity, container, false);
        // Inflate the layout for this fragment

        this.mActivity = container.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.pending_appointment_recycleview);
        txt = (TextView) view.findViewById(R.id.txttvv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        icon = (ImageView) view.findViewById(R.id.eeei3);

        //dialog
        pDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);


        RecyclerItemClick.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClick.OnItemClickListener() {
                 @Override
                 public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                     Log.d("--->>","id is "+pendingList.get(position).getAppointment_id());

                     int id = Integer.valueOf(pendingList.get(position).getAppointment_id());
                     Log.d("--->>","id is "+id);
                     update(id);




                 }
             });

        appointmentList = (ArrayList<AppointmentPojo>) getArguments().getSerializable("listdata");

         pendingList = new ArrayList<>();
        //ApprovedList = new ArrayList<>();


        for (int i = 0 ; i < appointmentList.size() ; i++)
        {
           if (appointmentList.get(i).getStatus().equals("pending"))
           {
               pendingList.add(appointmentList.get(i));
           }
//           else
//           {
//               ApprovedList.add(appointmentList.get(i));
//           }
        }

        padapter = new PAAdapter(mActivity,pendingList);
        recyclerView.setAdapter(padapter);



        return view;
    }

    private void update(int appointment_id) {


//                .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter



        pDialog.show();
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                    .build(); //Finally building the adapter

            //Creating object for our interface
            RegisterApi api = adapter.create(RegisterApi.class);

            api.updateAppointments(appointment_id, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Log.d("pending","success approved");
                    pDialog.dismiss();
                    ppDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
                    ppDialog.setTitleText("Approved!")
                            .setContentText("Appointment has been Approved!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    ppDialog.dismiss();
                                    getActivity().finish();
                                    Intent i = new Intent(getActivity(), DrActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    getActivity().overridePendingTransition(0, 0);
                                    startActivity(i);
                                }
                            }).show();

                    ppDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();
                    Log.d("pending",""+error.getMessage());
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Approve Appointment fail..")
                            .show();

                }
            });


    }

}