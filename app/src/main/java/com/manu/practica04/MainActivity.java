package com.manu.practica04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cargarUsuarios();

        loadUsers();
    }

    private void cargarUsuarios() {
        TextView tv_mensaje = (TextView) findViewById(R.id.tv_mensaje);
        tv_mensaje.setText("");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objeto = new JSONObject(response.get(i).toString());
                                tv_mensaje.append("Nombre: " + objeto.getString("name") + " Dirección: " + objeto.getString("email") + "\n");
                                tv_mensaje.append("--------------------------------------------------\n");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv_mensaje.setText("Lo siento hubo un error");
                    }
                });

        queue.add(jsonRequest);
    }

    public void loadUsers() {
        ListView lv_users = (ListView) findViewById(R.id.lv_users);
        ArrayList<String> usersInfo = new ArrayList<>();

        TextView tv_mensaje = (TextView) findViewById(R.id.tv_mensaje);
        tv_mensaje.setText("");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject objeto = new JSONObject(response.get(i).toString());
                                String userInfo = "Nombre: " + objeto.getString("name") + "\n Dirección: " + objeto.getString("email");

                                usersInfo.add(userInfo);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String userInfo = "Lo siento hubo un error";

                        usersInfo.add(userInfo);
                    }
                });

        queue.add(request);

        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, usersInfo);

        lv_users.setAdapter(adapter);
    }
}