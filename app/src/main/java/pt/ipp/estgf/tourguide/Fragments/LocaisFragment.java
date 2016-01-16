package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Activities.InformacaoLocal;
import pt.ipp.estgf.tourguide.Adapter.CategoriaAdapter;
import pt.ipp.estgf.tourguide.Adapter.LocalAdapter;
import pt.ipp.estgf.tourguide.Adapter.SearchableLocalAdapter;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 27/11/2015.
 */
public class LocaisFragment extends ListFragment {
    private Context mContext;
    private SearchableLocalAdapter mAdapter;
    private ArrayList<Local> mLocais = new ArrayList<Local>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SearchableLocalAdapter(mContext, mLocais);
        setListAdapter(mAdapter);
        GestorLocaisInteresse gestorLocais = new GestorLocaisInteresse();
        mLocais.addAll(gestorLocais.listar(mContext));
        mAdapter.notifyDataSetChanged();
        // Specify the layout to use when the list of choices appears
        final Spinner spinner = (Spinner)getActivity().findViewById(R.id.spinnerCategory);
        ArrayList<Categoria> cats =  new GestorCategorias().listar(mContext);
        cats.add(0,new Categoria("Categorias",""));
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(mContext,R.layout.spinner_item, cats);
        spinner.setAdapter(adapter);
        //SpinnerRaio
        final Spinner spinnerRaio = (Spinner)getActivity().findViewById(R.id.spinnerRaio);
        ArrayList<String> raioSpinner = new ArrayList<>();
        raioSpinner.add("Raio");
        raioSpinner.add("< 2 KM");
        raioSpinner.add("< 5 KM");
        raioSpinner.add("< 10 KM");
        raioSpinner.add("< 15 KM ");
        raioSpinner.add("< 25 KM");
        ArrayAdapter<CharSequence> raioAdapter = new ArrayAdapter(mContext,R.layout.spinner_item, raioSpinner);
        spinnerRaio.setAdapter(raioAdapter);
        //SpinnerRatingbar
        final Spinner spinnerRating = (Spinner)getActivity().findViewById(R.id.spinnerRating);
        ArrayList<String> rats = new ArrayList<>();
        rats.add("Rating");
        rats.add("> 1");
        rats.add("> 2");
        rats.add("> 3");
        rats.add("> 4");
        ArrayAdapter<CharSequence> starsadapter = new ArrayAdapter(mContext,R.layout.spinner_item, rats);
        spinnerRating.setAdapter(starsadapter);

        final SearchView txtPesquisa = (SearchView)getActivity().findViewById(R.id.txtPesquisa);
        txtPesquisa.setQuery("", false);
/*
        txtPesquisa.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    spinnerRating.setVisibility(View.GONE);
                    spinnerRaio.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                   // txtPesquisa.setQuery("", false);
                } else {
                    spinnerRating.setVisibility(View.VISIBLE);
                }
            }
        });*/

        spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    if(!txtPesquisa.getQuery().equals("")&&(spinner.getSelectedItemPosition()<1))
                        mAdapter.getFilter().filter("qcr:" + spinner.getSelectedItem().toString() + ":" + txtPesquisa.getQuery().toString() + ":" + spinner.getSelectedItemPosition());
                } else {
                    mAdapter.getFilter().filter("r:" + spinnerRating.getSelectedItemPosition());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if(txtPesquisa.getQuery().toString().equals(""))
                    {
                        Toast.makeText(getContext(),"txTpesquisa Vazio, Filtrar Cats",Toast.LENGTH_SHORT).show();
                        mAdapter.getFilter().filter("c:" + spinner.getSelectedItem().toString());
                    } else {
                        Toast.makeText(getContext(),"Filtrar Cats + Query",Toast.LENGTH_SHORT).show();
                        mAdapter.getFilter().filter("qc:" + spinner.getSelectedItem().toString() + ":" + txtPesquisa.getQuery().toString());
                    }
                } else {
                    mAdapter.getFilter().filter("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SearchView.OnQueryTextListener searchTextWatcher = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(spinner.getSelectedItemPosition()>0)
                {
                    mAdapter.getFilter().filter("qc:" + spinner.getSelectedItem().toString() + ":" + txtPesquisa.getQuery().toString());
                } else {
                    mAdapter.getFilter().filter("q:" + query.toString());
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    //qd alguem esta a escrever
                    // Search
                } else {

                    //Quando tudo foi apagado
                    mAdapter.getFilter().filter("");

                    if(spinner.getSelectedItemPosition()>0){
                        mAdapter.getFilter().filter("c:");
                        mAdapter.getFilter().filter("c:" + spinner.getSelectedItem().toString());
                   }
                    }

                return false;
            }
        };
        txtPesquisa.setOnQueryTextListener(searchTextWatcher);
/*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position > 0){
                    // get spinner value
                }else{
                    // show toast select gender
                }
                // mAdapter.getFilter().filter("c:" + spinner.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/

        final Button btnAdd = (Button) getView().findViewById(R.id.btn_NewLoc);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                View va = View.inflate(mContext, R.layout.layout_add_categoria, null);
                btnAdd.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        /*GestorADT<Categoria> gestorCats = new GestorCategorias();
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


                        }*/
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
        mContext = this.getActivity();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_locais, container, false);

        return mContentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        final Local c = new Local(mLocais.get(position).getId(),mLocais.get(position).getNome(),
                mLocais.get(position).getDescricao(), mLocais.get(position).getRating(),  mLocais.get(position).getCoordenadas(), mLocais.get(position).getCategoria());
        final int pos = position;
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("O que deseja fazer com " + mLocais.get(position).getNome());
        builder.setTitle("O que fazer?");
        AlertDialog mDialog=builder.create();
        builder.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Botao remove
                GestorADT<Local> gestorLocs = new GestorLocaisInteresse();
                gestorLocs.remover(c,mContext);
                mLocais.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext,"Local removida!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Detalhes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, InformacaoLocal.class);
                i.putExtra("idLocal", mLocais.get(pos).getId());
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

        /*        mCategorias.add(new Categoria("Java","jpg.jpg"));
        mAdapter.notifyDataSetChanged();
        */
        /*Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMG);*/
        // MenuInflater menu = new MenuInflater(mContext);
        //  menu.inflate(R.menu.menu_main_ativity,menu);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
