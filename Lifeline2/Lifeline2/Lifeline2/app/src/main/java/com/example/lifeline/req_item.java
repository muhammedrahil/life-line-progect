package com.example.lifeline;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.Map;

import static android.widget.AdapterView.*;

public class req_item extends AppCompatActivity implements OnItemClickListener  {
    ListView l;
    SharedPreferences sh;
    ArrayList<String> item,item_id,donor_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_item);

        l=(ListView)findViewById(R.id.l1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url ="http://"+sh.getString("ip","")+":5000/view_item";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(req_item.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    item=new ArrayList<>();
                    item_id=new ArrayList<>();
                    donor_id=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        item.add(jo.getString("item_name"));
                        item_id.add(jo.getString("item_id"));
                        donor_id.add(jo.getString("login_id"));

                    }
                    ArrayAdapter<String> ad=new ArrayAdapter<>(req_item.this,android.R.layout.simple_list_item_1,item);
                    l.setAdapter(ad);
                    l.setOnItemClickListener(req_item.this);


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



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemid=item_id.get(position);
        String donorid=donor_id.get(position);

        Intent i=new Intent(getApplicationContext(),req_iem1.class);
        i.putExtra("itemid",itemid);
        i.putExtra("did",donorid);
        startActivity(i);


//        AlertDialog.Builder ald=new AlertDialog.Builder(req_item.this);
//        ald.setTitle("Select option")
//                .setPositiveButton(" Request ", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        RequestQueue queue = Volley.newRequestQueue(req_item.this);
//                        String url ="http://"+sh.getString("ip","")+":5000/request_item";
//
//                        // Request a string response from the provided URL.
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the response string.
//                                Log.d("+++++++++++++++++",response);
//                                try {
//                                    JSONObject json=new JSONObject(response);
//                                    String res=json.getString("task");
//                                    Toast.makeText(getApplicationContext(),"exp"+res,Toast.LENGTH_LONG).show();
//
//                                    if(res.equals("success"))
//                                    {
//                                        Toast.makeText(getApplicationContext(),"Item Requested",Toast.LENGTH_LONG).show();
//                                        startActivity(new Intent(getApplicationContext(),userhome.class));
//                                    }
//                                    else
//                                    {
//
//                                        Toast.makeText(getApplicationContext(),"Item Request Failed",Toast.LENGTH_LONG).show();
//                                    }
//
//
//                                } catch (JSONException e) {
//                                    Toast.makeText(getApplicationContext(),"exp"+e,Toast.LENGTH_LONG).show();
//
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//
//                                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams()
//                            {
//                                Map<String, String>  params = new HashMap<String, String>();
//                                params.put("item_id", itemid);
//                                params.put("uid", sh.getString("lid",""));
//                                params.put("did",donorid);
//
//                                return params;
//                            }
//                        };
//                        // Add the request to the RequestQueue.
//                        queue.add(stringRequest);
//
//                                         }
//                })
//                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        Intent i=new Intent(getApplicationContext(),userhome.class);
//                        startActivity(i);
//
//                    }
//                });
//
//        AlertDialog al=ald.create();
//        al.show();
//        startDownload(record.get(pos));


    }

    }

