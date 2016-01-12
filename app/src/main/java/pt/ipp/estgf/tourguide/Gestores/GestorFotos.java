package pt.ipp.estgf.tourguide.Gestores;

import android.content.Context;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Classes.Foto;
import pt.ipp.estgf.tourguide.Interfaces.GestorADT;

/**
 * Created by Vitor on 20/11/2015.
 */
public class GestorFotos implements GestorADT<Foto> {


    /**
     * Adicionar uma Foto a base de dados
     *
     * @param elemento
     * @return retorna o resultado da operacao
     */
    @Override
    public boolean adicionar(Foto elemento,Context context) {
        try {
            String adicionarFotoSQL = "INSERT INTO tbl_photo( name VARCHAR(75), description VARCHAR(255), path VARCHAR(75)) VALUES (" +
                    elemento.getName() + "," +
                    elemento.getDescription() + "," +
                    elemento.getPath() + "," + ");";
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Listar as Fotos da base de dados
     *
     * @return retorna uma lista de Fotos
     */
    @Override
    public ArrayList<Foto> listar(Context context) {

        try {
            String listarCategoriaSQL = "SELECT * FROM tbl_photo";
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Editar uma Foto da base de dados
     * @param elemento
     * @return retorna o resultado da operacao
     */

    @Override
    public boolean editar(Foto elemento, Foto oldElement, Context context) {
        try {
            String editarCategoriaSQL = "UPDATE tbl_photo SET name ='" + elemento.getName() +
                    "',description='" + elemento.getDescription() +
                    "',path='" + elemento.getPath() +
                    "' WHERE id_photo ='" + elemento.getId() + "'";
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Foto remover(Foto elemento, Context contexto) {
        return null;
    }

    /**
     * Romover a Foto da base de dados
     *
     * @param elemento
     * @return retorna a Foto removida
     */

}
