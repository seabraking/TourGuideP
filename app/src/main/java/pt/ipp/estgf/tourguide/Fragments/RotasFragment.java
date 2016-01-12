package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Activities.RotaMapa;
import pt.ipp.estgf.tourguide.Adapter.RotaAdapter;
import pt.ipp.estgf.tourguide.Classes.Rota;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 27/11/2015.
 */
public class RotasFragment extends ListFragment{


    private Context mContextRota;
    private RotaAdapter mAdapter;
    private ArrayList<Rota> mRotas = new ArrayList<Rota>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextRota = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new RotaAdapter(mContextRota, mRotas);
        setListAdapter(mAdapter);
        mRotas.clear();
        final GestorRotas gestorRotas = new GestorRotas();
        if(gestorRotas.listar(mContextRota)!=null){
            mRotas.addAll(gestorRotas.listar(mContextRota));
            mAdapter.notifyDataSetChanged();
        }

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinnerNumeroLocaisRota);
        Integer[] nums= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(mContextRota,android.R.layout.simple_dropdown_item_1line,nums);
        spinner.setAdapter(adapter);



        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContextRota = this.getActivity();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragments_rotas, container, false);
        return mContentView;
    }


}
