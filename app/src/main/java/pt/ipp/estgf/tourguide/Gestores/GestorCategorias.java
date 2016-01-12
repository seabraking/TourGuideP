package pt.ipp.estgf.tourguide.Gestores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.BaseDados.SQLiteConnect;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;

/**
 * Created by Vitor on 20/11/2015.
 */
public class GestorCategorias implements GestorADT<Categoria> {


    /**
     * Adicionar uma Categoria a base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean adicionar(Categoria elemento,Context context){
        try {
            String adicionarCategoriaSQL = "INSERT INTO tbl_category VALUES ('" + elemento.getNome() + "','" + elemento.getIcon() + "');";
            SQLiteConnect dbHelper = new SQLiteConnect(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(adicionarCategoriaSQL);

            return true;
        } catch (Exception e) {

            return false;
        }

    }

    /**
     * Listar as Categorias da base de dados
     *
     * @return retorna uma lista de todas Categorias
     */
    public String mostrarIconCat(Context context, String catName){
        try{
            String listarCategoriaSQL = "SELECT * FROM tbl_category WHERE category_name='" + catName + "'";
            SQLiteConnect dbHelper = new SQLiteConnect(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery(listarCategoriaSQL, null);
            c.moveToFirst();
            return c.getString(1);
        } catch (Exception e){
            Toast.makeText(context,"Erro SQL", Toast.LENGTH_SHORT);
        }
        return null;
    }

    @Override
    public ArrayList<Categoria> listar(Context context) {
        try {

            //Load From BD
            String listarCategoriaSQL = "SELECT * FROM tbl_category GROUP BY category_name";
            SQLiteConnect dbHelper = new SQLiteConnect(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery(listarCategoriaSQL, null);
            ArrayList<Categoria> p = new ArrayList<>();

            if (c != null && c.moveToFirst()) {
                do {
                    p.add(new Categoria(c.getString(0),c.getString(1)));
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            return  p;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Editar uma Categoria da base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean editar(Categoria elemento, Categoria OldElement, Context context) {
        try {
            String editarCategoriaSQL = "UPDATE tbl_category SET category_name ='" + elemento.getNome() +
                    "',icon='" + elemento.getIcon() + "' WHERE category_name ='" + OldElement.getNome() + "'";
            SQLiteConnect dbHelper = new SQLiteConnect(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(editarCategoriaSQL);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Romover a Categoria da base de dados
     *
     * @param elemento
     * @return retorna a Categoria removido
     */
    @Override
    public Categoria remover(Categoria elemento, Context context) {
        try {

            String removerCategoriaSQL = "DELETE FROM tbl_category WHERE category_name='" + elemento.getNome() + "'";
            SQLiteConnect dbHelper = new SQLiteConnect(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(removerCategoriaSQL);

            return elemento;
        } catch (Exception e) {
            return null;
        }
    }


    public ArrayList<Categoria> obterPrincipaisCategorias(Context context){
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            String categoriasSQL = "SELECT category_name,count(*) AS NUM FROM tbl_poi GROUP BY category_name";
            SQLiteConnect dbHelperAux = new SQLiteConnect(context);
            SQLiteDatabase dbAux = dbHelperAux.getWritableDatabase();
            Cursor cursorAux = dbAux.rawQuery(categoriasSQL, null);

            if (cursorAux != null && cursorAux.moveToFirst()) {
                int count=0;
                do {
                    String categoria = "SELECT * FROM tbl_category WHERE category_name='" + cursorAux.getString(0) + "'";
                    Cursor cursor = dbAux.rawQuery(categoria, null);
                    cursor.moveToFirst();
                    categorias.add(new Categoria(cursor.getString(0), cursor.getString(1)));
                    count++;
                    cursor.close();

                } while (cursorAux.moveToNext()&&count<8);
            }

            cursorAux.close();

            return categorias;


        } catch (Exception e) {
            return null;
        }
    }
    public int getNumLocaisCategoria(String nameCategoria,Context context ){
        try {
            String categoriasSQL = "SELECT count(*) AS NUM FROM tbl_poi WHERE category_name='"+nameCategoria+"'";
            SQLiteConnect dbHelperAux = new SQLiteConnect(context);
            SQLiteDatabase dbAux = dbHelperAux.getWritableDatabase();
            Cursor cursor = dbAux.rawQuery(categoriasSQL, null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        } catch (Exception e) {
            return -1;
        }
    }

}