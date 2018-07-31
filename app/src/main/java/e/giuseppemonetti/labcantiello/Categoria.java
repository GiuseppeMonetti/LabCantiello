package e.giuseppemonetti.labcantiello;

/**
 * Created by Giuseppe Monetti on 31/07/2018.
 */

public class Categoria {
    private String nomeCategoria;
    private int key;

    public Categoria(String n, int k) {
        nomeCategoria = n;
        key = k;
    }



    public String getNomeCategoria() {
        return nomeCategoria;
    }



}
