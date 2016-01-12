package pt.ipp.estgf.tourguide.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pt.ipp.estgf.tourguide.Fragments.FragmentMap;
import pt.ipp.estgf.tourguide.R;


import android.content.Intent;
import android.os.Bundle;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Fragments.FragmentMap;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 11/12/2015.
 */
public class RotaMapa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rota_mapa);

        FragmentMap newFragment = new FragmentMap();
        Bundle args = new Bundle();
        Intent intent = getIntent();
        int strID = intent.getExtras().getInt("LocaisRota");
        args.putInt("idRota",strID);
        newFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.idMapa, newFragment);
        ft.commit();
    }

}

