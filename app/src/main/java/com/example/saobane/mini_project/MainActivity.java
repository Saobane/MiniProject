package com.example.saobane.mini_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fragments.AjoutTrajetFragment;
import fragments.EvalTrajetFragment;
import fragments.ProfilFragment;
import fragments.SearchCovoitFragment;
import fragments.StatsFragment;
import fragments.TrajetsFragment;

//ACTIVITE PRINCIPAL

import static com.example.saobane.mini_project.Utils.getImage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LocationListener{

    public final int REQUEST_CODE_READ_SMS = 200;
    public static  final int PERMISSION_GPS=100;
    private LocationManager lm;
    Double longitude,latitude;

public static int id_user;
    public static String identifiant, nom, prenom, score,scoreF, number,address;
    public static byte[] imageUri;
    ImageView im;
    TextView tvscore;
    private View navheader;
    Geocoder gc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        gc=new Geocoder(MainActivity.this, Locale.getDefault());

        UtilisateurDB udb = new UtilisateurDB(this);
        TrajetDB tdb= new TrajetDB(this);
        Intent intent = getIntent();
        nom = intent.getStringExtra("nom").toString();
        prenom = intent.getStringExtra("prenom").toString();
        score = intent.getStringExtra("score").toString();
        imageUri = intent.getByteArrayExtra("imageUri");


        id_user=intent.getIntExtra("id_user",0);


        //Set téléphone number

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    REQUEST_CODE_READ_SMS); // define this constant yourself
        } else {
            // you have the permission
        }

        number = tManager.getLine1Number();

//ville

                lm=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                      if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                      {
                             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_GPS);


                      }
                      else
                      {

                          lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,MainActivity.this);
                      }



        //Show toolbar


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:srcCompat="@android:drawable/ic_dialog_email" />






        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ///Naviguation header set photo
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navheader = navigationView.getHeaderView(0);
        im = (ImageView) navheader.findViewById(R.id.imageView);
        tvscore=(TextView)navheader.findViewById(R.id.tvScore);
        im.setImageBitmap(getImage(imageUri));

        ArrayList<String> nbrTrjAll=tdb.getAllTrajets();

        double scoreT=Double.parseDouble(score)/nbrTrjAll.size();
tvscore.setText(""+scoreT);
        scoreF=""+scoreT;
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
        public void  onRequestPermissionsResult(int requestCode, @NonNull String[] persmissions,@NonNull int[] grantResults){
                if(requestCode==PERMISSION_GPS){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,MainActivity.this);
            }
        else
            {
    Toast.makeText(MainActivity.this, "Permission refusé", Toast.LENGTH_SHORT).show();
            }
                }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_profil) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfilFragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_ajTrajet) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AjoutTrajetFragment()).commit();


        } else if (id == R.id.nav_trajets) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TrajetsFragment()).commit();



        } else if (id == R.id.nav_srchCovoit) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, new SearchCovoitFragment()).commit();


        } else if (id == R.id.nav_evalTrajet) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new EvalTrajetFragment()).commit();


        }else if (id == R.id.nav_stats) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new StatsFragment()).commit();


        }else if (id == R.id.nav_quitter) {
            finish();
            System.exit(0);


        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            latitude=location.getLatitude();
            longitude=location.getLongitude();

            getCity();


        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }




    public void getCity()  {

        List<Address> adrList;
        try {
            adrList=gc.getFromLocation(latitude,longitude,2);

            if(adrList!=null && adrList.size()>0){
                Address adr=adrList.get(0);
                String useradress=adr.getAddressLine(0),city=adr.getLocality();
                address=city;



            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
