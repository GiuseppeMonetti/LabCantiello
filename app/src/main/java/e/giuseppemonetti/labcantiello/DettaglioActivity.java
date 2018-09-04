package e.giuseppemonetti.labcantiello;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DettaglioActivity extends AppCompatActivity {
    private TextView vTitolo;
    private TextView vDescrizione;
    private TextView vNumero;
    private TextView vEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio);
        vTitolo = findViewById(R.id.titoloText);
        vDescrizione = findViewById(R.id.descrizioneText);
        vNumero = findViewById(R.id.recapitoText);
        vEmail = findViewById(R.id.emailText);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String nomeattrazione = getIntent().getStringExtra("nomeattr");
        DatabaseReference ref = database.getReference().child("Coordinate");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vDescrizione.setText(dataSnapshot.child(nomeattrazione).child("descrizione").getValue(String.class));
                vNumero.setText(dataSnapshot.child(nomeattrazione).child("rec").getValue(String.class));
                vEmail.setText(dataSnapshot.child(nomeattrazione).child("email").getValue(String.class));
                vTitolo.setText(nomeattrazione);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
