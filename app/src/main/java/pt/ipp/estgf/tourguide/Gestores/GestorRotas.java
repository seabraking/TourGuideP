package pt.ipp.estgf.tourguide.Gestores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.BaseDados.SQLiteConnect;
import pt.ipp.estgf.tourguide.Classes.Categoria;
import pt.ipp.estgf.tourguide.Classes.Coordenadas;
import pt.ipp.estgf.tourguide.Classes.Local;
import pt.ipp.estgf.tourguide.Classes.Rota;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;

/**
 * Created by Vitor on 20/11/2015.
 */
public class GestorRotas implements GestorADT<Rota> {


    /**
     * Adicionar uma Rota a base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean adicionar(Rota elemento,Context context) {
        try {
            String adicionarRotaSQL = "INSERT INTO tbl_tour(name" +
                    "VARCHAR(75), description VARCHAR(255)) VALUES (" +
                    elemento.getNome() + "," +
                    elemento.getDescricaoRota()+");";

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Listar as Rota da base de dados
     *
     * @return retorna uma lista de rotas
     */
    @Override
    public ArrayList<Rota> listar(Context context) {
        try {
            String listarRotaSQL = "SELECT * FROM tbl_tour";


            SQLiteConnect db = new SQLiteConnect(context);
            SQLiteDatabase rotasdb = db.getWritableDatabase();
            Cursor cRotas = rotasdb.rawQuery(listarRotaSQL, null);
            ArrayList<Rota> p = new ArrayList<>();

            if (cRotas != null && cRotas.moveToFirst()) {
                do {
                    p.add(new Rota(cRotas.getInt(0),cRotas.getString(1),cRotas.getString(2)));
                } while (cRotas.moveToNext());
            }
            cRotas.close();
            db.close();
            return  p;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Editar uma Rota da base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean editar(Rota elemento,Rota oldElement, Context context) {

        try {
            String editarCategoriaSQL = "UPDATE tbl_tour SET name ='" + elemento.getNome() +
                    "',description='" + elemento.getDescricaoRota() +
                    "' WHERE id_tour ='" + elemento.getId() + "'";
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Rota remover(Rota elemento, Context contexto) {
        return null;
    }

    /**
     * Romover uma Rota da base de dados
     *
     * @param elemento
     * @return retorna a Rota removida
     */






    /**
     * Adicionar um Local Interesse na Rota a base de dados
     * @param rota
     * @param locais
     * @return retorna o resultado da operacao
     */
    public boolean adicionarLocalInteresseRota(Rota rota,Local locais) {
        try {
            String adicionarRotaSQL = "INSERT INTO tbl_tour_pois(id_tour INTEGER, id_poi INTEGER) VALUES (" +
                    rota.getId() + "," +
                    locais.getId()+");";

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Adicionar Locais Interesse numa Rota pertencente a base de dados
     * @param rota
     * @param locais
     * @return retorna o resultado da operacao
     */
    public boolean adicionarLocaisInteresseRota(Rota rota,Local[] locais) {
        try {
            int id= rota.getId();
            for(int i=0;i<locais.length;i++){
                String adicionarRotaSQL = "INSERT INTO tbl_tour_pois(id_tour INTEGER, id_poi INTEGER) VALUES (" +
                        id + "," +
                        locais[i].getId()+");";
            }


            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Romover um LocalInteresse de uma Rota presente na base de dados
     *
     * @param rota
     * @param localInteresse
     * @return retorna o Local Interesse removido
     */
    public Local removerLocalInteresseRota(Rota rota, Local localInteresse) {
        try {
            String removerLocalInteresseSQL = "DELETE FROM tbl_tour_pois WHERE id_poi='" + localInteresse.getId() + "'";

            return localInteresse;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Local> listarLocaisRota(int idrota,Context context) {

        try {
            String listarLocaisSQL = "SELECT tbl_poi.id_poi,name,description,latitude,longitude,rating,category_name FROM tbl_poi INNER JOIN tbl_tour_pois ON tbl_poi.id_poi = tbl_tour_pois.id_poi WHERE tbl_tour_pois.id_tour = "+idrota;


            SQLiteConnect db = new SQLiteConnect(context);
            SQLiteDatabase localDb = db.getWritableDatabase();
            Cursor cLocais = localDb.rawQuery(listarLocaisSQL, null);
            ArrayList<Local> p = new ArrayList<>();


            if (cLocais != null && cLocais.moveToFirst()) {
                do {

                    Coordenadas crd = new Coordenadas(cLocais.getString(3), cLocais.getString(4));
                    Categoria cat = new Categoria(cLocais.getString(6), null);
                    p.add(new Local(cLocais.getInt(0), cLocais.getString(1), cLocais.getString(2), cLocais.getInt(5), crd, cat));
                } while (cLocais.moveToNext());
            }
            cLocais.close();
            db.close();
            return p;

        } catch (Exception e) {
    return null;
        }
    }








}
