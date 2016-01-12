package pt.ipp.estgf.tourguide.Classes;


import android.media.Rating;

import pt.ipp.estgf.tourguide.Gestores.GestorCategorias;

/**
 * Created by Vitor on 20/11/2015.
 */
public class Local {
    private int id;
    private String nome;
    private String descricao;
    private int rating;
    private Coordenadas coordenadas;
    private Categoria categoria;

    public Local(int id, String nome, String descricao, int rating, Coordenadas coordenadas, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.rating = rating;
        this.coordenadas = coordenadas;
        this.categoria = categoria;
    }

    public int getId() {  return id; }

    public void setId(int id) {  this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
