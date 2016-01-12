package pt.ipp.estgf.tourguide.Activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Adapter.LocalAdapter;
import pt.ipp.estgf.tourguide.Adapter.SearchableLocalAdapter;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.Gestores.GestorRotas;
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
        Button button = (Button) findViewById(R.id.botaoAdicionarLocalRota);

        Intent intent = getIntent();
        int idRota = intent.getExtras().getInt("idRota");
        nomeRota.setText(intent.getExtras().getString("nomeRota"));
        GestorRotas gestorRotas = new GestorRotas();
        mLocaisRota.addAll(gestorRotas.listarLocaisRota(idRota, getApplicationContext()));
        mAdapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog builder = new AlertDialog.Builder(v.getContext()).create();
                final LayoutInflater inflater = getLayoutInflater();
                View view = View.inflate(v.getContext(), R.layout.layout_add_local_rota, null);
                final Button btnAddCLoc = (Button) view.findViewById(R.id.btn_addLocal);
                final Spinner selectLocal = (Spinner) view.findViewById(R.id.selectLocal);

                ArrayList<Local> locals = new GestorLocaisInteresse().listar(v.getContext());
                ArrayList<String> locaisSpinner = new ArrayList<String>();

                for (int i = 0; i < locals.size(); i++) {
                    boolean localEncontrado = false;
                    for (int j = 0; j < mLocaisRota.size(); j++) {
                        if (locals.get(i).getId() == mLocaisRota.get(j).getId()) {
                            localEncontrado = true;
                        }
                    }//como dafuq
                    // faz o passo 3 xD e 4 e 5PAH
                    if(!localEncontrado){
                        locaisSpinner.add(locals.get(i).getId() + " - " + locals.get(i).getNome());}
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, locaisSpinner);
                selectLocal.setAdapter(adapter);

                //SpinnerRatingbar

                /*
                btnAddCLoc.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        GestorADT<Categoria> gestorCats = new GestorCategorias();
                        String nomeCat = edtNomeCat.getText().toString();
                        if (nomeCat.matches("")) {
                            edtNomeCat.setHint("Nome não pode ser nulo");
                            edtNomeCat.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        } else {

                            Categoria novaCat = new Categoria(edtNomeCat.getText().toString(), "ic_categoria_praia");

                            if (gestorCats.adicionar(novaCat, mContext)) {

                                builder.dismiss();
                                mCategorias.add(novaCat);
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(mContext, "Categoria Adicionada!", Toast.LENGTH_LONG).show();
                            } else {
                                builder.dismiss();
                                Toast.makeText(mContext, "Erro Sql!", Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                });
                */
                builder.setView(view);
                builder.show();
            }
        });


    }
}
