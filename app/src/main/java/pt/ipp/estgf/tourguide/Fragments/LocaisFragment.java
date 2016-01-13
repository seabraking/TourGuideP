package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(mContext,android.R.layout.simple_dropdown_item_1line, cats);
        spinner.setAdapter(adapter);
        //SpinnerRatingbar
        Spinner spinnerRating = (Spinner)getActivity().findViewById(R.id.spinnerRating);
        ArrayList<Integer> rats = new ArrayList<>();
        rats.add(1);
        rats.add(2);
        rats.add(3);
        rats.add(4);
        rats.add(5);
        ArrayAdapter<CharSequence> starsadapter = new ArrayAdapter(mContext,android.R.layout.simple_dropdown_item_1line, rats);
        spinnerRating.setAdapter(starsadapter);
        final SearchView txtPesquisa = (SearchView)getActivity().findViewById(R.id.txtPesquisa);

        SearchView.OnCloseListener onClose = new SearchView.OnCloseListener(){

            @Override
            public boolean onClose() {
                Toast.makeText(mContext,"EitaPorra",Toast.LENGTH_LONG);
                return true;
            }
        };
        SearchView.OnQueryTextListener searchTextWatcher = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query.toString());

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    // Search
                } else {
                    mAdapter.getFilter().filter("");
                }
                return false;
            }
        };
        txtPesquisa.setOnQueryTextListener(searchTextWatcher);
        txtPesquisa.setOnCloseListener(onClose);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               // mAdapter.getFilter().filter("c:" + spinner.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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
    public void onListItemClick(ListView l, View v, int position, long id) {
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
                mLocais.clear();
                final GestorLocaisInteresse gestorLocais = new GestorLocaisInteresse();
                mLocais.addAll(gestorLocais.listar(mContext));
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext,"Local removida!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                View va = View.inflate(mContext, R.layout.layout_edit_local, null);
                final Button btnAddCat = (Button) va.findViewById(R.id.btn_addCat);
                final EditText edtNomeCat = (EditText) va.findViewById(R.id.editNomeCategoria);
                edtNomeCat.setText(c.getNome());
                btnAddCat.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        GestorADT<Categoria> gestorCats = new GestorCategorias();
                        String nomeLoc = edtNomeCat.getText().toString();
                        if (nomeLoc.matches("")) {
                            edtNomeCat.setHint("Nome não pode ser nulo");
                            edtNomeCat.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        } else {
/*
                            Categoria novoLoc = new Categoria(edtNomeCat.getText().toString(), c.getIcon());

                            if (gestorCats.editar(novoLoc,c, mContext)) {

                                builder.dismiss();

                                mLocais.remove(pos);
                                mLocais.add(novoLoc);



                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(mContext, "Categoria Editada!", Toast.LENGTH_LONG).show();
                            } else {
                                builder.dismiss();
                                Toast.makeText(mContext, "Erro Sql!", Toast.LENGTH_LONG).show();
                            }
*/

                        }
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

        /*        mCategorias.add(new Categoria("Java","jpg.jpg"));
        mAdapter.notifyDataSetChanged();
        */
        /*Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMG);*/
        // MenuInflater menu = new MenuInflater(mContext);
        //  menu.inflate(R.menu.menu_main_ativity,menu);
    }


}
