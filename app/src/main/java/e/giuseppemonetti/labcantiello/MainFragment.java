package e.giuseppemonetti.labcantiello;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import e.giuseppemonetti.labcantiello.Datasource.DatiCategoria;


/**
 * Created by Giuseppe Monetti on 30/07/2018.
 */

public class MainFragment extends Fragment {

    private ListView listaCat;
    private DatiCategoria dc;
    private CategoriaAdapter catAdapter;

    public MainFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);


        listaCat = view.findViewById(R.id.listaCategorie);

        dc = DatiCategoria.getIstance();

        catAdapter = new CategoriaAdapter(this.getContext(),dc.getElencoCategorie());


        dc.iniziaOsservazioneEventi(new DatiCategoria.UpdateListener() {
            @Override
            public void eventiAggiornati() {
                catAdapter.update(dc.getElencoCategorie());
            }
        });

        listaCat.setAdapter(catAdapter);

        listaCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity activity = (MainActivity) getActivity();
                String city = activity.getCity();
                if(city == null)
                {
                    Toast t = Toast.makeText(getContext(),getResources().getString(R.string.msg_findposition),Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }

                Intent intent = new Intent(getContext(),ListActivity.class);
                intent.putExtra("LOCALITY", city);
                TextView t = view.findViewById(R.id.textCategoria);
                intent.putExtra("CATEGORIA",t.getText());
                startActivity(intent);
            }
        });

        return view;
    }

}
