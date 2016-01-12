package pt.ipp.estgf.tourguide.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;
import pt.ipp.estgf.tourguide.R;

/**
 * Created by Vitor on 20/11/2015.
 */
public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    private Context context;
    private ArrayList<Categoria> mList;

    public CategoriaAdapter(Context context, ArrayList<Categoria> list){
        super(context,R.layout.layout_line_categoria,list);
        this.context = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.layout_line_categoria, null);

        }
        TextView mNome = (TextView)v.findViewById(R.id.nomeCategoria);
        ImageView mIcon = (ImageView)v.findViewById(R.id.iconCategoria);



        Categoria categoria = (Categoria) mList.get(position);
        Context context = mIcon.getContext();
        int id = context.getResources().getIdentifier(categoria.getIcon(), "drawable",
                context.getPackageName());
        mIcon.setImageResource(id);

        mNome.setText(mList.get(position).getNome());

        TextView numLocaisCategoria = (TextView) v.findViewById(R.id.numlocaiscategoria);
        GestorCategorias gc = new GestorCategorias();
        numLocaisCategoria.setText(gc.getNumLocaisCategoria(categoria.getNome(), context) + "");
        return v;
    }

}
