package com.example.sendy.ahirodent.Adapter;

/**
 * Created by Sendy on 24-Oct-16.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Helps.ColorDiagram;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.R;
import com.example.sendy.ahirodent.ShowAppointmentActivity;
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


public class AAdapter extends RecyclerView.Adapter<AAdapter.MyViewHolder>
{

    private Context mContext;
    private List<AppointmentPojo> appointmentList;
    ColorDiagram colorr = new ColorDiagram();

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public ImageView call;
        public TextView name,date,time,today;
        public RoundedLetterView iv;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.a_list_name);
            date = (TextView)itemView.findViewById(R.id.a_list_date);
            time = (TextView)itemView.findViewById(R.id.a_list_time);
            today = (TextView)itemView.findViewById(R.id.today);
            //iv = (CircularImageView) itemView.findViewById(R.id.a_list_pic);
            call = (ImageView) itemView.findViewById(R.id.a_list_call);
            iv = (RoundedLetterView) itemView.findViewById(R.id.a_list_pic);

            call.setOnClickListener(this);
            iv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view.getId() == call.getId())
            {
                Log.d("ShowAppoi Activity","call");
                            Intent intentt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + appointmentList.get(getAdapterPosition()).getPatient_number()));
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            mContext.startActivity(intentt);
            }
        }
    }

    public AAdapter(ShowAppointmentActivity showAppointmentActivity, List<AppointmentPojo> appointmentList)
    {

        this.mContext = showAppointmentActivity;
        this.appointmentList = appointmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AppointmentPojo item = appointmentList.get(position);
        holder.name.setText(item.getPatient_name());
        holder.date.setText(item.getAppointment_date());
        holder.time.setText(item.getAppointment_time());

        try {
            setToday(holder,item.getAppointment_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //get first letter of each String item
        String firstLetter = String.valueOf(item.getPatient_name().charAt(0));

        holder.iv.setTitleText(firstLetter.toUpperCase());
        holder.iv.setBackgroundColor(colorr.getColor());
    }

    private void setToday(MyViewHolder holder, String appointment_date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = sdf.parse(appointment_date);

        DateTime day = new DateTime(date.getTime());

        if(isToday(day))
        {
            holder.today.setText("Today");
        }
        else if(dayIsYesterday(day))
        {

            holder.today.setText("Yesterday");
        }
       else if(dayIsTomorrow(day))
        {

            holder.today.setText("Tomorrow");
        }
        else if(IsNextMonth(day))
        {
            holder.today.setText("Next Month");
        }
        else if(IsLastMonth(day))
        {
            holder.today.setText("Last Month");
        }
       else if(IsLastWeek(day))
        {

            holder.today.setText("Last week");
        }
       else if(IsNextWeek(day))
        {

            holder.today.setText("Next week");
        }
        else
        {
            holder.today.setText("Recent");
        }
    }

    private boolean IsNextMonth(DateTime day) {
        DateTime nextweek = new DateTime().withTimeAtStartOfDay().plusMonths(1);
        DateTime nextweek2 = new DateTime().withTimeAtStartOfDay().plusMonths(2);
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isAfter(nextweek) && inputDay.isBefore(nextweek2);
    }

    private boolean IsLastMonth(DateTime day) {
        DateTime lastweek = new DateTime().withTimeAtStartOfDay().minusMonths(1);
        DateTime lastweek2 = new DateTime().withTimeAtStartOfDay().minusMonths(2);
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isBefore(lastweek) && inputDay.isAfter(lastweek2);
    }

    private boolean IsLastWeek(DateTime day) {
        DateTime lastweek = new DateTime().withTimeAtStartOfDay().minusWeeks(1);
        DateTime lastweek2 = new DateTime().withTimeAtStartOfDay().minusWeeks(2);
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isBefore(lastweek) && inputDay.isAfter(lastweek2);
    }

    private boolean IsNextWeek(DateTime day) {
        DateTime nextweek = new DateTime().withTimeAtStartOfDay().plusWeeks(1);
        DateTime nextweek2 = new DateTime().withTimeAtStartOfDay().plusWeeks(2);
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isAfter(nextweek) && inputDay.isBefore(nextweek2);
    }

    private static boolean dayIsYesterday(DateTime day) {
        DateTime yesterday = new DateTime().withTimeAtStartOfDay().minusDays(1);
        System.out.print(yesterday);
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isEqual(yesterday);
    }

    private static boolean dayIsTomorrow(DateTime day) {
        DateTime tomorrow = new DateTime().withTimeAtStartOfDay().plusDays(1);
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isEqual(tomorrow);
    }

    private static boolean isToday(DateTime day) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime inputDay = day.withTimeAtStartOfDay();
        return inputDay.isEqual(today);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
