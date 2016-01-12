package pt.ipp.estgf.tourguide.Adapter;

import android.content.Context;
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

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 14/12/2015.
 */
public class SearchableLocalAdapter extends BaseAdapter implements Filterable {

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
        return convertView;
    }

    static class ViewHolder {
        TextView mNome;
        TextView mDesc;
        RatingBar mRating;
        TextView mCat;
        ImageView mImageCat;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Local> list = mList;

            int count = list.size();
            final ArrayList<Local> nlist = new ArrayList<Local>(count);

            Local filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getNome().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                    //Toast.makeText(context,"Encontrado: " + filterableString.getNome(),Toast.LENGTH_SHORT).show();
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
