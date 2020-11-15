package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.helloworld.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchCompany extends AppCompatActivity {
    String JSON_URL = "http://avoindata.prh.fi/bis/v1?totalResults=false&maxResults=1000&resultsFrom=0&companyRegistrationFrom=1960-01-01&name=";
    String companyURL;
    RecyclerView recyclerView;
    List<Company> companyList;
    SearchCompanyAdapter searchCompanyAdapter;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    String searchWord;
    TextView searchingText, companyNameTitle, companyBusinessIDTitle, companyFormTitle, companyRegistrationDateTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_company);

        recyclerView = findViewById(R.id.companyRecyclerView);
        companyList = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar);
        searchingText = findViewById(R.id.textViewSearching);

        companyNameTitle = findViewById(R.id.textViewCompanyNameTitle);
        companyBusinessIDTitle = findViewById(R.id.textViewCompanyBusinessIDTitle);
        companyFormTitle = findViewById(R.id.textViewCompanyFormTitle);
        companyRegistrationDateTitle = findViewById(R.id.textViewCompanyRegistrationDateTitle);

        Intent intent = getIntent();
        searchWord = intent.getStringExtra(HomeFragment.companyKeyword);
        companyURL = JSON_URL + searchWord;

        loadCompanyList();
    }

    private void loadCompanyList() {
        // Creating a string request to send request to the url.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, companyURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        searchingText.setVisibility(View.GONE);

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
                                Company company = new Company(companyObject.getString("name"),
                                                            companyObject.getString("businessId"),
                                                            companyObject.getString("companyForm"),
                                                            companyObject.getString("registrationDate"));

                                // adding the company to a list.
                                companyList.add(company);
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            searchCompanyAdapter = new SearchCompanyAdapter(getApplicationContext(), companyList);
                            recyclerView.setAdapter(searchCompanyAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent = new Intent(SearchCompany.this, CompanyNotFound.class);
                        startActivity(intent);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchIcon);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCompanyAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}