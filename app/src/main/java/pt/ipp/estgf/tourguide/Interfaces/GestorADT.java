package pt.ipp.estgf.tourguide.Interfaces;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Vitor on 20/11/2015.
 */
public interface GestorADT<T>
{

    /**
     * Adicionar um elemento a base de dados
     * @param elemento
     * @return retorna o resultado da operacao
     */
    public boolean adicionar(T elemento,Context context);

    /**
     * Listar os elementos da base de dados
     * @return retorna uma lista
     */
    public ArrayList<T> listar(Context context);

    /**
     * Editar um elemento da base de dados
     * @param elemento
     * @return retorna o resultado da operacao
     */
    public boolean editar(T elemento, T oldElement, Context context);

    /**
     * Romover o elemento da base de dados
     * @param elemento
     * @return retorna o elemento removido

     */
    public T remover(T elemento, Context contexto);



}
