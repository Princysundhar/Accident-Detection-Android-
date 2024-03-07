package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class custom_view_accident_detection extends BaseAdapter {
    private Context context;
    String [] aid,lattitude,longitude,place,date,time,status;
    SharedPreferences sh;
    String url;

    public custom_view_accident_detection(Context applicationContext, String[] aid, String[] lattitude, String[] longitude, String[] place, String[] date, String[] time, String[] status) {
        this.context =applicationContext;
        this.aid = aid;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.place = place;
        this.date = date;
        this.time = time;
        this.status = status;
    }


    @Override
    public int getCount() {
        return place.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_accident_detection,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView37);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView39);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView41);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView43);

        Button b1 =(Button)gridView.findViewById(R.id.button8);
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {          // Locate
            @Override
            public void onClick(View view) {
                int ik=(int)view.getTag();
                String url = "http://maps.google.com/?q=" + lattitude[ik] + "," + longitude[ik];
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        Button b2 = (Button)gridView.findViewById(R.id.button9);        // Delete
        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int pos=(int)view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context);
                sh.getString("ip","");
                url = sh.getString("url","")+"android_delete_accident";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context.getApplicationContext(),view_accident_detction.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(i);


                                    } else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("aid", aid[pos]);//passing to python



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
        });




        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);



        tv1.setText(place[i]);
        tv2.setText(date[i]);
        tv3.setText(time[i]);
        tv4.setText(status[i]);



        return gridView;
    }
}
