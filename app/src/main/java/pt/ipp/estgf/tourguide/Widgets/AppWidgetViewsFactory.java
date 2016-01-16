package pt.ipp.estgf.tourguide.Widgets;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.GPSTracker;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 16/01/2016.
 */
public class AppWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory, LocationListener {
    private Context context = null;
    //private int appWidgetId;
    private ArrayList<Local> arrayList = new ArrayList<Local>();

    public AppWidgetViewsFactory(Context ctxt, Intent intent) {
        this.context = ctxt;
		/*appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		Log.e(getClass().getSimpleName(), appWidgetId + "");*/
    }

    @Override
    public void onCreate() {
        GestorLocaisInteresse gestor = new GestorLocaisInteresse();

        arrayList.addAll(gestor.listar(context));
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
        row.setImageViewResource(R.id.iconCategoria,id);
        row.setTextViewText(R.id.txtDesc,arrayList.get(position).getDescricao());

        //
        //GPS
        GPSTracker gpsTracker = new GPSTracker(context, this);
        String stringLatitude = "", stringLongitude = "", nameOfLocation="";

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());
            Double lat1 = (Double.parseDouble(stringLatitude));
            Double long1 = Double.parseDouble(stringLongitude);
            Double lat2 = Double.parseDouble(arrayList.get(position).getCoordenadas().getLatitude());
            Double long2 = Double.parseDouble(arrayList.get(position).getCoordenadas().getLongitude());
            DecimalFormat df = new DecimalFormat("#.0");
            row.setTextViewText(R.id.txtDistancia, " (" + df.format(gpsTracker.distance(lat1,long1,lat2, long2)) + " km)");
        } else {
            row.setTextViewText(R.id.txtDistancia, "No Gps");

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
}