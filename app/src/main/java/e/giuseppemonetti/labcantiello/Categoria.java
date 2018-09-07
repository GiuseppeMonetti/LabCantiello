package e.giuseppemonetti.labcantiello;

/**
 * Created by Giuseppe Monetti on 31/07/2018.
 */

public class Categoria {
    private String nomeCategoria;
    private int key;
    private String iconURL;

    public Categoria(String n, int k, String i) {
        nomeCategoria = n;
        iconURL = i;
        key = k;
    }



    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public String getIconURL(){return iconURL;}
    public int getKey() {return key;}


}
