package pt.ipp.estgf.tourguide.Widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by bia on 16/01/2016.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new AppWidgetViewsFactory(this.getApplicationContext(), intent));
    }
}