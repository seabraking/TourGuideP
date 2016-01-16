package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pt.ipp.estgf.tourguide.Activities.InformacaoLocal;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.R;


public class ObterCoordenadasFragment extends SupportMapFragment {

    GoogleMap mMap = getMap();
    Bundle x;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = getArguments();
    }


    @Override
    public void onStart() {
        super.onStart();
        mMap = super.getMap();
        int idLocal = x.getInt("idLocal");
        GestorLocaisInteresse managmentLocal = new GestorLocaisInteresse();
        Local localMap = managmentLocal.getLocalById(idLocal, getContext());


        if (localMap == null) {
            Toast toast = Toast.makeText(getContext(), "Impossivel obter mapa", Toast.LENGTH_SHORT);
            toast.show();
        } else {



            LatLng latLng=new LatLng(Double.parseDouble(localMap.getCoordenadas().getLatitude().toString()),
                    Double.parseDouble(localMap.getCoordenadas().getLongitude().toString()));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(localMap.getNome());
            markerOptions.snippet("Categoria: " + localMap.getCategoria() + "\nDescrição: \n" + localMap.getDescricao() +
                    "Rating: \n" + localMap.getRating() + "Coordenadas: \n Lat: " + localMap.getCoordenadas().getLatitude() +
                    "\n Lon: " + localMap.getCoordenadas().getLongitude());
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }


    }


}