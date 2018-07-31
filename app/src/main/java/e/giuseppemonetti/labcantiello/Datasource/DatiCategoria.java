package e.giuseppemonetti.labcantiello.Datasource;

import android.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import e.giuseppemonetti.labcantiello.Categoria;

/**
 * Created by Giuseppe Monetti on 31/07/2018.
 */

public class DatiCategoria extends Fragment {

    //abbiamo creato il DatiCategoria
    private Hashtable<Integer,Categoria> elencoCategorie;

    private static DatiCategoria istance = null;


    private final static String FB_NODO_CATEGORIE = "Categoria";


    private ValueEventListener listenerEventi;


    //Costruttore DatiCategoria
    public DatiCategoria(){
        elencoCategorie = new Hashtable<>();
    }


    public interface UpdateListener
    {
        void eventiAggiornati();
    }

    public void iniziaOsservazioneEventi(final UpdateListener notifica)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(FB_NODO_CATEGORIE);
        listenerEventi = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                elencoCategorie.clear();
                for (DataSnapshot elemento:dataSnapshot.getChildren())
                {
                    Categoria c = new Categoria(elemento.getValue(String.class),Integer.parseInt(elemento.getKey()));
                    elencoCategorie.put(Integer.parseInt(elemento.getKey()),c);
                }
                notifica.eventiAggiornati();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(listenerEventi);
    }

    public void terminaOsservazioneEventi()
    {
        if(listenerEventi != null)
        {
            FirebaseDatabase.getInstance().getReference(FB_NODO_CATEGORIE).removeEventListener(listenerEventi);
        }
    }


    //Riferimento ai dati
    public static DatiCategoria getIstance(){
        if(istance==null)
            istance = new DatiCategoria();

        return istance;
    }


    public Categoria getCategoria(int numCat){
        return elencoCategorie.get(numCat);
    }

    public ArrayList<Categoria> getElencoCategorie()
    {
        ArrayList<Categoria> risultato = new ArrayList<Categoria>();

        //itero tutti gli elementi per aggiungerli alla lista creata
        for(Map.Entry<Integer, Categoria> elemento : elencoCategorie.entrySet())
        {
            risultato.add(elemento.getValue());
        }
        return risultato;
    }

}
