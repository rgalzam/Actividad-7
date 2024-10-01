package com.tecmilenio.topic8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtTitle, txtBody;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txtUser);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeerWs();
                //enviarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(), txtUser.getText().toString());
                //eliminarWs();
            }
        });

    }

    private void LeerWs() {
        String url = "https://jsonplaceholder.typicode.com/posts/2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Process the response here
                        // For example, converting the response to a JSONObject or displaying it
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String title = jsonObject.getString("title");
                            String body = jsonObject.getString("body");
                            String userId = jsonObject.getString("userId");

                            // Display the received values in a Toast message
                            Toast.makeText(MainActivity.this,
                                    "Title: " + title + "\nBody: " + body + "\nUserId: " + userId,
                                    Toast.LENGTH_LONG).show();

                            txtUser.setText(userId);
                            txtTitle.setText(title);
                            txtBody.setText(body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Log.e("Error", error.getMessage());
                    }
                });

        // Add the request to the request queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void enviarWs(final String title, final String body, final String userId) {

        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "RESULTADO POST = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void actualizarWs(final String title, final String body, final String userId) {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postResquest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "RESULTADO = " + response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", "1");
                params.put("title", title);
                params.put("body", body);
                params.put("userId", userId);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }

    private void eliminarWs() {

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postResquest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(MainActivity.this, "RESULTADO = " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
}