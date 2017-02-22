package com.carlodelledonne.tarbula_10.services;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.carlodelledonne.tarbula_10.BoughtPageFragment;
import com.carlodelledonne.tarbula_10.MainTabActivity;
import com.carlodelledonne.tarbula_10.TobuyPageFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Carlo on 21/02/2017.
 */
public class GetProducts extends AsyncTask<String, String, String> {


    private static final String urlLoadProducts = "http://cdelledonne.dynu.com:10080/app_tarbula/get_products.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_BUYER = "buyer";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_BOUGHT = "bought";
    private static final String TAG_BOUGHTFOR = "bought_for";

    private static final String LOG = "DatabaseHandler_log";
    private static final String VOLLEY_LOG = "DatabaseHandler_volley_log";

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            MainTabActivity.pDialog.show();
    }


    protected String doInBackground(String... args) {

        String url = urlLoadProducts;
        if (MainTabActivity.filterTenant != null)
            url = url + "?buyer='" + MainTabActivity.filterTenant.getName() + "'";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(LOG, "onResponse");
                        Log.d(LOG, response.toString());

                        // check for success tag
                        try {
                            int success = response.getInt(TAG_SUCCESS);
                            Log.d(LOG, "success: " + success);

                            if (success == 1) {
                                // successfully retrieved products
                                JSONArray products = response.getJSONArray(TAG_PRODUCTS);

                                MainTabActivity.mListTobuy.clear();
                                MainTabActivity.mListBought.clear();
                                for (Tenant t : MainTabActivity.mListMates)
                                    t.setBalance(0);

                                for (int i=0; i<products.length(); i++) {

                                    JSONObject o = products.getJSONObject(i);
                                    // Parsing json object response
                                    // response will be a json object
                                    //String id = o.getString(TAG_PID);
                                    String name = o.getString(TAG_NAME);
                                    Double price = o.getDouble(TAG_PRICE);
                                    String description = o.getString(TAG_DESCRIPTION);
                                    String buyer = o.getString(TAG_BUYER);
                                    int bought = o.getInt(TAG_BOUGHT);
                                    String boughtFor = o.getString(TAG_BOUGHTFOR);

                                    if (bought == 0) {
                                        Product product = Product.newProduct(name, 0);
                                        MainTabActivity.mListTobuy.add(product);
                                    }
                                    else {
                                        Product product = Product.newProduct(name, 1)
                                                .addBuyer(buyer)
                                                .addPrice(price.floatValue())
                                                .addDescription(description)
                                                .addUsers(boughtFor);
                                        MainTabActivity.mListBought.add(0, product);

                                        TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
                                        splitter.setString(boughtFor);
                                        int size = 0;
                                        for (int j=0; j<boughtFor.length(); j++) {
                                            if( boughtFor.charAt(j) == ',' ) {
                                                size++;
                                            }
                                        }
                                        size++;
                                        float fraction = price.floatValue() / size;
                                        for (String s : splitter) {
                                            for (Tenant t : MainTabActivity.mListMates) {
                                                if (t.getName().equals(s))
                                                    t.setBalance(t.getBalance() - fraction);
                                                if (t.getName().equals(buyer))
                                                    t.setBalance(t.getBalance() + fraction);
                                            }
                                        }
                                    }

                                    // Fill list with response
//                                    HashMap<String, String> product = new HashMap<>();
//                                    product.put(TAG_PID, id);
//                                    product.put(TAG_NAME, name);
//                                    productsList.add(product);
                                }

                                Log.d(LOG, MainTabActivity.mListTobuy.toString());
                                Log.d(LOG, MainTabActivity.mListBought.toString());
                                MainTabActivity.mAdapterTobuy.notifyDataSetChanged();
                                MainTabActivity.mAdapterBought.notifyDataSetChanged();
                                TobuyPageFragment.checkTextViewVisibility();
                                BoughtPageFragment.checkTextViewVisibility();
                                BoughtPageFragment.refreshFilter();

                                MainTabActivity.mAdapterMates.notifyDataSetChanged();
                                //MainTabActivity.temp = MainTabActivity.mListMates;
                                Collections.sort(MainTabActivity.temp);
                                MainTabActivity.mAdapterBalance.notifyDataSetChanged();

                                MainTabActivity.pDialog.dismiss();
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
