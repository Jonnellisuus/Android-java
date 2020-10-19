package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class SearchCompany extends AppCompatActivity {
    private static final String JSON_URL = "http://avoindata.prh.fi/bis/v1?totalResults=false&maxResults=10&resultsFrom=0&companyRegistrationFrom=1900-01-01";
    RecyclerView recyclerView;
    List<Company> companyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_company);

        recyclerView = findViewById(R.id.companyRecyclerView);
        companyList = new ArrayList<>();

        loadCompanyList();
    }

    private void loadCompanyList() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Creating a string request to send request to the url.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            // Getting the whole json object from the response.
                            JSONObject jsonObject = new JSONObject(response);

                            /*
                            The wanted data is in an array named "results" and that array is
                            inside an object. Getting a json array.
                             */
                            JSONArray companyArray = jsonObject.getJSONArray("results");

                            // Looping through all the elements of the json array.
                            for (int i = 0; i < companyArray.length(); i++) {

                                // Getting the json object of the particular index inside the array.
                                JSONObject companyObject = companyArray.getJSONObject(i);

                                // Creating a company object and giving them the values from json object.
                                Company company = new Company(companyObject.getString("name"));

                                // adding the company to a list.
                                companyList.add(company);
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            SearchCompanyAdapter searchCompanyAdapter = new SearchCompanyAdapter(getApplicationContext(), companyList);
                            recyclerView.setAdapter(searchCompanyAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}