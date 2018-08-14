package e.giuseppemonetti.labcantiello;

/**
 * Created by Giuseppe Monetti on 13/08/2018.
 */

public class Attrazione {
    private String nome;
    private Double lat;
    private Double lng;
    private int key;
    private String categoria;

    public Attrazione(String nome, int key) {
        this.nome = nome;
        this.key = key;
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
    public int getKey(){return key;}
    public String getCategoria(){ return categoria;}
    public void setCategoria(String c){ categoria=c;}

    public void setLat(Double l) {lat = l;}
    public void setLng(Double l) {lng = l;}
}
