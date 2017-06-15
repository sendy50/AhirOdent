package com.example.sendy.ahirodent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendy.ahirodent.Helps.ColorDiagram;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.R;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import java.util.List;

/**
 * Created by Sendy on 25-Oct-16.
 */

public class PAdapter extends RecyclerView.Adapter<PAdapter.MyViewHolder> {



    private List<PatientPojo> patientlist;

    ColorDiagram colorr = new ColorDiagram();
    public Context mContext;



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

    public PAdapter(Context context ,List<PatientPojo> appointmentList)
    {

        this.patientlist = appointmentList;
        mContext = context;

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
//        ColorGeneratorr generator = ColorGeneratorr.MATERIAL; // or use DEFAULT
//        // generate random color
//       // int color = generator.getColor(patientlist.get(position));
//        //int color = generator.getRandomColor();
////generator.getRandomColor()
//
//        TextDrawable drawable = TextDrawable.builder()
//                .buildRound(firstLetter,0xffff0000 ); // radius in px
//
//        holder.iv.setImageDrawable(drawable);

    }


    @Override
    public int getItemCount() {
        return patientlist.size();
    }




}