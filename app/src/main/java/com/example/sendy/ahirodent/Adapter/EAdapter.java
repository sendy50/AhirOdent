package com.example.sendy.ahirodent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sendy.ahirodent.CreateEventActivity;
import com.example.sendy.ahirodent.Helps.SimpleDividerItemDecoration;
import com.example.sendy.ahirodent.Model.EventPojo;
import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.R;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Cybertron on 11-Mar-17.
 */

public class EAdapter extends RecyclerView.Adapter<EAdapter.MyViewHolder> {


    private  SweetAlertDialog pDialog;
    private Context getContext;
    private List<EventPojo> eventList;
    private SweetAlertDialog ppDialoge;

    public EAdapter(Context context, List<EventPojo> eventList)
    {

        this.eventList = eventList;
        this.getContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private final TextView startdate;
        private final TextView lastdate;
        private final LinearLayout Llevent;
        public TextView title;
        public TextView message;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.event_ttl);
            message = (TextView) itemView.findViewById(R.id.event_msg);
            startdate = (TextView) itemView.findViewById(R.id.event_startdate);
            lastdate = (TextView) itemView.findViewById(R.id.event_lastdate);
            Llevent = (LinearLayout) itemView.findViewById(R.id.llevent);
            //dialog
            pDialog = new SweetAlertDialog(getContext, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);

            if(M.getLoginStatus(getContext).equals("drlogin"))
            Llevent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Event--->","clickedddd");
                    delete(getAdapterPosition());
                }
            });

        }
    }

    private void delete(final int position) {

        {


            ppDialoge = new SweetAlertDialog(getContext, SweetAlertDialog.WARNING_TYPE);
            ppDialoge.setTitleText("Are you sure?");
            ppDialoge.setContentText("Won't be able to delete this event!");
            ppDialoge.setConfirmText("Yes,delete it!");
            ppDialoge.setCancelText("Cancel");
            ppDialoge.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    final RestAdapter adapter = new RestAdapter.Builder()
                            .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                            .build(); //Finally building the adapter
                    //Creating object for our interface
                    RegisterApi api = adapter.create(RegisterApi.class);

                    api.deleteEvent(eventList.get(position).getEvent_title(), new Callback<Response>() {
                        public Intent intent;

                        @Override
                        public void success(Response response, Response response2) {

                            ppDialoge.setTitleText("Deleted!")
                                    .setContentText("Your event has been deleted!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            pDialog.dismiss();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                            intent = new Intent(getContext,CreateEventActivity.class);
                            getContext.startActivity(intent);

                            Log.d("","deleted event"+eventList.get(position).getEvent_title());
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            new SweetAlertDialog(getContext, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Delete event fail..")
                                    .show();
                            Log.d("","deleted  fail"+eventList.get(position).getEvent_title());
                        }
                    });
                }
            });
            ppDialoge.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ppDialoge.dismiss();
                }
            });
            ppDialoge.show();


        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_event_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        EventPojo item = eventList.get(position);
        holder.title.setText(item.getEvent_title());
        holder.message.setText(item.getEvent_message());
        holder.startdate.setText("Start Date :"+item.getEvent_startdate());
        holder.lastdate.setText("Last Date :"+item.getEvent_lastdate());


    }


    @Override
    public int getItemCount() {

        return eventList.size();
    }


}
