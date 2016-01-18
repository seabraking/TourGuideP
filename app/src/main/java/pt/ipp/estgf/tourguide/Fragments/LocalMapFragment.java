package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 14/01/2016.
 */
public class LocalMapFragment extends SupportMapFragment {

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
        int idLocal = x.getInt("idLocal");
        GestorLocaisInteresse managmentLocal = new GestorLocaisInteresse();
        Local localMap = managmentLocal.getLocalById(idLocal, getContext());


        if (localMap == null) {
            Toast toast = Toast.makeText(getContext(), "Impossivel obter mapa", Toast.LENGTH_SHORT);
            toast.show();
        } else {


            LatLng latLng = new LatLng(Double.parseDouble(localMap.getCoordenadas().getLatitude().toString()),
                    Double.parseDouble(localMap.getCoordenadas().getLongitude().toString()));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(localMap.getNome());
            markerOptions.snippet(
                            "Categoria: " + localMap.getCategoria() +
                            "\n\n" + "Descrição: " + localMap.getDescricao() +
                            "\n\n" + "Coordenadas" +
                            "\n" + "    Lat: " + localMap.getCoordenadas().getLatitude() +
                            "\n" + "    Lon: " + localMap.getCoordenadas().getLongitude() +
                            "\n\n" + "Rating: " + localMap.getRating()
            );
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    LinearLayout info = new LinearLayout(getContext());
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(getContext());
                    title.setTextColor(getResources().getColor(R.color.azul));
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(getContext());
                    snippet.setTextColor(getResources().getColor(R.color.preto));
                    snippet.setText(marker.getSnippet());
                    snippet.setTypeface(null, Typeface.BOLD);


                    info.addView(title);
                    info.addView(snippet);


                    return info;
                }
            });
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }
}