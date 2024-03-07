package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_accident_detction extends AppCompatActivity {
    String [] aid,lattitude,longitude,place,date,time,status;
    ListView li;
    SharedPreferences sh;
    String url;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accident_detction);
        li = findViewById(R.id.listview);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url = sh.getString("url","")+"android_view_accident_detection";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                aid = new String[js.length()];
                                lattitude = new String[js.length()];
                                longitude = new String[js.length()];
                                place = new String[js.length()];
                                date = new String[js.length()];
                                time = new String[js.length()];
                                status = new String[js.length()];



                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    aid[i] = u.getString("aid"); // dbcolumn name in double quotes
                                    lattitude[i] = u.getString("lattitude");
                                    longitude[i] = u.getString("longitude");
                                    place[i] = u.getString("place");
                                    date[i] = u.getString("date");
                                    time[i] = u.getString("time");
                                    status[i] = u.getString("status");


                                }
                                li.setAdapter(new custom_view_accident_detection(getApplicationContext(),aid,lattitude,longitude,place,date,time,status));//custom_view_service.xml and li is the listview object


                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("lid", sh.getString("lid",""));//passing to python
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);



    }
}