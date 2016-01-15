package pt.ipp.estgf.tourguide.Adapter;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Activities.MainAtivity;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.R;
import pt.ipp.estgf.tourguide.Services.GPSTracker;

/**
 * Created by bia on 14/12/2015.
 */
public class SearchableLocalAdapter extends BaseAdapter implements Filterable, LocationListener {

    private ArrayList<Local> mList = null;
    private ArrayList<Local> filteredData = null;
    private Context context;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    public SearchableLocalAdapter(Context context, ArrayList<Local> data) {

        this.filteredData = data;
        this.mList = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return filteredData.size();
    }

    public Local getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_line_local, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.mNome = (TextView) convertView.findViewById(R.id.nomeLocal);
            holder.mDesc = (TextView)convertView.findViewById(R.id.descricaoLocal);
            holder.mRating = (RatingBar)convertView.findViewById(R.id.localRating);
            holder.mCat = (TextView)convertView.findViewById(R.id.localCat);
            holder.mImageCat = (ImageView)convertView.findViewById(R.id.imgCat);
            holder.distance = (TextView)convertView.findViewById(R.id.distancia);

            // Bind the data efficiently with the holder.

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }
        Context context = holder.mImageCat.getContext();
        GestorCategorias gCats= new GestorCategorias();
        String ico = gCats.mostrarIconCat(context,filteredData.get(position).getCategoria().getNome());
        int id = context.getResources().getIdentifier(ico, "drawable",
                context.getPackageName());

        // If weren't re-ordering this you could rely on what you set last time
        holder.mNome.setText(filteredData.get(position).getNome());
        holder.mDesc.setText(filteredData.get(position).getDescricao());

        holder.mRating.setRating(filteredData.get(position).getRating());
        holder.mCat.setText(filteredData.get(position).getCategoria().getNome());
        holder.mImageCat.setImageResource(id);

        //GPS
        GPSTracker gpsTracker = new GPSTracker(context, this);
        String stringLatitude = "", stringLongitude = "", nameOfLocation="";

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.getLatitude());
            stringLongitude = String.valueOf(gpsTracker.getLongitude());
            Double lat1 = (Double.parseDouble(stringLatitude));
            Double long1 = Double.parseDouble(stringLongitude);
            Double lat2 = Double.parseDouble(filteredData.get(position).getCoordenadas().getLatitude());
            Double long2 = Double.parseDouble(filteredData.get(position).getCoordenadas().getLongitude());
            DecimalFormat df = new DecimalFormat("#.0");
            holder.distance.setText(" (" + df.format(gpsTracker.distance(lat1,long1,lat2, long2)) + " km)");
        } else {
            holder.distance.setText("Impossivel obter distancia.");
        }

        return convertView;
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

    static class ViewHolder {
        TextView mNome;
        TextView mDesc;
        RatingBar mRating;
        TextView mCat;
        ImageView mImageCat;
        TextView distance;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            ArrayList<Local> list= mList;
            /*
            if(filterString.matches("")){

                list ;

            } else {
                list = filteredData;
            }*/


            int count = list.size();
            final ArrayList<Local> nlist = new ArrayList<Local>(count);

            Local filterableString;
            if(filterString.startsWith("q:")){
                String[] str = filterString.split("q:");
                if(!str.equals(null)) {
                    list= filteredData;
                    count = list.size();
                    filterString = str[1];
                    for (int i = 0; i < count; i++) {
                        filterableString = list.get(i);
                        if (filterableString.getNome().toLowerCase().contains(filterString)) {
                            nlist.add(filterableString);
                        }
                    }

                }
            } else if(filterString.startsWith("c:")){
                String[] str = filterString.split("c:");
                if(!str.equals(null)) {
                    filterString = str[1];
                    list= filteredData;
                    count = list.size();
                    for (int i = 0; i < count; i++) {
                        filterableString = list.get(i);
                        if (filterableString.getCategoria().getNome().toLowerCase().contains(filterString.toLowerCase())) {
                            nlist.add(filterableString);
                        }
                    }
                    results.values = nlist;
                    results.count = nlist.size();

                    return results;

                }
            } else if(filterString.startsWith("r:")){
                String[] str = filterString.split("r:");
                if(!str.equals(null)) {
                    filterString = str[1];
                    list= filteredData;
                    count = list.size();
                    for (int i = 0; i < count; i++) {
                        filterableString = list.get(i);
                        if (filterableString.getRating()>(Integer.parseInt(filterString))) {
                            nlist.add(filterableString);
                        }
                    }
                }
                results.values = nlist;
                results.count = nlist.size();

                return results;

            } else {
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.getNome().toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }
                }
            }


            results.values = nlist;
            results.count = nlist.size();

            return results;

        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Local>) results.values;
            notifyDataSetChanged();
        }

    }
}
