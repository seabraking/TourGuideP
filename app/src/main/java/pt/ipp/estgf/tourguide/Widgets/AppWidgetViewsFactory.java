package pt.ipp.estgf.tourguide.Widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.GPSTracker;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Fragments.LocaisFragment;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 16/01/2016.
 */
public class AppWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory, LocationListener {
    private Context context = null;
    private int appWidgetId;
    private ArrayList<Local> arrayList = new ArrayList<Local>();
    String stringLatitude = "", stringLongitude = "";
    GPSTracker gpsTracker;
    Double raio;
    public AppWidgetViewsFactory(Context ctxt, Intent intent) {
        this.context = ctxt;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

    }


    @Override
    public void onCreate() {
        GestorLocaisInteresse gestor = new GestorLocaisInteresse();
        ArrayList<Local>tmpList = new ArrayList<>();

        int nrlocaisAdded = 0;
        tmpList.addAll(gestor.listar(context));
        for(int i = 0; i<tmpList.size();i++){


            //     Toast.makeText(context,String.valueO
            raio = Double.parseDouble(WidgetProvider.getRaio());
            Double lat1 = Double.parseDouble(WidgetProvider.getLat());
            Double long1 = Double.parseDouble(WidgetProvider.getLong());

            Double lat2 = Double.parseDouble(tmpList.get(i).getCoordenadas().getLatitude());
            Double long2 = Double.parseDouble(tmpList.get(i).getCoordenadas().getLongitude());

            SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(context);
            String categoria, nrLocais, frequencia;
            nrLocais= (shr.getString("pref_nr_locais", "15"));
            categoria= (shr.getString("pref_categoria", "All"));
           // atualizacao= (shr.getString("pref_update", "15"));

            Double distance =  distance(lat1, long1, lat2, long2);
            String nomeCat = tmpList.get(i).getCategoria().getNome();
           if(distance<raio){
               if(!categoria.equals("All") && categoria==nomeCat){
                   arrayList.add(tmpList.get(i));
               }
            }

        }
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return (arrayList.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.row);

        row.setTextViewText(android.R.id.text1, arrayList.get(position).getNome());
        //Imagem Cat
        GestorCategorias gCats= new GestorCategorias();
        String ico = gCats.mostrarIconCat(context,arrayList.get(position).getCategoria().getNome());
        int id = context.getResources().getIdentifier(ico, "drawable",
                context.getPackageName());
        row.setImageViewResource(R.id.iconCategoria, id);
        row.setTextViewText(R.id.txtDesc, arrayList.get(position).getDescricao());
        //

        row.setTextViewText(R.id.txtDesc,String.valueOf(raio));


        if (!WidgetProvider.getLat().matches("")) {
            DecimalFormat df = new DecimalFormat("#.0");
            Double lat1 = Double.parseDouble(WidgetProvider.getLat());
            Double long1 = Double.parseDouble(WidgetProvider.getLong());

            Double lat2 = Double.parseDouble(arrayList.get(position).getCoordenadas().getLatitude());
            Double long2 = Double.parseDouble(arrayList.get(position).getCoordenadas().getLongitude());
            row.setTextViewText(R.id.txtDesc,arrayList.get(position).getDescricao()+  " - " + df.format(distance(lat1,long1,lat2, long2)) + "km");
        } else {
            row.setTextViewText(R.id.txtDesc, "No Gps");

        }

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(WidgetProvider.EXTRA_WORD,arrayList.get(position).getNome());
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        arrayList.clear();
        GestorLocaisInteresse gestor = new GestorLocaisInteresse();
        ArrayList<Local>tmpList = new ArrayList<>();

        tmpList.addAll(gestor.listar(context));
        for(int i = 0; i<tmpList.size();i++){


            //     Toast.makeText(context,String.valueO
           // raio = Double.parseDouble(WidgetProvider.getRaio());
            SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(context);
            raio = Double.parseDouble(shr.getString("pref_raio", "15"));
            Double lat1 = Double.parseDouble(WidgetProvider.getLat());
            Double long1 = Double.parseDouble(WidgetProvider.getLong());

            Double lat2 = Double.parseDouble(tmpList.get(i).getCoordenadas().getLatitude());
            Double long2 = Double.parseDouble(tmpList.get(i).getCoordenadas().getLongitude());

            Double distance =  distance(lat1, long1, lat2, long2);
            if(distance<raio){
                arrayList.add(tmpList.get(i));
            }

        }


    }

    public void checkGPS(){

        //GPS
        GPSTracker gpsTracker = new GPSTracker(context, this);


        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());
        } else {
            gpsTracker.showSettingsAlert();
        }


    }

    @Override
    public void onLocationChanged(Location location) {
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

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}