package pt.ipp.estgf.tourguide.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

import pt.ipp.estgf.tourguide.Adapter.CategoriaAdapter;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;
import pt.ipp.estgf.tourguide.R;

import static android.support.v7.widget.TintTypedArray.obtainStyledAttributes;


public class CategoriaFragment extends ListFragment {

    private static final int RESULT_LOAD_IMG = 0;
    private Context mContext;
    private CategoriaAdapter mAdapter;
    private ArrayList<Categoria> mCategorias = new ArrayList<Categoria>();
    private TabLayout tabLayout;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final Categoria c = new Categoria(mCategorias.get(position).getNome(),mCategorias.get(position).getIcon());
        final int pos = position;
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("O que deseja fazer com " + mCategorias.get(position).getNome());
        builder.setTitle("O que fazer?");
        AlertDialog mDialog=builder.create();
        builder.setNegativeButton("Remover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Botao remove
                GestorADT<Categoria> gestorCats = new GestorCategorias();
                gestorCats.remover(c,mContext);
                mCategorias.clear();
                final GestorCategorias gestorCategorias = new GestorCategorias();
                mCategorias.addAll(gestorCategorias.listar(mContext));
                mAdapter.notifyDataSetChanged();
                Toast.makeText(mContext,"Categoria removida!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                View va = View.inflate(mContext, R.layout.layout_edit_categoria, null);
                final Button btnAddCat = (Button) va.findViewById(R.id.btn_addCat);
                final EditText edtNomeCat = (EditText) va.findViewById(R.id.editNomeCategoria);
                edtNomeCat.setText(c.getNome());
                btnAddCat.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        GestorADT<Categoria> gestorCats = new GestorCategorias();
                        String nomeCat = edtNomeCat.getText().toString();
                        if (nomeCat.matches("")) {
                            edtNomeCat.setHint("Nome não pode ser nulo");
                            edtNomeCat.setHintTextColor(getResources().getColor(android.R.color.holo_red_dark));
                        } else {

                            Categoria novaCat = new Categoria(edtNomeCat.getText().toString(), c.getIcon());

                            if (gestorCats.editar(novaCat,c, mContext)) {

                                builder.dismiss();

                                mCategorias.remove(pos);
                                mCategorias.add(novaCat);



                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(mContext, "Categoria Editada!", Toast.LENGTH_LONG).show();
                            } else {
                                builder.dismiss();
                                Toast.makeText(mContext, "Erro Sql!", Toast.LENGTH_LONG).show();
                            }


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = mContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            mCategorias.add(new Categoria("Praias", picturePath));
            mAdapter.notifyDataSetChanged();




        }
    }


    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new CategoriaAdapter(mContext, mCategorias);
        setListAdapter(mAdapter);

        mCategorias.clear();
        final GestorCategorias gestorCategorias = new GestorCategorias();
        mCategorias.addAll(gestorCategorias.listar(mContext));
        ViewPager viewPager = new ViewPager(mContext);
        viewPager.setAdapter(
                new ViewPagerAdapterCategorias(getFragmentManager(), mContext));

        final Button btnAdd = (Button) getView().findViewById(R.id.btn_New);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = getActivity().getLayoutInflater();
                View va = View.inflate(mContext, R.layout.layout_add_categoria, null);
                final Button btnAddCat = (Button) va.findViewById(R.id.btn_addCat);
                final EditText edtNomeCat = (EditText) va.findViewById(R.id.editNomeCategoria);
                btnAddCat.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        GestorADT<Categoria> gestorCats = new GestorCategorias();
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


                        }
                    }
                });
                builder.setView(va);
                builder.show();

            }
        });

        //the images to display
        Integer[] imageIDs = {
                R.drawable.ic_categoria_banco,
                R.drawable.ic_categoria_casino,
                R.drawable.ic_categoria_empresa
        };






        // if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
        // Give the TabLayout the ViewPager

        //adicionar tabs
        tabLayout = (TabLayout) getView().findViewById(R.id.sliding_tabs_categoria);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(viewPager.getAdapter());
        tabLayout.setSelectedTabIndicatorHeight(0);
        mAdapter.notifyDataSetChanged();
    }



    private void setupTabIcons(PagerAdapter pagerAdapter) {

        for(int i=0; i<pagerAdapter.getCount();i++) {
            LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.categoriaprincipal_tab, null);
            TextView tab = (TextView) linearLayout.findViewById(R.id.tab);
            tab.setText(pagerAdapter.getPageTitle(i));

            ViewPagerAdapterCategorias viewPagerAdapterCategorias = (ViewPagerAdapterCategorias) pagerAdapter;
            Categoria categoria = (Categoria) viewPagerAdapterCategorias.getmCategorias().get(i);

            int id = mContext.getResources().getIdentifier(categoria.getIcon(), "drawable",
                    mContext.getPackageName());


            tab.setCompoundDrawablesWithIntrinsicBounds(0, id , 0, 0);
            tabLayout.getTabAt(i).setCustomView(tab);
        }

    }



    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContext = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_categoria, container, false);



        return mContentView;
    }



}


