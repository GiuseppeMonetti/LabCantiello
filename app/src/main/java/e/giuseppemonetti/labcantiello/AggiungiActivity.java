package e.giuseppemonetti.labcantiello;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

import e.giuseppemonetti.labcantiello.Datasource.DatiCategoria;

public class AggiungiActivity extends AppCompatActivity {

    private FloatingActionButton floatButt;
    private Spinner vSpin;
    private EditText editDesc, editRec, editMail, editNome;
    private TextView tCat, tDesc, tRec, tMail, tNome;


    private String categoria, descrizione, recapito, mail, nome;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);

        floatButt = findViewById(R.id.floatButt);
        editDesc = findViewById(R.id.editDesc);
        editRec = findViewById(R.id.editRec);
        editMail = findViewById(R.id.editMail);
        editNome = findViewById(R.id.editNome);
        tCat = findViewById(R.id.tCat);
        tDesc = findViewById(R.id.tDesc);
        tRec = findViewById(R.id.tRec);
        tMail = findViewById(R.id.tMail);
        tNome = findViewById(R.id.tNome);
        vSpin = (Spinner) findViewById(R.id.spinner);

        //PRENDIAMO LE CATEGORIE E REMPIAMO LO SPINNER
        DatiCategoria dc = DatiCategoria.getIstance();
        ArrayList<Categoria> cat = dc.getElencoCategorie();
        ArrayAdapter<String> spinnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        for(int i = 0; i < cat.size(); i++)
        {
            spinnAdapter.add(cat.get(i).getNomeCategoria());
        }
        spinnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vSpin.setAdapter(spinnAdapter);


        floatButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!prelevaTesto()) {return;}
                DatabaseReference db = database.getReference(getIntent().getStringExtra("CITTA")).child(categoria);
                db.push().setValue(nome);

                DatabaseReference db2 = database.getReference("Coordinate");
                db2.child(nome).child("cat").setValue(trovaIdCat());
                db2.child(nome).child("descrizione").setValue(descrizione);
                db2.child(nome).child("email").setValue(mail);
                db2.child(nome).child("rec").setValue(recapito);
                db2.child(nome).child("lat").setValue(getIntent().getDoubleExtra("LAT",0));
                db2.child(nome).child("lng").setValue(getIntent().getDoubleExtra("LNG",0));

                Log.i("FAMM CAPI","cosa accade");
                Intent i = new Intent();
                setResult(RESULT_OK,i);
                finish();
            }
        });



        editMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        editRec.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        editDesc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        editNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.i("CIAO",Boolean.toString(b));
                if(!b)
                {
                    InputMethodManager imm =  (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }



    public int trovaIdCat()
    {
        if(categoria.equals("Bar & Pub")) return 0;
        if(categoria.equals("Ristoranti & Pizzerie")) return 1;
        if(categoria.equals("Cultura & Spettacolo")) return 2;
        else return 3;
    }

    public boolean prelevaTesto()
    {
        Toast t;
        categoria = vSpin.getSelectedItem().toString();
        vSpin.getFocusedChild();
        descrizione = editDesc.getText().toString();
        recapito = editRec.getText().toString();
        mail = editMail.getText().toString();
        nome = editNome.getText().toString();
        if(descrizione.isEmpty() || categoria.isEmpty() || recapito.isEmpty() || mail.isEmpty() || nome.isEmpty())
        {
            t = Toast.makeText(this,R.string.err,Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        return true;
    }

}
