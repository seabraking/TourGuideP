package pt.ipp.estgf.tourguide.Activities;

import android.os.Bundle;

import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
import pt.ipp.estgf.tourguide.R;


/**
 * Created by bia on 12/01/2016.
 */
public class Preferencias extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferencias_layout);
        ListPreference lp = (ListPreference)findPreference("pref_categoria");
        GestorADT<Categoria> gCats = new GestorCategorias();
        ArrayList<Categoria> cats = new ArrayList<Categoria>();
        cats.addAll(gCats.listar(getActivity()));
        ArrayList<String> catsString = new ArrayList<String>();
        int i;
        for(i=0;i<cats.size();i++){
            catsString.add(cats.get(i).getNome());
        }

        lp.setDefaultValue("Show All");
        lp.setEntries(catsString.toArray(new CharSequence[cats.size()]));
        lp.setEntryValues(catsString.toArray(new CharSequence[cats.size()]));
    }
}
