package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ip_page extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_page);
        e1 = findViewById(R.id.editText);
        b1 = findViewById(R.id.button);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(sh.getString("ip",""));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipaddress = e1.getText().toString();
                int flag =0;
                if(ipaddress.equalsIgnoreCase("")){
//                    Toast.makeText(ip_page.this, "Enter Ip address", Toast.LENGTH_SHORT).show();
                    e1.setError("Enter ip");
                    flag++;
                }
                if(flag==0) {
                    String url = "http://" + ipaddress + ":8000/";
                    Toast.makeText(ip_page.this, "welcome", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ipaddress);
                    ed.putString("url", url);
                    ed.commit();

                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }


            }
        });
    }
}