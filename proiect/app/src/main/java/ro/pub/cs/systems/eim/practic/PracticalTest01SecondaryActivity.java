package ro.pub.cs.systems.eim.practic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    EditText suma;
    Button ok, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        suma = findViewById(R.id.suma);
        ok = findViewById(R.id.ok);
        cancel = findViewById(R.id.cancel);

        Intent intent = this.getIntent();
        String sumaFromIntent = intent.getStringExtra("suma");
        suma.setText(sumaFromIntent);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01SecondaryActivity.this, PracticalTest01MainActivity.class);
                intent.putExtra("val1", "OK"); //Optional parameters
                PracticalTest01SecondaryActivity.this.startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01SecondaryActivity.this, PracticalTest01MainActivity.class);
                intent.putExtra("val1", "Cancel"); //Optional parameters
                PracticalTest01SecondaryActivity.this.startActivity(intent);
            }
        });
    }
}
