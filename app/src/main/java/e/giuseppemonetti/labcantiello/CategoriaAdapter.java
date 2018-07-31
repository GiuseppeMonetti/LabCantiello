package e.giuseppemonetti.labcantiello;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giuseppe Monetti on 31/07/2018.
 */

public class CategoriaAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Categoria> elencoCategorie;

    public CategoriaAdapter(Context context, ArrayList<Categoria> elencoCategorie) {
        this.context = context;
        this.elencoCategorie = elencoCategorie;
    }


    public void update(ArrayList<Categoria> newList)
    {
        elencoCategorie = newList;
        notifyDataSetChanged();
    }

    // Invocato per ottenere il numero di elementi nella lista
    public int getCount(){return  elencoCategorie.size();}

    // Invocato per ottenere l'iesimo elemento
    public Categoria getItem (int i){return elencoCategorie.get(i); }

    public long getItemId(int i) {
        return 0;
    }

    // Invocato per ottenere la view della riga da visualizzare
    public View getView (int i, View view, ViewGroup viewGroup){

        //Provvedo a costruire il layout nel caso in cui esso non sia stato iniettato
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_categoria, null);
        }

        //ottengo il nome completo corrente
        TextView vNomeCat = view.findViewById(R.id.textCategoria);

        //imposto i valori da visualizzare
        Categoria c = elencoCategorie.get(i);
        vNomeCat.setText(c.getNomeCategoria());


        return view;
    }

    public void setElencoCategorie(ArrayList<Categoria> elencoCategorie)
    {
        this.elencoCategorie=elencoCategorie;
        notifyDataSetChanged();
    }

}
