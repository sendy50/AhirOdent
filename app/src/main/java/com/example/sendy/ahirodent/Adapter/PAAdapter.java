package com.example.sendy.ahirodent.Adapter;

/**
 * Created by Sendy on 24-Oct-16.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Helps.RecyclerItemClick;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.R;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PAAdapter extends RecyclerView.Adapter<PAAdapter.MyViewHolder>
{

    private SweetAlertDialog pDialog;
    private Context mActivity;
    private List<AppointmentPojo> appointmentList;
    private SweetAlertDialog ppDialog;



    public PAAdapter(Context mActivity, List<AppointmentPojo> appointmentList)
    {

        this.mActivity = mActivity;
        this.appointmentList = appointmentList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public LinearLayout ll;
        public ImageView icon;
        private TextView name,date_time,number,email;


        private MyViewHolder(View itemView)
        {
            super(itemView);
            ll = (LinearLayout)itemView.findViewById(R.id.apolll);
            name = (TextView)itemView.findViewById(R.id.textView6);
            date_time = (TextView)itemView.findViewById(R.id.textView7);
            number = (TextView)itemView.findViewById(R.id.textView8);
            email = (TextView)itemView.findViewById(R.id.textView9);
            icon = (ImageView) itemView.findViewById(R.id.eeei3);
//            icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("scsdvdvf--------","");
//                    ppDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.CUSTOM_IMAGE_TYPE).setTitleText("Are you sure?")
//                            .setContentText("Wonn be Approve this Appointment?")
//                            .setConfirmText("Yes,Approve it!")
//                            .setCustomImage(R.drawable.approved)
//                            .setCancelText("Cancel");
//
//                    ppDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                            ppDialog.dismiss();
//                            Log.d("abc","cde");
//                            update(Integer.valueOf(appointmentList.get(getAdapterPosition()).getAppointment_id()));
//                        }
//
//                    });
//                }
//            });


            //dialog
            pDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);

        }


}





    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType == 0) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.appointment_appr, parent, false));

        }
        else {

            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.appointment_appr2, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AppointmentPojo item = appointmentList.get(position);

        holder.name.setText(item.getPatient_name());
        holder.date_time.setText(item.getAppointment_date());
        holder.number.setText(item.getPatient_number());
        holder.email.setText(item.getPatient_email());

//        holder.ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("",""+view.getTag());
//            }
//        });

    }


    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }


    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
