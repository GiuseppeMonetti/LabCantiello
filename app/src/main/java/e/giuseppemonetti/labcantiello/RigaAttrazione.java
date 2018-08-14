package e.giuseppemonetti.labcantiello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Giuseppe Monetti on 13/08/2018.
 */

public class RigaAttrazione extends AppCompatActivity {
    private TextView vTextAttrazione;
    private TextView vTextDistanza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_riga_attrazione);
        vTextAttrazione = findViewById(R.id.textAttrazione);
        vTextDistanza = findViewById(R.id.textDistanza);
    }
}
