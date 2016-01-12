package pt.ipp.estgf.tourguide.Fragments;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
        Spinner spinner = (Spinner)getActivity().findViewById(R.id.spinnerCategory);
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


}
