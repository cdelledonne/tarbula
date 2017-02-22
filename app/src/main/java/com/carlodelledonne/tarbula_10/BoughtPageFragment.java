package com.carlodelledonne.tarbula_10;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.carlodelledonne.tarbula_10.services.BoughtTabAdapter;
import com.carlodelledonne.tarbula_10.services.GetProducts;
import com.carlodelledonne.tarbula_10.services.Tenant;
import com.carlodelledonne.tarbula_10.services.Product;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Carlo on 29/11/15.
 */
public class BoughtPageFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    static ListView listView;
    static List<Product> tempList;
    static TextView textView;
    static Product toRemove;
    private FloatingActionButton button;
    static Tenant filter;
    static TextView textViewFilter;
    private static Context context;
    private static View viewForSnackbar;
    public static boolean deletable;
    private static String empty_bought_list_textview;
    private static String empty_filter_textview;
    static Product detail;

    int nr = 0;

    private int mPage;

    public static BoughtPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BoughtPageFragment fragment = new BoughtPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        tempList = new ArrayList<>();
        toRemove = null;
        filter = null;
        detail = null;
        context = getActivity();
        deletable = false;
        empty_bought_list_textview = getString(R.string.empty_bought_list_textview);
        empty_filter_textview = getString(R.string.empty_filter_textview);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_bought, container, false);
        listView = (ListView) view.findViewById(R.id.bought_list);
        textView = (TextView) view.findViewById(R.id.no_bought_element_text);
        checkTextViewVisibility();
        listView.setAdapter(MainTabActivity.mAdapterBought);

        // TODO: element-retrieving will be done on-line, from database
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detail = (Product) listView.getItemAtPosition(position);
                openDetailDialog();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });

        textViewFilter = (TextView) view.findViewById(R.id.filter_textview);
        textViewFilter.setText(getString(R.string.buyer, getString(R.string.everyone)));
        textViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterDialog();
            }
        });

        button = (FloatingActionButton) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boughtProductDialog(v);
            }
        });
        viewForSnackbar = view;
        refreshFilter();
        return view;
    }

    /*public static void prova(final Product p, final int position) {
        MainTabActivity.mListBought.remove(p);
        refreshFilter();
        Snackbar snackbar = Snackbar.make(viewForSnackbar, "Elemento rimosso", Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("ANNULLA", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainTabActivity.mListBought.add(position, p);
                refreshFilter();
            }
        });
        snackbar.setActionTextColor(Color.parseColor("#ff7505"));
    }*/

    public static void refreshFilter() {
        // TODO: retrieve filtered list here
        tempList.clear();
        for (Product p : MainTabActivity.mListBought) {
            if ((filter == null) ||
                    (p.getBuyer().equals(filter.getName())))
                tempList.add(p);
        }

        if (tempList.isEmpty())
            deletable = false;
        MainTabActivity.mAdapterBought =
                new BoughtTabAdapter(context, R.layout.list_bought, tempList);
        BoughtPageFragment.listView.setAdapter(MainTabActivity.mAdapterBought);
        if (!tempList.isEmpty())
            BoughtPageFragment.textView.setVisibility(View.INVISIBLE);
        else {
            if (filter == null)
                BoughtPageFragment.textView.setText(empty_bought_list_textview);
            else BoughtPageFragment.textView.setText(empty_filter_textview + " " + filter.toString());
            BoughtPageFragment.textView.setVisibility(View.VISIBLE);
        }
    }

    public void boughtProductDialog(View view) {
        // toRemove = null;
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = BoughtProductDialog.newInstance();
        newDialog.show(ft, "bought");
    }

    public void openFilterDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = FilterDialog.newInstance();
        newDialog.show(ft, "filter");
    }

    public void openDetailDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = ItemDetailDialog.newInstance();
        newDialog.show(ft, "detail");
    }

    public static void checkTextViewVisibility() {
        if (MainTabActivity.mListBought.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.INVISIBLE);
    }

    public static void removeProductFromBoughtList(Product p) {
    /*    // TODO: rewrite this method from scratch after introducing database
        MainTabActivity.mListBought.remove(p);
        MainTabActivity.mAdapterBought.notifyDataSetChanged();
        float fraction = p.getPrice()/p.getUsers().size();
        for (Tenant i : p.getUsers()) {
            i.setBalance(i.getBalance() + fraction);
        }
        //p.getBuyer().setBalance(p.getBuyer().getBalance() - p.getPrice());
        MainTabActivity.temp = MainTabActivity.mListMates;
        Collections.sort(MainTabActivity.temp);
        MainTabActivity.mAdapterBalance.notifyDataSetChanged();
        BoughtPageFragment.refreshFilter();*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                new GetProducts().execute();
                break;
            case R.id.settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.delete:
                if (!tempList.isEmpty())
                    BoughtPageFragment.deletable = !BoughtPageFragment.deletable;
                MainTabActivity.mAdapterBought.notifyDataSetChanged();
                break;
            case R.id.credits:
                Intent creditsIntent = new Intent(getActivity(), CreditsActivity.class);
                startActivity(creditsIntent);
                break;
            default:
                break;
        }
        return true;
    }

}
