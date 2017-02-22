package com.carlodelledonne.tarbula_10;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.carlodelledonne.tarbula_10.services.BalanceTabAdapter;
import com.carlodelledonne.tarbula_10.services.BoughtTabAdapter;
import com.carlodelledonne.tarbula_10.services.DatabaseHandler;
import com.carlodelledonne.tarbula_10.services.GetProducts;
import com.carlodelledonne.tarbula_10.services.GetTenants;
import com.carlodelledonne.tarbula_10.services.Tenant;
import com.carlodelledonne.tarbula_10.services.Product;
import com.carlodelledonne.tarbula_10.services.SampleFragmentPagerAdapter;
import com.carlodelledonne.tarbula_10.services.SettingsAdapter;
import com.carlodelledonne.tarbula_10.services.StorageUtility;
import com.carlodelledonne.tarbula_10.services.TobuyTabAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainTabActivity extends AppCompatActivity {

    public static List<Product> mListTobuy;
    public static List<Product> mListBought;
    public static List<Tenant> temp;
    public static List<Tenant> mListMates;
    public static BoughtTabAdapter mAdapterBought;
    public static BalanceTabAdapter mAdapterBalance;
    public static TobuyTabAdapter mAdapterTobuy;
    public static ArrayAdapter<Tenant> mAdapterMates;
    private ViewPager viewPager;
    //static ArrayAdapter<Product> mAdapterBought;

    public static Context context;

    public static ProgressDialog pDialog;

    public static Tenant filterTenant = null;

    // TODO: files handles not required after switching to database
    final static String TOBUY_LIST_FILE = "com.dellegallopiva.files.TOBUY_LIST";
    final static String BOUGHT_LIST_FILE = "com.dellegallopiva.files.BOUGHT_LIST";
    public final static String MATES_LIST_FILE = "com.dellegallopiva.files.MATES_LIST";

    public static final String TAG_LOG = MainTabActivity.class.getName();

    private static final String LOG = "DatabaseHandler_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG_LOG, "onCreate");
        setContentView(R.layout.activity_main_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainTabActivity.this));
        viewPager.setCurrentItem(1);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        context = getApplicationContext();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Retrieving products...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);

        //setup liste e adapter
        mListTobuy = new ArrayList<>();
        mListBought = new ArrayList<>();
        mListMates = new ArrayList<>();
        temp = new ArrayList<>();

        //new GetProducts().execute();
        new GetTenants().execute();

        // TODO: the list-loading mode will change
        //if (StorageUtility.loadList(this, TOBUY_LIST_FILE).size() > 0)
        //    mListTobuy = (List<Product>)StorageUtility.loadList(this, TOBUY_LIST_FILE);
        //if (StorageUtility.loadList(this, BOUGHT_LIST_FILE).size() > 0)
        //    mListBought = (List<Product>)StorageUtility.loadList(this, BOUGHT_LIST_FILE);
        //if (StorageUtility.loadList(this, MATES_LIST_FILE).size() > 0)
        //    mListMates = (List<Tenant>)StorageUtility.loadList(this, MATES_LIST_FILE);

        Log.d(LOG, "listsLoaded");

        mAdapterTobuy = new TobuyTabAdapter(this, R.layout.list_tobuy, mListTobuy);
        mAdapterBought = new BoughtTabAdapter(this, R.layout.list_bought, mListBought);
        mAdapterMates = new SettingsAdapter(this, R.layout.list_mates, mListMates);
        //temp = mListMates;
        //Collections.sort(temp);
        mAdapterBalance = new BalanceTabAdapter(this, R.layout.list_balance, temp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG_LOG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG_LOG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG_LOG, "onPause");

        // TODO: the updating mechanism will be immediately after each change, not just onPause
        StorageUtility.storeList(this, MainTabActivity.mListBought,
                MainTabActivity.BOUGHT_LIST_FILE);
        StorageUtility.storeList(this, MainTabActivity.mListTobuy,
                MainTabActivity.TOBUY_LIST_FILE);
        StorageUtility.storeList(this, MainTabActivity.mListMates,
                MainTabActivity.MATES_LIST_FILE);
        Log.w(TAG_LOG, "onPauseSAVED");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG_LOG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG_LOG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG_LOG, "onRestart");
    }

    @Override
    public void onBackPressed() {
        if ((viewPager.getCurrentItem() == 0) && (TobuyPageFragment.deletable)) {
            TobuyPageFragment.deletable = false;
            MainTabActivity.mAdapterTobuy.notifyDataSetChanged();
        }
        else if ((viewPager.getCurrentItem() == 1) && (BoughtPageFragment.deletable)) {
            BoughtPageFragment.deletable = false;
            MainTabActivity.mAdapterBought.notifyDataSetChanged();
        }
        else
            super.onBackPressed();
    }

    public static String priceToString(float price) {
        float f = Math.round(price * 100)/100f;
        String s = String.valueOf(f);
        int i;
        for (i=0; i<s.length(); i++) {
            if(s.charAt(i) == '.')
                break;
        }
        int decimals = s.length() - (i+1);
        if (decimals == 1) s += "0";
        return s;
    }
}