package pt.ipp.estgf.tourguide.Classes;

/**
 * Created by Vitor on 20/11/2015.
 */
public class Categoria {

    private String nome;
    private String icon;

    public Categoria(String nome, String icon) {
        this.nome = nome;
        this.icon = icon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return nome;
    }
}
