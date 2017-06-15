package com.example.sendy.ahirodent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Adapter.AAdapter;
import com.example.sendy.ahirodent.Adapter.PAdapter;
import com.example.sendy.ahirodent.Helps.RecyclerItemClick;
import com.example.sendy.ahirodent.Model.AppointmentPojo;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;
import com.google.android.gms.common.data.DataHolder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowAllPatientsActivity extends AppCompatActivity  {

    private List<PatientPojo> patientList;
    private RecyclerView recyclerView;
    LinearLayout ll;
    private PAdapter padapter;
    private List<AppointmentPojo> appointmentList;
    private TextView searchBtn;
    private RelativeLayout layout;
    private TextView cancleBtn;
    private EditText searchText;
    private EditText mSearchView;
    private DataHolder[] displayedList;
    private View searchContainer;
    private EditText toolbarSearchView;
    private ImageView searchClearButton;
    private String query;
    private String patientArray[];
    private MaterialSearchView searchView;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_patients);

        changeStatusBarColor();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarrrrr);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("All Patients");

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


        if (hasActiveInternetConnection()) {
            Log.d("internet status", "Internet Access");
            getPatient();
        } else {

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
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
                            finish();
                        }
                    })
                    .show();


        }



        recyclerView = (RecyclerView) findViewById(R.id.patient_recycleview);
        ll = (LinearLayout)findViewById(R.id.lll);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setFilterTouchesWhenObscured(true);
        recyclerView.setHasFixedSize(true);




        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setEllipsize(true);
        searchView.setSuggestions(patientArray);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                query = newText.toLowerCase();

                final List<PatientPojo> filteredList = new ArrayList<>();

                for (int i = 0; i < patientList.size(); i++) {

                    final String text = patientList.get(i).getPatient_name().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(patientList.get(i));
                    }
                    else
                    {
                        Toast.makeText(ShowAllPatientsActivity.this, "Nothing to Match", Toast.LENGTH_SHORT).show();
                    }
                }

                padapter = new PAdapter(ShowAllPatientsActivity.this,filteredList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ShowAllPatientsActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(padapter);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                searchView.setVisibility(View.GONE);
            }
        });





        //click event
        RecyclerItemClick.addTo(recyclerView).setOnItemClickListener(new RecyclerItemClick.OnItemClickListener() {
                 @Override
                 public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                     if (v.getId() == R.id.ll)
                     {
                         Log.d("ShowAppoi Activity"+v.getId(),"oncl");
                         // TODO Handle item click

//                        if(appointmentList.size() != 0)
//                        {
                         PatientPojo patientlist = patientList.get(position);

                         String ctype;
                         Intent intent=new Intent(ShowAllPatientsActivity.this,PatientProfileActivity.class);

                         if(patientlist.getCase_type() == 1) {
                             ctype = "( New Case )";
                         }
                         else {

                             ctype = "( Old Case )";
                         }
                         intent.putExtra("patient_id", patientlist.getPatient_id());
                         intent.putExtra("patient_name", patientlist.getPatient_name());
                         intent.putExtra("patient_number",patientlist.getPatient_number());
                         intent.putExtra("patient_email", patientlist.getPatient_email());
                         intent.putExtra("patient_gender", patientlist.getGender());
                         intent.putExtra("member", patientlist.getMember());
                         intent.putExtra("case_type", ctype);
                         intent.putExtra("position", position);


                         startActivity(intent);

                         //call
//                        if (true)
//                        {
//                            Log.d("ShowAppoi Activity","call");
//                            Intent intentt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + appointmentList.get(position).getPatient_number()));
//                            if (ActivityCompat.checkSelfPermission(ShowAppointmentActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
//                            startActivity(intentt);
//                        }


                     }

                 }
             });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        //searchView = (SearchView) item.getActionView();
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private void getPatient() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                .build();
//        .setEndpoint("http://ahirodent.16mb.com") //Setting the Root URL
//                .build(); //Finally building the adapter


        pDialog.show();
        //Creating object for our interface
        RegisterApi api = adapter.create(RegisterApi.class);

        api.getAllPatient(new Callback<List<PatientPojo>>() {
            @Override
            public void success(List<PatientPojo> patientPojos, Response response) {

                patientList = new ArrayList<>();
                patientList = patientPojos;

                if(!(patientPojos.size() == 0))
                {
                    patientArray = new String[patientPojos.size()];
                    for (int i = 0; i < patientPojos.size(); i++)
                    {
                        patientArray[i] = patientPojos.get(i).getPatient_name();
                    }
                }
                

                Collections.sort(patientList, new Comparator<PatientPojo>()
                {
                    @Override
                    public int compare(PatientPojo lhs, PatientPojo rhs) {
                        
                        return lhs.getPatient_name().compareToIgnoreCase(rhs.getPatient_name());
                    }

                });



                padapter = new PAdapter(ShowAllPatientsActivity.this,patientList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ShowAllPatientsActivity.this);
                //layoutManager.setReverseLayout(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(padapter);

                //seachView.setAdapter(patientPojos);
                pDialog.dismiss();

                Log.d("Show Patient su>>>>",patientList.get(0).getPatient_name());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Show Patient err>>>","fail");
                pDialog.dismiss();
                new SweetAlertDialog(ShowAllPatientsActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }

    public boolean hasActiveInternetConnection()
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
