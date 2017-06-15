package com.example.sendy.ahirodent.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendy.ahirodent.Helps.ColorDiagram;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.R;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import java.util.List;

/**
 * Created by Sendy on 25-Oct-16.
 */
public class MAdapter extends RecyclerView.Adapter<MAdapter.MyViewHolder>
{

    private List<PatientPojo> patientlist;
    ColorDiagram colorr = new ColorDiagram();

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name;
        public RoundedLetterView iv;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.p_list_name);
            iv = (RoundedLetterView) itemView.findViewById(R.id.p_list_pic);


        }
    }

    public MAdapter(List<PatientPojo> appointmentList)
    {

        this.patientlist = appointmentList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PatientPojo item = patientlist.get(position);
        holder.name.setText(item.getPatient_name());

        //get first letter of each String item
        String firstLetter = String.valueOf(item.getPatient_name().charAt(0));

        holder.iv.setTitleText(firstLetter.toUpperCase());
//        Drawable myIcon = mContext.getResources().getDrawable( R.drawable.background );
//        holder.iv.setBackground(myIcon);
        holder.iv.setBackgroundColor(colorr.getColor());

    }


    @Override
    public int getItemCount() {
        return patientlist.size();
    }
}
