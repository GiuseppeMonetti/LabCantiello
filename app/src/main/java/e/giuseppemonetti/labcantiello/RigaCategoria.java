package e.giuseppemonetti.labcantiello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Giuseppe Monetti on 31/07/2018.
 */

public class RigaCategoria extends AppCompatActivity {



    private TextView vTextCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_categoria);
        vTextCategoria = findViewById(R.id.textCategoria);
    }
}
