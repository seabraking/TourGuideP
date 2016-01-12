package pt.ipp.estgf.tourguide.Gestores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.BaseDados.SQLiteConnect;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Coordenadas;
import pt.ipp.estgf.tourguide.Classes.Foto;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;

/**
 * Created by Vitor on 20/11/2015.
 */
public class GestorLocaisInteresse implements GestorADT<Local> {

    /**
     * Adicionar um LocalInteresse a base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean adicionar(Local elemento,Context context) {
        try {
            String adicionarLocalInteresseSQL = "INSERT INTO tbl_poi( " +
                    "name VARCHAR(75), description VARCHAR(255), latitude VARCHAR(15), longitude  VARCHAR(15)," +
                    "rating INTEGER, category_name VARCHAR(75)) VALUES (" +
                    elemento.getNome() + "," +
                    elemento.getDescricao() + "," +
                    elemento.getCoordenadas().getLatitude()+"," +
                    elemento.getCoordenadas().getLongitude()+"," +
                    elemento.getRating()+"," +
                    elemento.getCategoria().getNome()+");";

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Listar os LocalInteresse da base de dados
     *
     * @return retorna uma lista de todos os locais de interesse
     */
    @Override
    public ArrayList<Local> listar(Context context) {
        try {

            //Load From BD
            String listarCategoriaSQL = "SELECT * FROM tbl_poi";
            SQLiteConnect dbHelper = new SQLiteConnect(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery(listarCategoriaSQL, null);
            ArrayList<Local> p = new ArrayList<>();


            if (c != null && c.moveToFirst()) {
                do {
                    Coordenadas crd = new Coordenadas(c.getString(3),c.getString(4));
                    Categoria cat = new Categoria(c.getString(6),null);
                    p.add(new Local(c.getInt(0),c.getString(1),c.getString(2),c.getInt(5),crd,cat));
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
     * Editar um LocalInteresse da base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean editar(Local elemento, Local oldLocal, Context context) {
        try {
            String editarCategoriaSQL = "UPDATE tbl_poi SET name ='" + elemento.getNome() +
                    "',description='" + elemento.getDescricao() +
                    "',latitude='" + elemento.getCoordenadas().getLatitude() +
                    "',longitude='" + elemento.getCoordenadas().getLongitude() +
                    "',rating='" + elemento.getRating() +
                    "',category_name='" + elemento.getCategoria().getNome() +
                    "' WHERE id_poi ='" + elemento.getId() + "'";
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Local remover(Local elemento, Context contexto) {
        return null;
    }

    /**
     * Romover o LocalInteresse da base de dados
     *
     * @param elemento
     * @return retorna o LocalInteresse removido
     */


}
