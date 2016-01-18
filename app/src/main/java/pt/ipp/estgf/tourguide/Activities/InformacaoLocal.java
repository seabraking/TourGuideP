package pt.ipp.estgf.tourguide.Activities;


import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Coordenadas;
import pt.ipp.estgf.tourguide.Classes.GPSTracker;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Fragments.FragmentMap;
import pt.ipp.estgf.tourguide.Fragments.LocalMapFragment;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
import pt.ipp.estgf.tourguide.R;

public class InformacaoLocal extends AppCompatActivity implements LocationListener {
    View va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_local);


        getSupportActionBar().hide();

        final Intent intent = getIntent();
        final int idLocal = intent.getExtras().getInt("idLocal");

        final GestorLocaisInteresse gestorLocaisInteresse = new GestorLocaisInteresse();

        final TextView nomeLocal = (TextView) findViewById(R.id.infLocalNomeLocal);
        final TextView categoriaLocal = (TextView) findViewById(R.id.infLocalCategoria);
        final TextView descLocal = (TextView) findViewById(R.id.infLocalDescricao);
        final TextView latLocal = (TextView) findViewById(R.id.infLocalLat);
        final TextView logLocal = (TextView) findViewById(R.id.infLocalLong);
        final RatingBar ratingLocal = (RatingBar) findViewById(R.id.infLocalRating);
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
                va = View.inflate(InformacaoLocal.this, R.layout.layout_edit_local, null);

                Button btnEditLocal = (Button) va.findViewById(R.id.btn_editLoc);
                Button btnCancelEditLocal = (Button) va.findViewById(R.id.btn_editCancel);
                final EditText edtNomeCat = (EditText) va.findViewById(R.id.editNomeLocal);
                final EditText edtDescLocal = (EditText) va.findViewById(R.id.editDescricaoLocal);
                final Spinner edtRatLocal = (Spinner) va.findViewById(R.id.spinnerSelectRating);
                final EditText edtLatitude = (EditText) va.findViewById(R.id.editLatitudeLocal);
                final EditText edtLongitude = (EditText) va.findViewById(R.id.editLongitudeLocal);
                final Button obterCoordenadasMapa = (Button) va.findViewById(R.id.editObterCoordenadasMapa);
                final Button obterCoordenadasAtuais = (Button) va.findViewById(R.id.editObterCoordenadasAtuais);
                final Spinner edtCategoriaSpinner = (Spinner) va.findViewById(R.id.spinnerSelectCategoria);

                edtNomeCat.setText(local.getNome());
                edtDescLocal.setText(local.getDescricao());

                //RATING BAR
                Integer[] ratings = new Integer[]{1, 2, 3, 4, 5};
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
                edtCategoriaSpinner.setSelection(adapter.getPosition(local.getCategoria().getNome()));

                //obter coordenadas no mapa
                obterCoordenadasMapa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(InformacaoLocal.this, LocalObterCoordenadas.class);
                        startActivityForResult(i, 1);


                    }
                });
                obterCoordenadasAtuais.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //GPS tracker
                        GPSTracker gpsTracker = new GPSTracker(InformacaoLocal.this, InformacaoLocal.this);
                        String stringLatitude = "", stringLongitude = "";

                        if (gpsTracker.canGetLocation()) {
                            stringLatitude = String.valueOf(gpsTracker.getLatitude());
                            stringLongitude = String.valueOf(gpsTracker.getLongitude());

                                edtLatitude.setText(stringLatitude);
                                edtLongitude.setText(stringLongitude);


                        } else {
                            edtLatitude.setText("0.0");
                            edtLongitude.setText("0.0");
                            Toast.makeText(getApplicationContext(), "GPS desativado", Toast.LENGTH_SHORT).show();
                            gpsTracker.showSettingsAlert();
                        }
                    }

                });

                //carregar dados e update
                btnEditLocal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edtNomeCat.getText().toString().matches("") && !edtDescLocal.getText().toString().matches("") &&
                                !edtLatitude.getText().toString().matches("") && !edtLongitude.getText().toString().matches("")) {

                            Categoria categoria = new GestorCategorias().getCategoriaByName(edtCategoriaSpinner.getSelectedItem().toString(),
                                    InformacaoLocal.this);
                            Coordenadas coordenadas = new Coordenadas(edtLatitude.getText().toString(), edtLongitude.getText().toString());
                            Local newLocal = new Local(local.getId(), edtNomeCat.getText().toString(), edtDescLocal.getText().toString(),
                                    (Integer) edtRatLocal.getSelectedItem(), coordenadas, categoria);
                            Boolean resultadoOperacao = gestorLocaisInteresse.editar(newLocal, local, InformacaoLocal.this);

                            if (resultadoOperacao == true) {

                                Toast.makeText(getApplicationContext(), "Local editado!", Toast.LENGTH_SHORT).show();
                                builder.dismiss();

                                Intent intent1 = getIntent();
                                finish();
                                startActivity(intent1);

                            } else {
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao editar local!", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

                btnCancelEditLocal.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EditText edtLatitude = (EditText) va.findViewById(R.id.editLatitudeLocal);
        EditText edtLongitude = (EditText) va.findViewById(R.id.editLongitudeLocal);

        if (requestCode == 1) {
            if (resultCode == this.RESULT_OK) {
                String latitudeCoordenadas = data.getStringExtra("latitude");
                String longitudeCoordenadas = data.getStringExtra("longitude");
                edtLatitude.setText(latitudeCoordenadas);
                edtLongitude.setText(longitudeCoordenadas);

            }
        }
    }





    @Override
    public void onLocationChanged(Location location) {
        checkGPS();
        //  Toast.makeText(getApplicationContext(),"LocationChanged",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        checkGPS();
        //   Toast.makeText(getApplicationContext(),"StatusChanged",Toast.LENGTH_SHORT).show();

    }

    public void checkGPS(){
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {   }





}
