package ro.pub.cs.systems.eim.practic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button changeActivity;
    Button leftButton;
    Button rightButton;
    Button startService;

    EditText editText1;
    EditText editText2;

    private int serviceStatus = STOPPED;

    private final static int MAX_NR = 10;
    private final static int STOPPED = 0;
    private final static int STARTED = 1;

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("wow", intent.getStringExtra("message"));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        for (int index = 0; index < 3; index++) {
            intentFilter.addAction(String.valueOf(index + 1));
        }

        changeActivity = findViewById(R.id.changeActivity);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        startService = findViewById(R.id.service);

        if (savedInstanceState != null)
        {
           if (savedInstanceState.getString("editText1") != null)
           {
                editText1.setText(savedInstanceState.getString("editText1"));
           } else {
               editText1.setText(String.valueOf(0));
           }

            if (savedInstanceState.getString("editText2") != null)
            {
                editText1.setText(savedInstanceState.getString("editText2"));
            } else {
                editText2.setText(String.valueOf(0));
            }
        }
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.valueOf(editText1.getText().toString());
                value ++;
                editText1.setText(String.valueOf(value));
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.valueOf(editText2.getText().toString());
                value ++;
                editText2.setText(String.valueOf(value));
            }
        });

        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01MainActivity.this, PracticalTest01SecondaryActivity.class);
                int suma = Integer.valueOf(editText1.getText().toString().trim()) + Integer.valueOf(editText2.getText().toString().trim());
                intent.putExtra("suma", String.valueOf(suma)); //Optional parameters
                intent.putExtra("val1", editText1.getText().toString());
                intent.putExtra("val2", editText2.getText().toString());
                Log.d("wow", String.valueOf(suma));
                PracticalTest01MainActivity.this.startActivity(intent);
            }
        });

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val1 = Integer.parseInt(editText1.getText().toString());
                int val2 = Integer.parseInt(editText2.getText().toString());

                if (val1 + val2 > MAX_NR && serviceStatus == STOPPED) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                    intent.putExtra("val1", String.valueOf(val1));
                    intent.putExtra("val2", String.valueOf(val2));
                    getApplicationContext().startService(intent);
                    serviceStatus = STARTED;
                }
            }
        });

        Intent intent = this.getIntent();
        if (intent != null) {
            String result = intent.getStringExtra("val1");
            if (result != null) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("editText1", editText1.getText().toString());
        savedInstanceState.putString("editText2", editText1.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString("editText1") != null)
            {
                editText1.setText(savedInstanceState.getString("editText1"));
            } else {
                editText1.setText(String.valueOf(0));
            }

            if (savedInstanceState.getString("editText2") != null)
            {
                editText2.setText(savedInstanceState.getString("editText2"));
            } else {
                editText2.setText(String.valueOf(0));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
