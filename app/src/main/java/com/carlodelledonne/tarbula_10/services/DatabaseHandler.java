package com.carlodelledonne.tarbula_10.services;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.carlodelledonne.tarbula_10.MainTabActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Carlo on 20/02/2017.
 */
public class DatabaseHandler {

    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;

    private Tenant filterTenant;

    private static final String urlLoadProducts = "http://cdelledonne.dynu.com:10080/app_tarbula/get_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_BUYER = "buyer";
    private static final String TAG_DESCRIPTION = "description";

    private static final String LOG = "DatabaseHandler_log";
    private static final String VOLLEY_LOG = "DatabaseHandler_volley_log";


    public int loadProducts(Tenant tenant) {
        int message = 0;

        filterTenant = tenant;

        // Getting products details in background thread
        new GetProductDetails().execute();

        return message;
    }

    public int loadTenants() {
        int message = 0;

        return message;
    }

    public int deleteProduct() {
        int message = 0;

        return message;
    }

    public int editProduct() {
        int message = 0;

        return message;
    }


    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog();
//            pDialog.setMessage("Creating product...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }


        protected String doInBackground(String... args) {

            String url = urlLoadProducts;
            if (filterTenant != null)
                url = url + "?buyer" + filterTenant.getName();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // pDialog.dismiss();

                            // check for success tag
                            try {
                                int success = response.getInt(TAG_SUCCESS);
                                Log.d(LOG, "success: " + success);

                                if (success == 1) {
                                    // successfully retrieved products
                                    JSONArray products = response.getJSONArray(TAG_PRODUCTS);

                                    for (int i=0; i<products.length(); i++) {
                                        JSONObject o = products.getJSONObject(i);
                                        // Parsing json object response
                                        // response will be a json object
                                        String id = o.getString(TAG_PID);
                                        String name = o.getString(TAG_NAME);

                                        HashMap<String, String> product = new HashMap<>();
                                        product.put(TAG_PID, id);
                                        product.put(TAG_NAME, name);
                                        productsList.add(product);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(VOLLEY_LOG, "Error: " + error.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(request);

            return null;
        }
    }
}
