package pt.ipp.estgf.tourguide.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DecimalFormat;

import pt.ipp.estgf.tourguide.Activities.MainAtivity;
import pt.ipp.estgf.tourguide.Classes.GPSTracker;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 16/01/2016.
 */

public class WidgetProvider extends AppWidgetProvider implements LocationListener {
    public static String EXTRA_WORD = "WORD";
    public static String UPDATE_LIST = "UPDATE_LIST";
    private static String lat ="";
    private static String longitude="";
    Context c;

    public static String getLat() {
        return lat;
    }

    public static String getLong() {
        return longitude;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        c = context;
        GPSTracker gpsTracker = new GPSTracker(context,this);
        if (gpsTracker.canGetLocation()) {
            lat = String.valueOf((gpsTracker.getLatitude()));
            longitude = String.valueOf((gpsTracker.getLongitude()));
        }
        Log.e("app widget id - ", appWidgetIds.length + "");
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent svcIntent = new Intent(context, WidgetService.class);
            //svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            //svcIntent.setData(Uri.parse(svcIntent .toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
            widget.setRemoteAdapter(appWidgetIds[i], R.id.words, svcIntent);

            Intent clickIntent = new Intent(context, MainAtivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.words, clickPI);


            clickIntent = new Intent(context, WidgetProvider.class);
            clickIntent.setAction(UPDATE_LIST);
            PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context,0, clickIntent, 0);
            widget.setOnClickPendingIntent(R.id.update_list, pendingIntentRefresh);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equalsIgnoreCase(UPDATE_LIST)){
            updateWidget(context);
        }
        Log.e("onReceive", "onReceive");
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.words);
    }

    @Override
    public void onLocationChanged(Location location) {
        GPSTracker gpsTracker = new GPSTracker(c,this);
        if (gpsTracker.canGetLocation()) {
            lat = String.valueOf((gpsTracker.getLatitude()));
            longitude = String.valueOf((gpsTracker.getLongitude()));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        GPSTracker gpsTracker = new GPSTracker(c,this);
        if (gpsTracker.canGetLocation()) {
            lat = String.valueOf((gpsTracker.getLatitude()));
            longitude = String.valueOf((gpsTracker.getLongitude()));
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        GPSTracker gpsTracker = new GPSTracker(c,this);
        if (gpsTracker.canGetLocation()) {
            lat = String.valueOf((gpsTracker.getLatitude()));
            longitude = String.valueOf((gpsTracker.getLongitude()));
        }
    }

    @Override
    public void onProviderDisabled(String provider) {


            lat = "";
            longitude = "";

    }
}