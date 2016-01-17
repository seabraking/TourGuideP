package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Activities.LocaisRota;
import pt.ipp.estgf.tourguide.Activities.RotaMapa;
import pt.ipp.estgf.tourguide.Adapter.RotaAdapter;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Classes.Rota;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
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

        mAdapter.notifyDataSetChanged();


        Button btnAddRota = (Button) getActivity().findViewById(R.id.btn_NewRota);


        btnAddRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View va = View.inflate(mContextRota, R.layout.layout_add_rota, null);
                Button btnAddRota = (Button) va.findViewById(R.id.btn_addRota);
                Button btnCancelAddRota = (Button) va.findViewById(R.id.btn_Cancel);
                final EditText addNomeRota = (EditText) va.findViewById(R.id.addNomeRota);
                final EditText addDesRota = (EditText) va.findViewById(R.id.addDecricaoRota);

                addNomeRota.setHint("Nome da Rota");
                addDesRota.setHint("Descrição");


                btnAddRota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nomeRota = addNomeRota.getText().toString();
                        String desRota = addDesRota.getText().toString();

                        if (!nomeRota.matches("") && !desRota.matches("")) {

                            GestorRotas gestorRotas = new GestorRotas();
                            Rota rotaAdd = new Rota(gestorRotas.listar(mContextRota).size(), nomeRota, desRota, null);
                            Boolean res = gestorRotas.adicionar(rotaAdd, mContextRota);
                             if (res) {
                                mRotas.clear();
                                mRotas.addAll(gestorRotas.listar(mContextRota));
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(mContextRota, "Rota Adicionada!", Toast.LENGTH_SHORT).show();
                                builder.dismiss();
                            } else {
                                Toast.makeText(mContextRota, "Erro ao adicionar o rota!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContextRota, "Preencha os campos todos!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                btnCancelAddRota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                builder.setView(va);
                builder.show();

            }
        });

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





    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        final Rota rotaClick = mRotas.get(position);//rota clicada
        GestorRotas gestorRotas = new GestorRotas();
        ArrayList<Local> locaisArrayList = gestorRotas.listarLocaisRota(rotaClick.getId(), mContextRota);//obter todos os locais da rota
        Rota c = new Rota(rotaClick.getId(),rotaClick.getNome(),rotaClick.getDescricaoRota(),locaisArrayList);

        //Criar alertDialog
        AlertDialog.Builder builder=new AlertDialog.Builder(mContextRota);

        builder.setMessage("O que deseja fazer com " + rotaClick.getNome());
        builder.setTitle("O que fazer?");
        AlertDialog mDialog=builder.create();
        builder.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Botao remove
                GestorRotas gestorLocs = new GestorRotas();
                gestorLocs.remover(rotaClick, mContextRota);
                mRotas.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContextRota, "Rota removida!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View va = View.inflate(mContextRota, R.layout.layout_edit_rota, null);
                Button btnEdtRota = (Button) va.findViewById(R.id.btn_editRota);
                Button btnCancelEdtRota = (Button) va.findViewById(R.id.btn_Cancel);
                final EditText edtNomeRota = (EditText) va.findViewById(R.id.editNomeRota);
                final EditText edtDesRota = (EditText) va.findViewById(R.id.editDecricaoRota);

                edtNomeRota.setText(rotaClick.getNome());
                edtDesRota.setText(rotaClick.getDescricaoRota());



                btnEdtRota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nomeRota = edtNomeRota.getText().toString();
                        String desRota = edtDesRota.getText().toString();

                        if (!nomeRota.matches("") && !desRota.matches("")) {

                            GestorRotas gestorRotas = new GestorRotas();
                            Boolean res = gestorRotas.editar(new Rota(rotaClick.getId(), nomeRota, desRota, null), rotaClick, mContextRota);
                            if (res) {

                                mRotas.clear();
                                mRotas.addAll(gestorRotas.listar(mContextRota));
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(mContextRota, "Rota editada!", Toast.LENGTH_SHORT).show();
                                builder.dismiss();


                            } else {
                                Toast.makeText(mContextRota, "Erro ao editar o rota!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContextRota, "Preencha os campos todos!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                btnCancelEdtRota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                builder.setView(va);
                builder.show();
            }
        });
        builder.setNeutralButton("Nada", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Botao ok
            }
        });

        builder.show();


    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
