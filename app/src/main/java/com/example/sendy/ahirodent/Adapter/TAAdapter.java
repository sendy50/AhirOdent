package com.example.sendy.ahirodent.Adapter;

/**
 * Created by Sendy on 24-Oct-16.
 */

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sendy.ahirodent.Helps.ColorDiagram;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.R;
import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TAAdapter extends RecyclerView.Adapter<TAAdapter.MyViewHolder>
{


    private List<AppointmentPojo> appointmentList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private final LinearLayout ll;
        public ImageView icon;
        public TextView name,date_time,time,number,email;
        private MyViewHolder(View itemView)
        {
            super(itemView);
            ll = (LinearLayout)itemView.findViewById(R.id.apoll);
            name = (TextView)itemView.findViewById(R.id.textView6);
            date_time = (TextView)itemView.findViewById(R.id.textView7);
            number = (TextView)itemView.findViewById(R.id.textView8);
            email = (TextView)itemView.findViewById(R.id.textView9);
            //iv = (CircularImageView) itemView.findViewById(R.id.a_list_pic);
            icon = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }

    public TAAdapter(List<AppointmentPojo> appointmentList)
    {

        this.appointmentList = appointmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        if(viewType == 0) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.appointment_new, parent, false));

        }
        else {

                return new MyViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.appointment_new2, parent, false));

        }


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AppointmentPojo item = appointmentList.get(position);

                holder.name.setText(item.getPatient_name());
                holder.date_time.setText(item.getAppointment_date());
                holder.number.setText(item.getPatient_number());
                holder.email.setText(item.getPatient_email());

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
