package pt.ipp.estgf.tourguide.Activities;


import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Fragments.FragmentMap;
import pt.ipp.estgf.tourguide.Fragments.LocalMapFragment;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
import pt.ipp.estgf.tourguide.R;

public class InformacaoLocal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_local);


        getSupportActionBar().hide();

        Intent intent = getIntent();
        int idLocal = intent.getExtras().getInt("idLocal");

        GestorLocaisInteresse gestorLocaisInteresse = new GestorLocaisInteresse();

        TextView nomeLocal = (TextView) findViewById(R.id.infLocalNomeLocal);
        TextView categoriaLocal = (TextView) findViewById(R.id.infLocalCategoria);
        TextView descLocal = (TextView) findViewById(R.id.infLocalDescricao);
        TextView latLocal = (TextView) findViewById(R.id.infLocalLat);
        TextView logLocal = (TextView) findViewById(R.id.infLocalLong);
        RatingBar ratingLocal = (RatingBar) findViewById(R.id.infLocalRating);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.idInfLocalMapa);


        final Local local = gestorLocaisInteresse.getLocalById(idLocal, getApplicationContext());
        nomeLocal.setText(local.getNome());
        categoriaLocal.setText(local.getCategoria().getNome());
        descLocal.setText(local.getDescricao());
        latLocal.setText(local.getCoordenadas().getLatitude());
        logLocal.setText(local.getCoordenadas().getLongitude());
        ratingLocal.setRating(local.getRating());


        LocalMapFragment newFragment = new LocalMapFragment();
        Bundle args = new Bundle();
        args.putInt("idLocal", idLocal);
        newFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.idInfLocalMapa, newFragment);
        ft.commit();

        Button buttonEditLocal = (Button) findViewById(R.id.botaoEditLocal);
        buttonEditLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(InformacaoLocal.this).create();
                final LayoutInflater inflater = getLayoutInflater();
                View va = View.inflate(InformacaoLocal.this, R.layout.layout_edit_local, null);

                Button btnEditLocal = (Button) va.findViewById(R.id.btn_addLoc);
                Button btnCancelEditLocal = (Button) va.findViewById(R.id.btn_addLoc);
                EditText edtNomeCat = (EditText) va.findViewById(R.id.editNomeLocal);
                EditText edtDescLocal = (EditText) va.findViewById(R.id.editDescricaoLocal);
                Spinner edtRatLocal = (Spinner) va.findViewById(R.id.spinnerSelectRating);
                EditText edtLatitude = (EditText) va.findViewById(R.id.editLatitudeLocal);
                EditText edtLongitude = (EditText) va.findViewById(R.id.editLongitudeLocal);
                Spinner edtCategoriaSpinner = (Spinner) va.findViewById(R.id.spinnerSelectCategoria);

                edtNomeCat.setText(local.getNome());
                edtDescLocal.setText(local.getDescricao());

                //RATING BAR
                Integer[] ratings = new Integer[]{1,2,3,4,5};
                ArrayAdapter<Integer> ratingOptions = new ArrayAdapter(InformacaoLocal.this, R.layout.spinner_item, ratings);
                edtRatLocal.setAdapter(ratingOptions);
                edtRatLocal.setSelection(ratingOptions.getPosition(local.getRating()));

                edtLatitude.setText(local.getCoordenadas().getLatitude());
                edtLongitude.setText(local.getCoordenadas().getLongitude());

                GestorCategorias gestorCategorias = new GestorCategorias();
                ArrayList<Categoria> categoriaArrayList = gestorCategorias.listar(InformacaoLocal.this);
                String[] categorias = new String[categoriaArrayList.size()];
                for (int i = 0; i < categoriaArrayList.size(); i++) {
                    categorias[i] = categoriaArrayList.get(i).getNome();
                }

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(InformacaoLocal.this, R.layout.spinner_item, categorias);
                edtCategoriaSpinner.setAdapter(adapter);


                //carregar dados e update
                btnEditLocal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                builder.setView(va);
                builder.show();

            }
        });

    }


}
