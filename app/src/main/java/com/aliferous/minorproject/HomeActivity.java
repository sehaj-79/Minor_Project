package com.aliferous.minorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class HomeActivity extends AppCompatActivity {

    Spinner s1, s2;
    Button button;
    String string1 = "", string2 = "";
    int node1 = 0, node2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        s1 = findViewById(R.id.spinner1);
        s2 = findViewById(R.id.spinner2);
        button = findViewById(R.id.button);
        button.setEnabled(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity2.class);
                if (string1.equals("AB1 Entrance"))
                    node1 = 24;
                else if (string1.equals("DSW"))
                    node1 = 12;
                else if (string1.equals("Cheffies"))
                    node1 = 29;
                else if (string1.equals("ICICI Bank"))
                    node1 = 29;
                else if (string1.equals("AB1 Exit"))
                    node1 = 1;
                else if (string1.equals("Lift Lobby"))
                    node1 = 2;
                else if (string1.equals("Washroom"))
                    node1 = 4;

                if (string2.equals("AB1 Entrance"))
                    node2 = 24;
                else if (string2.equals("DSW"))
                    node2 = 12;
                else if (string2.equals("Cheffies"))
                    node2 = 29;
                else if (string2.equals("ICICI Bank"))
                    node2 = 29;
                else if (string2.equals("AB1 Exit"))
                    node2 = 1;
                else if (string2.equals("Lift Lobby"))
                    node2 = 2;
                else if (string2.equals("Washroom"))
                    node2 = 4;
                intent.putExtra("node1", node1);
                intent.putExtra("node2", node2);
                startActivity(intent);
            }
        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                string1 = adapterView.getSelectedItem().toString();
                checkSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                string2 = adapterView.getSelectedItem().toString();
                checkSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void checkSpinners(){
        if (!(string1.isEmpty() || string2.isEmpty() || string1.equals("Enter your current location") || string2.equals("Enter your desired destination"))){
            button.setAlpha(1.0F);
            button.setEnabled(true);
        }
        else {
            button.setAlpha(0.3F);
            button.setEnabled(false);
        }
    }
}