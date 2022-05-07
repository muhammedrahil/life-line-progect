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
import android.widget.TextView;
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

public class agent_upwork1 extends AppCompatActivity {
    EditText e;
    Button b;
    TextView t1,t2;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_upwork1);
        e=(EditText)findViewById(R.id.status);
        b=(Button)findViewById(R.id.button12);
        t1=(TextView)findViewById(R.id.textView16);
        t2=(TextView)findViewById(R.id.textView23);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String wid=getIntent().getStringExtra("wid");
        String donor=getIntent().getStringExtra("donorname");
        t1.setText(wid);
        t2.setText(donor);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = e.getText().toString();
                if (status.equalsIgnoreCase("")) {
                    e.setError("Enter Work Status!!");
                } else {

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(agent_upwork1.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/update_status";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");


                                if (res.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Work Updated", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), agent_upwork.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Work Update failed!!!", Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "exp" + e, Toast.LENGTH_LONG).show();
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
                            params.put("wid", wid);
                            params.put("status", status);

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