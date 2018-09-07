package e.giuseppemonetti.labcantiello.Datasource;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Attr;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import e.giuseppemonetti.labcantiello.Attrazione;



/**
 * Created by Giuseppe Monetti on 13/08/2018.
 */
public class DatiAttrazione extends Fragment {


    public interface UpdateListener
    {
        void listAttrazioniAggiornate();
    }

    private String city;
    private String categoria;


    //abbiamo creato il DatiAttrazione
    private ArrayList<Attrazione> elencoAttrazioni;
    private ArrayList<Attrazione> attrazioniPerMap;

    private static e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione istance = null;



    private ValueEventListener listener;
    private ValueEventListener listener2;


    //Costruttore DatiAttrazione
    public DatiAttrazione(){
        elencoAttrazioni = new ArrayList<>();
        attrazioniPerMap = new ArrayList<>();
    }


    public void iniziaOsservazioneEventi(final e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione.UpdateListener notifica)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                elencoAttrazioni.clear();
                for (DataSnapshot elemento:dataSnapshot.child(city).child(categoria).getChildren())
                {
                    String nome = elemento.getValue(String.class);
                    String descrizione = dataSnapshot.child("Coordinate").child(nome).child("descrizione").getValue(String.class);
                    String email = dataSnapshot.child("Coordinate").child(nome).child("email").getValue(String.class);
                    String recapito = dataSnapshot.child("Coordinate").child(nome).child("rec").getValue(String.class);
                    Attrazione a = new Attrazione(nome,descrizione,recapito,categoria);
                    elencoAttrazioni.add(a);
                }
                for(int i = 0; i < elencoAttrazioni.size();i++)
                {
                    double d = (dataSnapshot.child("Coordinate").child(elencoAttrazioni.get(i).getNome()).child("lat").getValue(Double.class));
                    elencoAttrazioni.get(i).setLat(d);
                    d = (dataSnapshot.child("Coordinate").child(elencoAttrazioni.get(i).getNome()).child("lng").getValue(Double.class));
                    elencoAttrazioni.get(i).setLng(d);
                }

                notifica.listAttrazioniAggiornate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void terminaOsservazioneEventi()
    {
        if(listener != null)
        {
            FirebaseDatabase.getInstance().getReference().removeEventListener(listener);
        }
    }








    public void iniziaOsservazioneMap(final Location l1, final e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione.UpdateListener notifica)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attrazioniPerMap.clear();
                Location l2;
                for(DataSnapshot elemento : dataSnapshot.child("Coordinate").getChildren())
                {
                    l2 = new Location("");
                    l2.setLatitude(elemento.child("lat").getValue(double.class));
                    l2.setLongitude(elemento.child("lng").getValue(double.class));
                    if(l2.distanceTo(l1)<2000)
                    {
                        attrazioniPerMap.add(new Attrazione(elemento.getKey(),l2.getLatitude(),l2.getLongitude(),dataSnapshot.child("Categoria").child(Integer.toString(elemento.child("cat").getValue(Integer.class))).child("nome").getValue(String.class)));
                    }
                }
                notifica.listAttrazioniAggiornate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
/*
    public void aggiornaAttrazioniPerMap(final Location l1, final e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione.UpdateListener n)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attrazioniPerMap.clear();
                Location l2;
                for(DataSnapshot elemento : dataSnapshot.child("Coordinate").getChildren())
                {
                    l2 = new Location("");
                    l2.setLatitude(elemento.child("lat").getValue(double.class));
                    l2.setLongitude(elemento.child("lng").getValue(double.class));
                    if(l2.distanceTo(l1)<2000)
                    {
                        attrazioniPerMap.add(new Attrazione(elemento.getKey(),l2.getLatitude(),l2.getLongitude(),dataSnapshot.child("Categoria").child(Long.toString(elemento.child("cat").getValue(Long.class))).getValue(String.class)));
                    }
                }
                n.listAttrazioniAggiornate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/



    public List<Attrazione> getAttrazioniPerMap()
    {
        return attrazioniPerMap;
    }








    //Riferimento ai dati
    public static e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione getIstance(){
        if(istance==null)
            istance = new e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione();

        return istance;
    }


    public ArrayList<Attrazione> getElencoAttrazioni() {return elencoAttrazioni;}

    public void setCity(String s){city = s;}
    public void setCategoria(String s){categoria = s;}

}