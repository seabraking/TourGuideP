package pt.ipp.estgf.tourguide.Classes;

import java.util.ArrayList;

import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;

/**
 * Created by Vitor on 20/11/2015.
 */
public class Rota {
    private int id;
    private String nome;
    private String descricaoRota;
    private ArrayList<Local> localArrayList;

    public Rota(int id,String nome, String descricaoRota, ArrayList<Local> localArrayList) {
        this.id = id;
        this.nome = nome;
        this.descricaoRota = descricaoRota;
        this.localArrayList = localArrayList;
    }
    public Rota(int id,String nome, String descricaoRota) {
        this.id = id;
        this.nome = nome;
        this.descricaoRota = descricaoRota;
    }

    public int getId() { return id; }

    public void setId(int id) {   this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricaoRota() {
        return descricaoRota;
    }

    public void setDescricaoRota(String descricaoRota) {
        this.descricaoRota = descricaoRota;
    }

    public ArrayList<Local> getLocalArrayList() {
        return localArrayList;
    }

    public void setLocalArrayList(ArrayList<Local> localArrayList) {
        this.localArrayList = localArrayList;
    }
}
