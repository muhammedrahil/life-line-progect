package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
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

public class register extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    Button b;
    SharedPreferences sp;

    String ip="",fn,ln,place,post,pin,dist,ph,email,uname,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1=(EditText)findViewById(R.id.edfname);
        e2=(EditText)findViewById(R.id.edlname);
        e3=(EditText)findViewById(R.id.edplace);
        e4=(EditText)findViewById(R.id.edpost);
        e5=(EditText)findViewById(R.id.edpin);
        e6=(EditText)findViewById(R.id.eddist);
        e7=(EditText)findViewById(R.id.edph);
        e8=(EditText)findViewById(R.id.edemail);
        e9=(EditText)findViewById(R.id.editTextTextPersonName2);
        e10=(EditText)findViewById(R.id.editTextTextPersonName3);
        b=(Button)findViewById(R.id.button6);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fn=e1.getText().toString();
                ln=e2.getText().toString();
                place=e3.getText().toString();
                post=e4.getText().toString();
                pin=e5.getText().toString();
                dist=e6.getText().toString();
                ph=e7.getText().toString();
                email=e8.getText().toString();
                uname=e9.getText().toString();
                pass=e10.getText().toString();
                if(fn.equalsIgnoreCase(""))
                {
                    e1.setError("Enter first name");
                }
                else if(!fn.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                }
                else if(ln.equalsIgnoreCase(""))
                {
                    e2.setError("Enter last name");
                }
                else if(!ln.matches("^[a-zA-Z]*$"))
                {
                    e2.setError("characters allowed");
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e3.setError("Enter place");
                }

                else if(post.equalsIgnoreCase(""))
                {
                    e4.setError("Enter post");
                }
                else if(pin.equalsIgnoreCase(""))
                {
                    e5.setError("Enter pin");
                }
                else if(pin.length()!=6)
                {
                    e5.setError("Invalid pincode");
                }

                else if (dist.equalsIgnoreCase("")) {
                    e6.setError("Enter Your District");
                }else if(ph.equalsIgnoreCase(""))
                {
                    e7.setError("Enter phoneno");
                }
                else if(ph.length()!=10)
                {
                    e7.setError("Invalid phoneno");
                }

                else if(email.equalsIgnoreCase(""))
                {
                    e8.setError("Enter email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e8.setError("Enter Valid Email");
                    e8.requestFocus();
                }
                else if(uname.equalsIgnoreCase(""))
                {
                    e8.setError("Enter username");
                }
                else if (pass.length()<6) {
                    e10.setError("min 6 numbers required");
                }
                else {


                    RequestQueue queue = Volley.newRequestQueue(register.this);
                    String url = "http://" + sp.getString("ip", "") + ":5000/registration";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
                                if(res.equals("invalid")){
                                    Toast.makeText(getApplicationContext(), "registration failed", Toast.LENGTH_LONG).show();
//                                    startActivity(new Intent(getApplicationContext(), register.class));
//                                    e9.setError("Username Already Existing");
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            } catch (JSONException e) {
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
                            params.put("fname", fn);
                            params.put("lname", ln);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("dist", dist);
                            params.put("phone", ph);
                            params.put("email", email);
                            params.put("username", uname);
                            params.put("password", pass);
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