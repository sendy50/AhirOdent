package com.example.sendy.ahirodent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.ahirodent.Helps.ObjectWrapperForBinder;
import com.example.sendy.ahirodent.Model.M;
import com.example.sendy.ahirodent.Model.PatientPojo;
import com.example.sendy.ahirodent.Services.RegisterApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularimageview.CircularImageView;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaatientActivity extends AppCompatActivity {

    private static final String TAG = "PaatientActivity";
    private CircularImageView ivAppointment;
    private CircularImageView ivDrProfile;
    private CircularImageView ivGallery;
    private CircularImageView ivContactUs;
    private CircularImageView ivLocation;
    private CircularImageView ivTreatment;
    private CircularImageView ivWayUs;
    private CircularImageView ivCertificate;
    private CircularImageView ivShareUs;
    private CircularImageView ivSymptoms;
    private TextView tvCavityClub;
    private LinearLayout ll;
    private TextView tvwebsite;
    private List<PatientPojo> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.activity_paatient);

//








  //
        changeStatusBarColor();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);

        toolbar.inflateMenu(R.menu.patient);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        FirebaseMessaging.getInstance().subscribeToTopic("Test");
//        FirebaseInstanceId.getInstance().getToken();

        ivAppointment = (CircularImageView)findViewById(R.id.appointment);
        ivDrProfile = (CircularImageView)findViewById(R.id.dr_profile);
        ivGallery = (CircularImageView)findViewById(R.id.gallery);
        ivContactUs = (CircularImageView)findViewById(R.id.contact_us);
        ivTreatment = (CircularImageView)findViewById(R.id.treatment);
        ivWayUs = (CircularImageView)findViewById(R.id.wayus);
        ivCertificate = (CircularImageView)findViewById(R.id.certificates);
        ivShareUs = (CircularImageView)findViewById(R.id.shareus);
        ivSymptoms = (CircularImageView)findViewById(R.id.symptoms);
        tvCavityClub = (TextView)findViewById(R.id.cavityclub);
        tvwebsite = (TextView)findViewById(R.id.website);
        ll = (LinearLayout)findViewById(R.id.clubll);

        ll.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivWayUs.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivShareUs.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivAppointment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivCertificate.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivContactUs.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivDrProfile.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivGallery.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivSymptoms.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        ivTreatment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));


        Log.d("PaatientActivity su>>>>",""+M.getPatient_id(PaatientActivity.this));


        String mem = M.getMember(PaatientActivity.this);
        if(mem.equals("yes"))
        {
            tvCavityClub.setText("You Are Member Of AhirOdent CAVITY FREE CLUB");
        }
        else
        {
            tvCavityClub.setText("AhirOdent's CAVITY FREE CLUB");
        }



        tvwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.ahirodent.webs.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ivCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaatientActivity.this,CredentialsActivity.class);
                startActivity(i);
            }
        });

        ivWayUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaatientActivity.this,WayUsActivity.class);
                startActivity(i);
            }
        });

        ivAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaatientActivity.this,AppontmentActivity.class);
                startActivity(i);
            }
        });


        ivDrProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaatientActivity.this,DrProfileActivity.class);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                startActivity(i);


            }
        });

        ivTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaatientActivity.this,TreatmentActivity.class);
                startActivity(i);
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PaatientActivity.this,GalleryActivity.class);
                startActivity(i);

            }

        });

        ivContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PaatientActivity.this,ContactUsActivity.class);
                startActivity(i);

            }

        });

        tvCavityClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(M.getMember(PaatientActivity.this).equals("yes"))
                {
                    Intent intent = new Intent(PaatientActivity.this,MemberCertificateActivity.class);
                    startActivity(intent);
                    Log.d("PaatientActivity su>>>>","member");
                }
                else
                {
                    showDialoge();
                }
            }
        });

        ivSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaatientActivity.this,SymptomsActivity.class);
                startActivity(i);

            }

        });

        ivShareUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pm=getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "Download AHIRODENT APP an ===> https://drive.google.com/open?id=0Bz8nnjlcUSb5c2Nacm1xc2pOQ3M";

                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(PaatientActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:

                Log.d("PaatientActivity su>>>>",""+M.getPatient_id(PaatientActivity.this));

                RestAdapter adapter = new RestAdapter.Builder()
                        .setEndpoint("https://ahirodentt.000webhostapp.com") //Setting the Root URL
                        .build(); //Finally building the adapter


                final ProgressDialog loading = ProgressDialog.show(this,"","Please wait...");
                //Creating object for our interface
                RegisterApi api = adapter.create(RegisterApi.class);

                api.getSelectedPatient(M.getPatient_id(PaatientActivity.this), new Callback<List<PatientPojo>>() {
                    public PatientPojo patientList1;

                    @Override
                    public void success(List<PatientPojo> patientPojos, Response response) {
                        loading.dismiss();

                        if (patientPojos != null && !patientPojos.isEmpty()) {

                            Log.d(TAG,"success>>>>"+patientPojos);
                            patientList1 = patientPojos.get(0);
                            String ctype;
                            Intent intent=new Intent(PaatientActivity.this,PatientProfileActivity.class);
                            final Bundle bundle = new Bundle();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                bundle.putBinder("object_value", new ObjectWrapperForBinder(patientList1));

                            }

                            intent.putExtras(bundle);

                            startActivity(intent);
                        }
                        else
                        {
                            Log.d(TAG,"fail>>>");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        loading.dismiss();
                        Log.d(TAG,"err>>>"+error.getMessage());
                    }
                });

                return true;
            case R.id.devloper_profile:

                Intent ii = new Intent(PaatientActivity.this,SendyActivity.class);
                startActivity(ii);
                return true;

            case R.id.menu_event:

                Intent iii = new Intent(PaatientActivity.this,EventActivity.class);
                startActivity(iii);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialoge() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PaatientActivity.this);

        builder.setPositiveButton("Join Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(PaatientActivity.this,CavityClubActivity.class);
                startActivity(i);

            }
        }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(PaatientActivity.this,CavityClubActivity.class);
                startActivity(i);
            }
        });


        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.go_pro_dialog_layout, null);
        dialog.setView(dialogLayout);
        dialog.show();

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.theme_primary));
        }
    }


}
