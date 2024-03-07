package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_doctor extends BaseAdapter {
    String [] did,name,designation,email,phone_no;
    private Context context;
    public custom_view_doctor(Context applicationContext, String[] did, String[] name, String[] designation, String[] email, String[] phone_no) {
        this.context = applicationContext;
        this.did = did;
        this.name = name;
        this.designation = designation;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_doctor,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView4);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView12);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView14);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView16);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(designation[i]);
        tv3.setText(email[i]);
        tv4.setText(phone_no[i]);

        return gridView;
    }
}
