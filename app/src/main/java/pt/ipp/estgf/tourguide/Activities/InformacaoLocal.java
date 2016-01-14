package pt.ipp.estgf.tourguide.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import pt.ipp.estgf.tourguide.Classes.Local;
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

        Local local = gestorLocaisInteresse.getLocalById(idLocal,getApplicationContext());
        nomeLocal.setText(local.getNome());
        categoriaLocal.setText(local.getCategoria().getNome());
        descLocal.setText(local.getDescricao());
        latLocal.setText(local.getCoordenadas().getLatitude());
        logLocal.setText(local.getCoordenadas().getLongitude());
        ratingLocal.setRating(local.getRating());



    }
}
