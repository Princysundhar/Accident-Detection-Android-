package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_nearby_policestation extends BaseAdapter {
    String [] pid,station_name,place,post,pin,email,phone_no,photo;
    private Context context;

    public custom_view_nearby_policestation(Context applicationContext, String[] pid, String[] station_name, String[] place, String[] post, String[] pin, String[] email, String[] phone_no, String[] photo) {
        this.context =applicationContext;
        this.pid = pid;
        this.station_name = station_name;
        this.place = place;
        this.post= post;
        this.pin= pin;
        this.email= email;
        this.phone_no= phone_no;
        this.photo= photo;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_nearby_policestation,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        ImageView imageView = (ImageView)gridView.findViewById(R.id.imageView3);
        TextView tv1=(TextView)gridView.findViewById(R.id.textView21);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView23);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView25);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView27);
        TextView tv5=(TextView)gridView.findViewById(R.id.textView29);
        TextView tv6=(TextView)gridView.findViewById(R.id.textView31);




        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);



        tv1.setText(station_name[i]);
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
