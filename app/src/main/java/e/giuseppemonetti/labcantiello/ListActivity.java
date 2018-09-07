package e.giuseppemonetti.labcantiello;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Semaphore;

import e.giuseppemonetti.labcantiello.Datasource.DatiAttrazione;
import e.giuseppemonetti.labcantiello.Datasource.DatiCategoria;

public class ListActivity extends AppCompatActivity {

    private ListView listaAttrazioni;
    private AttrazioneAdapter actAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listaAttrazioni = findViewById(R.id.attrazioniList);

        final DatiAttrazione da = DatiAttrazione.getIstance();

        final String locality = getIntent().getStringExtra("LOCALITY");
        String categoria = getIntent().getStringExtra("CATEGORIA");

        da.setCategoria(categoria);
        da.setCity(locality);

        actAdapter = new AttrazioneAdapter(this,da.getElencoAttrazioni());


        da.iniziaOsservazioneEventi(new DatiAttrazione.UpdateListener() {
            @Override
            public void listAttrazioniAggiornate() {
                actAdapter.update(da.getElencoAttrazioni());
            }
        });

        listaAttrazioni.setAdapter(actAdapter);
        listaAttrazioni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textAttrazione = view.findViewById(R.id.textAttrazione);
                Intent intent = new Intent(getApplicationContext(),DettaglioActivity.class);
                intent.putExtra("nomeattr",textAttrazione.getText());
                startActivity(intent);
            }
        });

    }
}
