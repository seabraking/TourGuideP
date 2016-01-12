package pt.ipp.estgf.tourguide.Fragments;

import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pt.ipp.estgf.tourguide.R;

/**
 * Created by bia on 26/11/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String txtCategorias;
    private String txtLocais;
    private String txtRotas;
    private String tabTitles[];
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.txtCategorias = context.getString(R.string.txtCategorias);
        this.txtLocais = context.getString(R.string.txtLocais);
        this.txtRotas = context.getString(R.string.txtRotas);
        tabTitles = new String[] { txtCategorias, txtLocais, txtRotas};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:{
                return new CategoriaFragment();
            }
            case 1:{
                return new LocaisFragment();
            }
            case 2:{
                return new RotasFragment();
            }
        }
        return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}