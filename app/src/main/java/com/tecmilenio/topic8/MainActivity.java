package com.tecmilenio.topic8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // la lista para los postq

    ListView listViewPosts;
    EditText etPostId, etUpdateId, etUpdateTitle, etUpdateBody;
    TextView tvPostId, tvPostTitle, tvPostBody;
    Button btnBuscar, btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewPosts = findViewById(R.id.listViewPosts);
        etPostId = findViewById(R.id.etPostId);
        etUpdateId = findViewById(R.id.etUpdateId);
        etUpdateTitle = findViewById(R.id.etUpdateTitle);
        etUpdateBody = findViewById(R.id.etUpdateBody);
        tvPostId = findViewById(R.id.tvPostId);
        tvPostTitle = findViewById(R.id.tvPostTitle);
        tvPostBody = findViewById(R.id.tvPostBody);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnActualizar = findViewById(R.id.btnActualizar);

        // aquí pos carga los posts

        getAllRequests();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPost(etPostId.getText().toString());
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost(etUpdateId.getText().toString(), etUpdateTitle.getText().toString(), etUpdateBody.getText().toString());
            }
        });
    }

    private void getAllRequests() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> posts = new ArrayList<>();
                        try {
                            for (int i = 0; i < 10; i++) {  // Limitar a 10 posts
                                JSONObject post = response.getJSONObject(i);
                                String title = post.getString("title");
                                posts.add(title);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, posts);
                            listViewPosts.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(MainActivity.this, "Error al obtener los posts", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void getPost(String postId) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject post = new JSONObject(response);
                            String id = post.getString("id");
                            String title = post.getString("title");
                            String body = post.getString("body");

                            tvPostId.setText("ID: " + id);
                            tvPostTitle.setText("Title: " + title);
                            tvPostBody.setText("Body: " + body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(MainActivity.this, "Error fetching post", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void updatePost(final String id, final String title, final String body) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        Toast.makeText(MainActivity.this, "PATCH con id "+id , Toast.LENGTH_LONG).show();

        StringRequest putRequest = new StringRequest(Request.Method.PATCH, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Post actualizado jeje", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                        Toast.makeText(MainActivity.this, "Error actualizando post", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("body", body);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(putRequest);
    }
}
