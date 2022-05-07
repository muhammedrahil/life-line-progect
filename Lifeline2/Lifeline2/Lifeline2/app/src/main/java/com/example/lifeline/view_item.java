package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class view_item extends AppCompatActivity {
    Button b;
    Spinner s;
    SharedPreferences sh;
    ArrayList<String> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        b=(Button)findViewById(R.id.button4);
        s=(Spinner)findViewById(R.id.spinner);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url ="http://"+sh.getString("ip","")+":5000/view_items";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(view_item.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    item=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        item.add(jo.getString("item_type"));

                    }
//                    ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    ArrayAdapter<String> adpt = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,item);
                    s.setAdapter(adpt);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=s.getSelectedItem().toString();

                startActivity(new Intent(getApplicationContext(),view_item1.class));

                SharedPreferences.Editor ed=sh.edit();
                ed.putString("type",type);
                ed.commit();


            }
        });



    }
}
