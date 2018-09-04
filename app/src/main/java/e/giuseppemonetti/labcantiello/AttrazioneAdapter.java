package e.giuseppemonetti.labcantiello;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Attr;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static java.lang.Math.round;

/**
 * Created by Giuseppe Monetti on 13/08/2018.
 */

public class AttrazioneAdapter extends BaseAdapter {



    private Context context;
    private ArrayList<Attrazione> elencoAttrazioni;

    public AttrazioneAdapter(Context context, ArrayList<Attrazione> elencoAttrazioni) {
        this.context = context;
        this.elencoAttrazioni = elencoAttrazioni;
    }


    public void update(ArrayList<Attrazione> newList)
    {
        elencoAttrazioni = newList;
        notifyDataSetChanged();
    }

    // Invocato per ottenere il numero di elementi nella lista
    public int getCount(){return  elencoAttrazioni.size();}

    // Invocato per ottenere l'iesimo elemento
    public Attrazione getItem (int i){return elencoAttrazioni.get(i); }

    public long getItemId(int i) {
        return 0;
    }

    // Invocato per ottenere la view della riga da visualizzare
    public View getView (int i, View view, ViewGroup viewGroup){

        //Provvedo a costruire il layout nel caso in cui esso non sia stato iniettato
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_riga_attrazione, null);
        }

        //ottengo il nome completo corrente
        TextView vTextAttrazione = view.findViewById(R.id.textAttrazione);
        TextView vTextDistanza = view.findViewById(R.id.textDistanza);

        //imposto i valori da visualizzare
        vTextAttrazione.setText(elencoAttrazioni.get(i).getNome());
        Location l1 = new Location("");
        l1.setLatitude(elencoAttrazioni.get(i).getLat());
        l1.setLongitude(elencoAttrazioni.get(i).getLng());
        Location l2 = new Location("");
        l2.setLatitude(MainActivity.getLatlng().latitude);
        l2.setLongitude(MainActivity.getLatlng().longitude);
        vTextDistanza.setText("A " + String.format("%.2f",l1.distanceTo(l2)/1000) + " km da te.");

        return view;
    }

    public void setElencoAttrazioni(ArrayList<Attrazione> elencoAttrazioni)
    {
        this.elencoAttrazioni=elencoAttrazioni;
        notifyDataSetChanged();
    }


    // CI RESTITUISCE L'ID DELLA RISORSA
    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }


}
