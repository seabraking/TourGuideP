package pt.ipp.estgf.tourguide.Activities;



import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Fragments.FragmentMap;
import pt.ipp.estgf.tourguide.Fragments.LocalMapFragment;
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


        Local local = gestorLocaisInteresse.getLocalById(idLocal,getApplicationContext());
        nomeLocal.setText(local.getNome());
        categoriaLocal.setText(local.getCategoria().getNome());
        descLocal.setText(local.getDescricao());
        latLocal.setText(local.getCoordenadas().getLatitude());
        logLocal.setText(local.getCoordenadas().getLongitude());
        ratingLocal.setRating(local.getRating());


        LocalMapFragment newFragment = new LocalMapFragment();
        Bundle args = new Bundle();
        args.putInt("idLocal",idLocal);
        newFragment.setArguments(args);
        FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
        ft.add(R.id.idInfLocalMapa, newFragment);
        ft.commit();

        Button buttonEditLocal = (Button) findViewById(R.id.botaoEditLocal);
        buttonEditLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(getApplicationContext()).create();
                final LayoutInflater inflater = getLayoutInflater();
                View va = View.inflate(getApplicationContext(), R.layout.layout_edit_local, null);


            }
        });

    }
}
