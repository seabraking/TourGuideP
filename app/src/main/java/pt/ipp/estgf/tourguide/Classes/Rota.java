package pt.ipp.estgf.tourguide.Classes;

import pt.ipp.estgf.tourguide.Gestores.GestorLocaisInteresse;

/**
 * Created by Vitor on 20/11/2015.
 */
public class Rota {
    private int id;
    private String nome;
    private String descricaoRota;
    private GestorLocaisInteresse gestorLocaisInteresse;

    public Rota(int id,String nome, String descricaoRota, GestorLocaisInteresse gestorLocaisInteresse) {
        this.id = id;
        this.nome = nome;
        this.descricaoRota = descricaoRota;
        this.gestorLocaisInteresse = gestorLocaisInteresse;
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

    public GestorLocaisInteresse getGestorLocaisInteresse() {
        return gestorLocaisInteresse;
    }

    public void setGestorLocaisInteresse(GestorLocaisInteresse gestorLocaisInteresse) {
        this.gestorLocaisInteresse = gestorLocaisInteresse;
    }
}
