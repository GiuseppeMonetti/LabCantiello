package e.giuseppemonetti.labcantiello;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Giuseppe Monetti on 30/07/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    /**
     * Numero di tab e quindi di fragment da visualizzare
     */
    private int numeroDiTab = 2;

    /**
     * Costruttore
     * @param fm
     */
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Restituisce l'elemento corrispondente alla posizione passata
     * @param position posizione
     * @return elemento
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();

            case 1:
                return new MapFragment();

            default:
                return null;
        }
    }

    /**
     * Restituisce il numero di fragment totali
     * @return numero di fragment
     */
    @Override
    public int getCount() {
        return numeroDiTab;
    }

    /**
     * Restituisce l'id della risorsa con il nome del tab da mettere nel menu
     * @param position indice della posizione
     * @return id della stringa da mostrare nel tab
     */
    public int getItemTabNameResourceId(int position) {
        switch (position) {
            case 0:
                return R.string.tab_principale;
            case 1:
                return R.string.tab_mappa;
            default:
                return R.string.tab_unknown;
        }
    }
}
