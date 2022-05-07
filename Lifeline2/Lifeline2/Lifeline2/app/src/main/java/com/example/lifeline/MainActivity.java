package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    SharedPreferences sp;

    String ip="",uname="",pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editTextTextPersonName);
        e2=(EditText)findViewById(R.id.editTextTextPassword);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button5);

        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=e1.getText().toString();
                pass=e2.getText().toString();

                if(uname.equals("")){
                    e1.setError("Enter Username");
                }
                else if(pass.equals("")){
                    e2.setError("Enter Password");
                }
                else
                {
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    String url ="http://"+ sp.getString("ip","")+":5000/login";
//                    Toast.makeText(getApplicationContext(),"type"+sp.getString("ip",""),Toast.LENGTH_LONG).show();
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++",response);
                            try {
                                JSONObject json=new JSONObject(response);
                                String res=json.getString("result");

//                                Toast.makeText(getApplicationContext(),"type"+type,Toast.LENGTH_LONG).show();
                                if(res.equals("invalid")){
                                    Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_LONG).show();

                                }
                                else {
                                    String arr[]=res.split("#");
                                    String id=arr[0];
                                    String type=arr[1];
                                    if (type.equals("user")) {
                                        SharedPreferences.Editor ed1 = sp.edit();
                                        ed1.putString("lid", id);
                                        ed1.commit();
                                        startActivity(new Intent(getApplicationContext(), userhome.class));
                                    } else if (type.equals("agent")) {

                                        SharedPreferences.Editor ed1 = sp.edit();
                                        ed1.putString("lid", id);
                                        ed1.commit();
                                        startActivity(new Intent(getApplicationContext(), agent_home.class));
                                    }

                                }


                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),"exp"+e,Toast.LENGTH_LONG).show();

                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("usernm", uname);
                            params.put("pass", pass);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }}
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder ald = new AlertDialog.Builder(MainActivity.this);
        ald.setTitle("Do you want to Exit")
                .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in = new Intent(Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog al = ald.create();
        al.show();
    }
}
