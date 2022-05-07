package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agent_upwork extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l;
    SharedPreferences sh;
    String wid,donorname;
    int pos;

    ArrayList<String> user,work,date,req_id,donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_upwork);
        l=(ListView)findViewById(R.id.l1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url="http://"+sh.getString("ip","")+":5000/view_work";

        RequestQueue queue= Volley.newRequestQueue(agent_upwork.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
                    work=new ArrayList<>();
                    date=new ArrayList<>();
                    user=new ArrayList<>();
                    req_id=new ArrayList<>();
                    donor=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        work.add("Item:"+jo.getString("item_name")+"\nDescription:"+jo.getString("description"));
                        date.add(jo.getString("date"));
                        user.add(jo.getString("user1")+" "+jo.getString("user2"));
                        req_id.add(jo.getString("request_id"));
                        donor.add(jo.getString("donorf")+""+jo.getString("donorl"));



                    }
//                    ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    l.setAdapter(new custom_req(agent_upwork.this,user,work,date));
                    l.setOnItemClickListener((AdapterView.OnItemClickListener) agent_upwork.this);


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
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };;
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

        pos=i;
        donorname=donor.get(pos);
        wid=req_id.get(pos);
        Intent in=new Intent(getApplicationContext(),agent_upwork1.class);

        in.putExtra("wid",wid);
        in.putExtra("donorname",donorname);
        startActivity(in);
    }
}
