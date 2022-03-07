package com.example.jodaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String>datos =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewData);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);
        listadatos();
    }
    public void listadatos(){
        String URL = "https://jsonplaceholder.typicode.com/users";
        JsonArrayRequest Users = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    parseJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(Users);
    }
    public void parseJSON(JSONArray myJSON)throws JSONException{
        for (int i=0;i<myJSON.length();i++){
            JSONObject jsonObject = null;
            Post data = new Post();
            jsonObject = myJSON.getJSONObject(i);
            data.setId(jsonObject.getInt("id"));
            data.setName(jsonObject.getString("name"));
            data.setEmail(jsonObject.getString("email"));
            datos.add(data.getEmail());
        }
        arrayAdapter.notifyDataSetChanged();
    }
}