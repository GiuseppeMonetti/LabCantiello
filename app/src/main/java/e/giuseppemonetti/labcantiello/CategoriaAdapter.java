package e.giuseppemonetti.labcantiello;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

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
        ImageView vIcona = (ImageView) view.findViewById(R.id.icona);

        //imposto i valori da visualizzare
        for(Categoria c : elencoCategorie)
        {
            if(c.getKey()== i)
            {
                vNomeCat.setText(c.getNomeCategoria());
                Picasso.get().load(c.getIconURL()).into(vIcona);
            }

        }

        return view;
    }

    public void setElencoCategorie(ArrayList<Categoria> elencoCategorie)
    {
        this.elencoCategorie=elencoCategorie;
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
