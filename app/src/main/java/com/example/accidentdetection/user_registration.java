package com.example.accidentdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.Manifest;
public class user_registration extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
//    CalendarView c1;
    RadioGroup r1;
    RadioButton rb1,rb2;
    ImageView img1;
    Button b1;
    SharedPreferences sh;
    String url;
    Bitmap bitmap = null;
    ProgressDialog pd;
    String gender ="male";
    String MobilePattern = "[6-9][0-9]{9}";
    String PinPattern = "[6-9][0-9]{5}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    String password_pattern="[A-Z][a-z][0-9]{3,8}";
    String password_pattern="[A-Za-z0-9]{3,8}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url = sh.getString("url","")+"android_user_registration";

        img1 = findViewById(R.id.imageView);
        e1 = findViewById(R.id.editText4);
        r1 = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
//        c1 = findViewById(R.id.calendarView);
        e2 = findViewById(R.id.editText7);
        e3 = findViewById(R.id.editText8);
        e4 = findViewById(R.id.editText9);
        e5 = findViewById(R.id.editText10);
        e6 = findViewById(R.id.editText11);
        e7 = findViewById(R.id.editText12);
        e8 = findViewById(R.id.editText14);
        e9 = findViewById(R.id.editTextTextPersonName2);            // Date picker
        b1 = findViewById(R.id.button5);


        e9.setOnClickListener(new View.OnClickListener() {              // Date picking

            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int  mDay = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(user_registration.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int a=monthOfYear+1;

                                String mm=String.valueOf(a);
                                String mn="0"+mm;

                                if (mm.length()==1){

                                    e9.setText(year + "-" + (mn) + "-" + dayOfMonth);
                                }
                                else {

                                    e9.setText(year + "-" + (mm) + "-" + dayOfMonth);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString();
                String house_name = e2.getText().toString();
                String place = e3.getText().toString();
                String post = e4.getText().toString();
                String pin = e5.getText().toString();
                String email = e6.getText().toString();
                String contact = e7.getText().toString();
                String password = e8.getText().toString();
                String dob = e9.getText().toString();

                if(rb2.isChecked()){
                    gender ="female";
                }
                int flag = 0;
                if(name.equalsIgnoreCase("")){
                    e1.setError("Enter name");
                    flag++;
                }

                if(house_name.equalsIgnoreCase("")){
                    e2.setError("Enter house name");
                    flag++;
                }
                if(place.equalsIgnoreCase("")){
                    e3.setError("Enter place");
                    flag++;
                }
                if(post.equalsIgnoreCase("")){
                    e4.setError("Enter post");
                    flag++;
                }
                if(!pin.matches(PinPattern)){
                    e5.setError("Enter valid pin");
                    flag++;
                }
                if(!email.matches(emailPattern)){
                    e6.setError("Enter the valid email");
                    flag++;
                }
                if(!contact.matches(MobilePattern)){
                    e7.setError("Enter contact");
                    flag++;
                }
                if(!password.matches(password_pattern)){
                    Toast.makeText(user_registration.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(bitmap==null){
                    Toast.makeText(user_registration.this, "Choose image", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(flag==0){
                    uploadBitmap(name,dob,house_name,place,post,pin,email,contact,password);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                img1.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final String name,final String dob, final String house_name, final String place, final String post,final String pin, final String email,final String contact,final String password) {


        pd = new ProgressDialog(user_registration.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration failed" ,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("name", name);//passing to python
                params.put("dob", dob);//passing to python
                params.put("gender", gender);//passing to python
                params.put("house_name", house_name);//passing to python
                params.put("place", place);
                params.put("post", post);
                params.put("pin", pin);
                params.put("email", email);
                params.put("contact", contact);
                params.put("password", password);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}