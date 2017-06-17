package com.example.hi.vol;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RequestQueue.RequestFinishedListener<Object> {

    ArrayList<Starbean> starbeanarray;
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<String> namearray;
    ArrayList<String> heightarray;
    ArrayList<String> massarray;
    RecyclerView mList;
    RecyclerView mrecycle;
    String name,height,mass;
    RequestQueue mRequestQueue;
    String URL="http://swapi.co/api/people/";
    MeraAdapter adapter;
    LinearLayoutManager manager;
    DB_Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        namearray=new ArrayList<>();
        heightarray=new ArrayList<>();
        massarray=new ArrayList<>();
        mList= (RecyclerView) findViewById(R.id.list_list);

        mRequestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest request=new JsonObjectRequest(URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray array=response.getJSONArray("results");
                    handler=new DB_Handler(MainActivity.this);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        name=object.getString("name");
                        namearray.add(name);
                        height=object.getString("height");
                        heightarray.add(height);
                        mass=object.getString("mass");
                        massarray.add(mass);

                        Log.e(">>>",name);
                        Log.e(">>>",height);
                        Log.e(">>>",mass);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(">>>",error.toString());
            }
        });
       /* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", getJS);
                params.put("height", height);
                params.put("mass", mass);
                contactList.add(params);
                return  params;
            }};*/
        mRequestQueue.add(request);
        mRequestQueue.addRequestFinishedListener(this);
    }


    @Override
    public void onRequestFinished(Request<Object> request) {
        mList= (RecyclerView) findViewById(R.id.list_list);
        adapter = new MeraAdapter(MainActivity.this,namearray,heightarray,massarray);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //GridLayoutManager manager=new GridLayoutManager(MainActivity.this,1);
        mList.setLayoutManager(manager);
        mList.setAdapter(adapter);
        handler.getWritableDatabase();
        handler.insertData(name,height,mass);}


    }
}
