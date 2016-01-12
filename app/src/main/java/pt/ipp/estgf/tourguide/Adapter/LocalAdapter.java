package pt.ipp.estgf.tourguide.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 20/11/2015.
 */
public class LocalAdapter extends ArrayAdapter<Local> {
    private Context context;
    private ArrayList<Local> mList;

    public LocalAdapter(Context context, ArrayList<Local> list){
        super(context, R.layout.layout_line_local,list);
        this.context = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        //convertView.setBackgroundResource(position % 2 == 0 ? context.getResources(R.color.branco): v.getResources(R.color.azul));
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.layout_line_local, null);


        }
        TextView mNome = (TextView)v.findViewById(R.id.nomeLocal);
        TextView mDesc = (TextView)v.findViewById(R.id.descricaoLocal);
        RatingBar mRating = (RatingBar)v.findViewById(R.id.localRating);
        TextView mCat = (TextView)v.findViewById(R.id.localCat);
        mNome.setText(mList.get(position).getNome());
        mDesc.setText(mList.get(position).getDescricao());
        mRating.setRating(mList.get(position).getRating());
        mCat.setText(mList.get(position).getCategoria().getNome());
        return v;
    }
}
