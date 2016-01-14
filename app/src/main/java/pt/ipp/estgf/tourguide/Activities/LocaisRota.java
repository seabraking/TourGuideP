package pt.ipp.estgf.tourguide.Activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Adapter.LocalAdapter;
import pt.ipp.estgf.tourguide.Adapter.SearchableLocalAdapter;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Classes.Rota;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
import pt.ipp.estgf.tourguide.R;

public class LocaisRota extends ListActivity {
    private Context mContext;
    private SearchableLocalAdapter mAdapter;
    private ArrayList<Local> mLocaisRota = new ArrayList<Local>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_rota);
        mAdapter = new SearchableLocalAdapter(getApplicationContext(), mLocaisRota);
        setListAdapter(mAdapter);

        TextView nomeRota = (TextView) findViewById(R.id.nomeRotaLocais);
        Button buttonAdd = (Button) findViewById(R.id.botaoAdicionarLocalRota);

        Intent intent = getIntent();
        final int idRota = intent.getExtras().getInt("idRota");
        nomeRota.setText(intent.getExtras().getString("nomeRota"));
        GestorRotas gestorRotas = new GestorRotas();
        mLocaisRota.addAll(gestorRotas.listarLocaisRota(idRota, getApplicationContext()));
        mAdapter.notifyDataSetChanged();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog builder = new AlertDialog.Builder(v.getContext()).create();
                final LayoutInflater inflater = getLayoutInflater();
                View view = View.inflate(v.getContext(), R.layout.layout_add_local_rota, null);
                final Button btnAddLoc = (Button) view.findViewById(R.id.btn_addLocal);
                final Button btnCancel = (Button) view.findViewById(R.id.btn_Cancel);
                final Spinner selectLocal = (Spinner) view.findViewById(R.id.selectLocal);

                final ArrayList<Local> locals = new GestorLocaisInteresse().listar(v.getContext());
                ArrayList<String> locaisSpinner = new ArrayList<String>();

                for (int i = 0; i < locals.size(); i++) {
                    boolean localEncontrado = false;
                    for (int j = 0; j < mLocaisRota.size(); j++) {
                        if (locals.get(i).getId() == mLocaisRota.get(j).getId()) {
                            localEncontrado = true;
                        }
                    }

                    if (!localEncontrado) {
                        locaisSpinner.add(locals.get(i).getId() + " - " + locals.get(i).getNome());
                    }
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, locaisSpinner);
                selectLocal.setAdapter(adapter);


                btnAddLoc.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String localAdicionar = selectLocal.getSelectedItem().toString();
                        if (!localAdicionar.matches("")) {
                            String[] parte = localAdicionar.split(" ");
                            int idLocal = Integer.parseInt(parte[0]);
                            GestorRotas gestorRotas = new GestorRotas();

                            if (gestorRotas.adicionarLocalInteresseRota(idRota, idLocal, getApplicationContext())) {
                                builder.dismiss();
                                //Encontar a posicao do local selecionado atraves do id

                                for (int i = 0; i < locals.size(); i++) {
                                    if (locals.get(i).getId() == idLocal) {
                                        mLocaisRota.add(locals.get(i));
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }


                                Toast.makeText(getApplicationContext(), "Adicionado à rota", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERRO SQLite", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Nada selecionado", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();


                    }
                });


                builder.setView(view);
                builder.show();
            }
        });


    }


    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        final View view = v;
        final Local c = new Local(mLocaisRota.get(position).getId(), mLocaisRota.get(position).getNome(),
                mLocaisRota.get(position).getDescricao(), mLocaisRota.get(position).getRating(),
                mLocaisRota.get(position).getCoordenadas(), mLocaisRota.get(position).getCategoria());
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("O que deseja fazer com " + mLocaisRota.get(position).getNome());
        builder.setTitle("O que fazer?");
        AlertDialog mDialog = builder.create();
        builder.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Botao remove
                GestorRotas gestorRotas = new GestorRotas();
                Intent intent = getIntent();
                final int idRota = intent.getExtras().getInt("idRota");
                gestorRotas.removerLocalInteresseRota(idRota, mLocaisRota.get(pos).getId(), getApplicationContext());
                mLocaisRota.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Local removido!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Detalhes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(getApplicationContext(), InformacaoLocal.class);
                i.putExtra("idLocal",mLocaisRota.get(pos).getId());
                startActivity(i);
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
}

