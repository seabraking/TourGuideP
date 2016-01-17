package pt.ipp.estgf.tourguide.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Activities.LocaisRota;
import pt.ipp.estgf.tourguide.Activities.RotaMapa;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Classes.Rota;
import pt.ipp.estgf.tourguide.Fragments.FragmentMap;
import pt.ipp.estgf.tourguide.Fragments.LocaisFragment;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 20/11/2015.
 */
public class RotaAdapter extends ArrayAdapter<Rota> {

    private Context context;
    private ArrayList<Rota> mList;

    public RotaAdapter(Context context, ArrayList<Rota> list) {
        super(context, R.layout.layout_line_rota, list);
        this.context = context;
        this.mList = list;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;

        //convertView.setBackgroundResource(position % 2 == 0 ? context.getResources(R.color.branco): v.getResources(R.color.azul));
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.layout_line_rota, null);


        }

        final TextView mNome = (TextView) v.findViewById(R.id.nomeRota);
        TextView mDesc = (TextView) v.findViewById(R.id.descricaoRota);
        final TextView id = (TextView) v.findViewById(R.id.rotaid);
        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.localRotaRating);
        mNome.setText(mList.get(position).getNome());
        mDesc.setText(mList.get(position).getDescricaoRota());
        id.setText(String.valueOf(mList.get(position).getId()));




        final View newView = (View) v;

        Button buttonLocais = (Button) v.findViewById(R.id.locaisRota);
        buttonLocais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(v.getContext(), LocaisRota.class);
                i.putExtra("idRota",Integer.parseInt(id.getText().toString()));
                i.putExtra("nomeRota",mNome.getText().toString());
                context.startActivity(i);
            }
        });


        Button buttonMapa= (Button) v.findViewById(R.id.mapa);

        buttonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(context, RotaMapa.class);
                i.putExtra("LocaisRota",Integer.parseInt(id.getText().toString()));
                context.startActivity(i);

                //
            }
        });

        //Media dos locais da rota
        GestorRotas gestorRotas = new GestorRotas();
        ArrayList<Local> localArrayList = gestorRotas.listarLocaisRota(Integer.parseInt(id.getText().toString()),context);
        float rating = 0;
        for(int i=0;i<localArrayList.size();i++){
            rating += localArrayList.get(i).getRating();
        }

        if(localArrayList.size()!=0){

            ratingBar.setRating(rating/localArrayList.size());
        }





        return v;
    }
}
