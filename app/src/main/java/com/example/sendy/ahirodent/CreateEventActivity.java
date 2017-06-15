package com.example.sendy.ahirodent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Adapter.EAdapter;
import com.example.sendy.ahirodent.Helps.RecyclerItemClick;
import com.example.sendy.ahirodent.Helps.SimpleDividerItemDecoration;
import com.example.sendy.ahirodent.Model.EventPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.support.design.R.styleable.TabLayout;
import static com.example.sendy.ahirodent.R.id.ll;

public class CreateEventActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static EditText massage;
    private TabLayout tabLayout;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Generate Event");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private int mFragmentIndex;
        private RecyclerView recyclerView;
        private LinearLayout ll;
        private EditText title;

        private EditText lastDate;
        private Button CreateEventBtn;
        private List<EventPojo> eventList;
        public EAdapter eadapter;
        private SweetAlertDialog pDialog;
        private SweetAlertDialog ppDialoge;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (getArguments() != null) {
                mFragmentIndex = getArguments().getInt(ARG_SECTION_NUMBER);
            }
            View rootView;

            if (mFragmentIndex == 1) {
                rootView = inflater.inflate(R.layout.fragment_create_event, container, false);

                title = (EditText) rootView.findViewById(R.id.et_event_name);
                massage = (EditText) rootView.findViewById(R.id.et_event_msg);
                lastDate = (EditText) rootView.findViewById(R.id.et_date);
                CreateEventBtn = (Button) rootView.findViewById(R.id.create_event_btn);


                //dialog
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);




                lastDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentDate=Calendar.getInstance();
                        int mYear = mcurrentDate.get(Calendar.YEAR);
                        int mMonth=mcurrentDate.get(Calendar.MONTH);
                        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
                        {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                            {
                                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                                selectedmonth = selectedmonth+1;
                                lastDate.setText(selectedday+"/"+selectedmonth+"/"+selectedyear);
                            }
                        },mYear, mMonth, mDay);
                        mDatePicker.setTitle("Select date");
                        mDatePicker.show();
                    }
                });

                CreateEventBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!(title.getText().toString().equals("")))
                        {
                            if (!(massage.getText().toString().equals("")))
                            {
                                if(hasActiveInternetConnection())
                                {
                                    //send data to database
                                    String startdate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());

                                    insertEvent(title.getText().toString(),massage.getText().toString(),startdate,lastDate.getText().toString());


                                    sendMessage(title.getText().toString(),massage.getText().toString());

                                    title.setText("");
                                    massage.setText("");
                                    lastDate.setText("");


                                    getActivity().finish();
                                    Intent i = new Intent(getActivity(), CreateEventActivity.class);  //your class
                                    startActivity(i);

                                }
                                else
                                {

                                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops... No Internet Connection")
                                            .setContentText("No internet connection on your device. Would you like to enable it?")
                                            .setConfirmText("Enable Internet")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(dialogIntent);
                                                }
                                            })
                                            .setConfirmText("Cancel")
                                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    getActivity().finish();
                                                }
                                            })
                                            .show();


                                }




                                //eadapter.notifyDataSetChanged();

                            }
                            else {
                                Toast.makeText(getContext(), "Enter Message of Event", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Enter Title of Event", Toast.LENGTH_SHORT).show();
                        }
                    }


                });


            } else {
                rootView = inflater.inflate(R.layout.fragment_show_event, container, false);

                recyclerView = (RecyclerView) rootView.findViewById(R.id.event_recycleview);
                ll = (LinearLayout) rootView.findViewById(R.id.llll);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());


                //get data from dtabase n set to recyclerview
                getEventList();

            }

            return rootView;
        }



        private void sendMessage(String title,String message) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                    .build(); //Finally building the adapter
            //Creating object for our interface
            RegisterApi api = adapter.create(RegisterApi.class);

            api.sendMessage(title , message, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {


                    Log.d("Event","successfully broadcast");
                }
                @Override
                public void failure(RetrofitError error) {


                    Log.d("Event","err >>>"+error.getMessage());
                }
            });

        }

        //insert event

        private void insertEvent(final String title, String massage, String startdate, String lastDate) {

            pDialog.show();
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                    .build(); //Finally building the adapter
            //Creating object for our interface
            RegisterApi api = adapter.create(RegisterApi.class);

            api.insertEvent(title, massage, startdate, lastDate, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {

                   // Toast.makeText(getContext(), "successfully Broadcast Your Event", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Your Event is Broadcast Successfully")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    getActivity().finish();
                                }
                            })
                            .show();
                    Log.d("Event","successfully inserted");
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong!")
                            .show();
                    Log.d("Event","err >>>"+error.getMessage());
                }
            });
        }

        //get all event

        private void getEventList() {
            //dialog
            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);

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

                    if(eventList == null)
                    {
                        pDialog.dismiss();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Does not Avail Any Event!")
                                .show();
                    }
                    else
                    {
                        eadapter = new EAdapter(getContext(),eventList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                        recyclerView.setAdapter(eadapter);
                        pDialog.dismiss();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.dismiss();

                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong!")
                            .show();
                    Log.d("Event","err >>>"+error.getMessage());
                }
            });
        }


    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Create Event";
                case 1:
                    return "Show Event";
            }
            return null;
        }
    }

    public static boolean hasActiveInternetConnection()
    {
        try
        {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(4000);
            urlc.setReadTimeout(4000);
            urlc.connect();
            int networkcode2 = urlc.getResponseCode();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e)
        {
            Log.i("warning", "Error checking internet connection", e);
            return false;
        }

    }



}
