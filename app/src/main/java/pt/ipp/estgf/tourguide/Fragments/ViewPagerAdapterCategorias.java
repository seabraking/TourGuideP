package pt.ipp.estgf.tourguide.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 29/11/2015.
 */
public class ViewPagerAdapterCategorias extends FragmentPagerAdapter {


    private ArrayList<Categoria> mCategorias;
    private Context context;

    public ArrayList<Categoria> getmCategorias() {
        return mCategorias;
    }

    public ViewPagerAdapterCategorias(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        GestorCategorias gestorCategorias = new GestorCategorias();

        //this.categorias = gestorCategorias.obterPrincipaisCategorias(context);
        this.mCategorias = new ArrayList<>();

        mCategorias = gestorCategorias.obterPrincipaisCategorias(context);


    }

    @Override
    public int getCount() {
        return mCategorias.size();
    }

    @Override
    public Fragment getItem(int position) {

        return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        return ((Categoria) mCategorias.get(position)).getNome();
        //return "praia";
    }
}
