package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 10/12/2015.
 */
public class FragmentMap  extends SupportMapFragment{

    private GoogleMap mMap;
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
        int idRota = x.getInt("idRota");
        GestorRotas managmentRotas = new GestorRotas();
        ArrayList<Local> localArrayList = managmentRotas.listarLocaisRota(idRota, getContext());

        if (localArrayList == null) {
            Toast toast = Toast.makeText(getContext(), "NAO EXISTEM LOCAIS NESTA ROTA", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            PolylineOptions lineOptions = new PolylineOptions();


            for (int i = 0; i < localArrayList.size(); i++) {

                Local local = localArrayList.get(i);
                LatLng latLng=new LatLng(Double.parseDouble(local.getCoordenadas().getLatitude().toString()),
                        Double.parseDouble(local.getCoordenadas().getLongitude().toString()));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(local.getNome());
                markerOptions.snippet("Ordem na Rota: " + (i + 1) + "\nCategoria: " + local.getCategoria() + "\nDescrição: \n" + local.getDescricao() +
                        "Rating: \n" + local.getRating() + "Coordenadas: \n Lat: " + local.getCoordenadas().getLatitude() +
                        "\n Lon: " + local.getCoordenadas().getLongitude());
                lineOptions.add(latLng);
                mMap.addMarker(markerOptions).setAnchor(0.5f,1);





            }
            Polyline polyline = mMap.addPolyline(lineOptions);


        }
    }


}
