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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_nearby_hospital extends BaseAdapter {
    private Context context;
    String [] hid,name,photo,place,post,pin,lattitude,longitude,email,phone_no;


    public custom_view_nearby_hospital(Context applicationContext, String[] hid,String[] name, String[] photo, String[] place, String[] post, String[] pin,String[] lattitude,String[] longittude ,String[] email, String[] phone_no) {
        this.context = applicationContext;
        this.hid = hid;
        this.name = name;
        this.photo = photo;
        this.place = place;
        this.post = post;
        this.pin = pin;
        this.lattitude = lattitude;
        this.longitude = longittude;
        this.email = email;
        this.phone_no = phone_no;
    }

    @Override
    public int getCount() {
        return phone_no.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_nearby_hospital,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView2);
        TextView tv1=(TextView)gridView.findViewById(R.id.textView3);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView6);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView8);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView10);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView33);
        TextView tv6=(TextView)gridView.findViewById(R.id.textView35);

        Button b1 = (Button)gridView.findViewById(R.id.button4);            // Location
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik=(int)view.getTag();
                String url = "http://maps.google.com/?q=" + lattitude[ik] + "," + longitude[ik];
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        Button b2 = (Button)gridView.findViewById(R.id.button6);        // view doctor
        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("hid",hid[pos]);

                ed.commit();

                Intent i = new Intent(context,view_doctor.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        Button b3 = (Button)gridView.findViewById(R.id.button7);    // view fecility
        b3.setTag(i);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("hid",hid[pos]);

                ed.commit();

                Intent i = new Intent(context,view_fecility.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(place[i]);
        tv3.setText(post[i]);
        tv4.setText(pin[i]);
        tv5.setText(email[i]);
        tv6.setText(phone_no[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":8000" + photo[i];    // For Image
        Picasso.with(context).load(url).transform(new CircleTransform()).into(imageView);//circle
        return gridView;
    }
}
