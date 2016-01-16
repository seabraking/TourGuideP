package pt.ipp.estgf.tourguide.Widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 16/01/2016.
 */
public class DownloadWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context c, Intent intent) {
        super.onReceive(c, intent);
    }
    @Override
    public void onUpdate(Context c, AppWidgetManager widgetMan, int[] widgetIds) {
// update a cada instância da widget
        final int N = widgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = widgetIds[i];
// cria um objecto RemoteViews com o layout da widget
            RemoteViews views = new RemoteViews(c.getPackageName(),
                    R.layout.widget_layout);
            views.setTextViewText(R.id.locAtual, "Texto");
            widgetMan.updateAppWidget(appWidgetId, views);
        }
    }
}