package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class req_iem1 extends AppCompatActivity {
    EditText e;
    Button b;
    SharedPreferences sh;
    String item_id="";
    String did;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_iem1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e=(EditText)findViewById(R.id.editTextTextMultiLine);
        b=(Button)findViewById(R.id.button13);
        item_id=getIntent().getStringExtra("itemid");
        did=getIntent().getStringExtra("did");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String decrip = e.getText().toString();
                if (decrip.equalsIgnoreCase("")) {
                    e.setError("Enter Your Description");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(req_iem1.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/request_item";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
//                                Toast.makeText(getApplicationContext(), "exp" + res, Toast.LENGTH_LONG).show();

                                if (res.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Item Requested", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), userhome.class));
                                } else {

                                    Toast.makeText(getApplicationContext(), "Item Request Failed", Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "exp" + e, Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("item_id", item_id);
                            params.put("uid", sh.getString("lid", ""));
                            params.put("did", did);
                            params.put("desciption", decrip);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                }
            }
        });


    }
}