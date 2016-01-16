package pt.ipp.estgf.tourguide.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pt.ipp.estgf.tourguide.Fragments.ViewPagerAdapter;
import pt.ipp.estgf.tourguide.R;
import pt.ipp.estgf.tourguide.Classes.GPSTracker;


public class MainAtivity extends AppCompatActivity implements LocationListener{




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ativity);




        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
         getSupportActionBar().setSubtitle(R.string.app_subname);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(
                new ViewPagerAdapter(getSupportFragmentManager(), this));

        // if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));


        checkGPS();



    }

        public String ConvertPointToLocation(String Latitude, String Longitude) {
            String address = "";
            Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocation(
                        Float.parseFloat(Latitude), Float.parseFloat(Longitude), 1);

                if (addresses.size() > 0) {
                    for (int index = 0; index < addresses.get(0)
                            .getMaxAddressLineIndex(); index++)
                        address += addresses.get(0).getAddressLine(index) + " ";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return address;
        }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_ativity, menu);


        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent newPrefMenu = new Intent(this, SettingsActivity.class);
                this.startActivity(newPrefMenu);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        checkGPS();
      //  Toast.makeText(getApplicationContext(),"LocationChanged",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        checkGPS();
     //   Toast.makeText(getApplicationContext(),"StatusChanged",Toast.LENGTH_SHORT).show();

    }

    public void checkGPS(){
        //LocalActual
        TextView localActual = (TextView)findViewById(R.id.localActual);
        //GPS
        GPSTracker gpsTracker = new GPSTracker(MainAtivity.this, this);
        String stringLatitude = "", stringLongitude = "", nameOfLocation="";

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());
            if(isNetworkAvailable()) {
                nameOfLocation = ConvertPointToLocation(stringLatitude, stringLongitude);
            } else {
                nameOfLocation = stringLatitude + ";" + stringLongitude;
            }
            localActual.setText(nameOfLocation);
        } else {
            localActual.setText("GPS está desativado!");
            gpsTracker.showSettingsAlert();
        }


    }

    @Override
    public void onProviderEnabled(String provider) {
        TextView local = (TextView)findViewById(R.id.localActual);
        local.setText("Gps Ativado");
        Toast.makeText(getApplicationContext(),"ProviderEnabled",Toast.LENGTH_SHORT).show();
        checkGPS();


    }

    @Override
    public void onProviderDisabled(String provider) {
        TextView local = (TextView)findViewById(R.id.localActual);
        local.setText("Gps Desativado");
        Toast.makeText(getApplicationContext(),"Desativado",Toast.LENGTH_SHORT).show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        //LocalActual
        TextView localActual = (TextView)findViewById(R.id.localActual);
        //GPS
        GPSTracker gpsTracker = new GPSTracker(MainAtivity.this, this);
        String stringLatitude = "", stringLongitude = "", nameOfLocation="";

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());
            if(isNetworkAvailable()) {
                nameOfLocation = ConvertPointToLocation(stringLatitude, stringLongitude);
            } else {
                nameOfLocation = stringLatitude + ";" + stringLongitude;
            }
            localActual.setText(nameOfLocation);
        } else {
            localActual.setText("Sinal GPS Impreciso, aguarde!");
        }
        super.onResume();
    }
}
