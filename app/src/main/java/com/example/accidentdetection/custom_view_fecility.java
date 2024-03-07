package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class custom_view_fecility extends BaseAdapter {
    String [] fid,fecility_type,details;
    private Context context;
    public custom_view_fecility(Context applicationContext, String[] fid, String[] fecility_type, String[] details) {
        this.context = applicationContext;
        this.fid = fid;
        this.fecility_type = fecility_type;
        this.details = details;
    }


    @Override
    public int getCount() {
        return fecility_type.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_fecility,null);//same class name

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView18);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView44);
//        EditText e1 =(EditText)gridView.findViewById(R.id.textView44) ;



        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);



        tv1.setText(fecility_type[i]);
        tv2.setText(details[i]);


        return gridView;
    }
}
