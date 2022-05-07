package com.example.lifeline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class agent_view_work extends AppCompatActivity {
    ListView l;
    SharedPreferences sh;
    ArrayList<String> user,work,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_view_work);

        l=(ListView)findViewById(R.id.l1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url ="http://"+sh.getString("ip","")+":5000/view_work";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(agent_view_work.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    work=new ArrayList<>();
                    date=new ArrayList<>();
                    user=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        work.add("Item:"+jo.getString("item_name")+"\nDescription:"+jo.getString("description"));
                        date.add(jo.getString("date"));
                        user.add(jo.getString("user1")+" "+jo.getString("user2"));


                    }
//                    ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    l.setAdapter(new custom_req(agent_view_work.this,user,work,date));


                } catch (JSONException e) {
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
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };;
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}
