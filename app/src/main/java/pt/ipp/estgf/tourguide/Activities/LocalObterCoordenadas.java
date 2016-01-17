package pt.ipp.estgf.tourguide.Activities;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import pt.ipp.estgf.tourguide.Fragments.ObterCoordenadasFragment;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 16/01/2016.
 */
public class LocalObterCoordenadas extends FragmentActivity {
    public String lat;
    public String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota_mapa);





        ObterCoordenadasFragment newFragment = new ObterCoordenadasFragment();
        Bundle args = new Bundle();
        Intent intent = getIntent();
        int strID = intent.getExtras().getInt("idLocal");
        args.putInt("idLocal", strID);
        newFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.idMapa, newFragment);
        ft.commit();



        newFragment.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                lat = String.valueOf(latLng.latitude);
                lon = String.valueOf(latLng.longitude);
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent();
        i.putExtra("latitude", lat);
        i.putExtra("longitude",lon);
        setResult(RESULT_OK, i);
        super.onBackPressed();
    }

}
