package e.giuseppemonetti.labcantiello;

/**
 * Created by Giuseppe Monetti on 13/08/2018.
 */

public class Attrazione {
    private String nome;
    private Double lat;
    private Double lng;
    private String categoria;
    private String descrizione;
    private String recapito;
    private String mail;

    public Attrazione(String nome, String descrizione, String recapito, String categoria) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.recapito = recapito;
        this.categoria = categoria;
    }

    public Attrazione(String nome, Double lat, Double lng)
    {
        this.nome = nome;
        this.lat = lat;
        this.lng = lng;
    }
    public Attrazione(String nome, Double lat, Double lng, String categoria)
    {
        this.nome = nome;
        this.lat = lat;
        this.lng = lng;
        this.categoria = categoria;
    }
    public String getNome(){ return nome;}
    public Double getLat(){ return lat;}
    public Double getLng(){ return lng;}
    public String getCategoria(){ return categoria;}
    public void setCategoria(String c){ categoria=c;}

    public void setLat(Double l) {lat = l;}
    public void setLng(Double l) {lng = l;}
}
