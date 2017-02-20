package com.carlodelledonne.tarbula_10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.carlodelledonne.tarbula_10.services.Tenant;
import com.carlodelledonne.tarbula_10.services.Product;

import java.util.Collections;

/**
 * Created by Carlo on 29/11/15.
 */
public class BalancePageFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";

    static ListView listView;
    static TextView textView;

    private int mPage;

    public static BalancePageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BalancePageFragment fragment = new BalancePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_balance, container, false);
        listView = (ListView) view.findViewById(R.id.balance_list);
        listView.setAdapter(MainTabActivity.mAdapterBalance);
        textView = (TextView) view.findViewById(R.id.no_balance_element_text);
        checkTextViewVisibility();

        Button buttonReset = (Button) view.findViewById(R.id.erase_button);
        Button buttonPayment = (Button) view.findViewById(R.id.directPayment_button);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClearBalanceDialog();
            }
        });

        buttonPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDirectPaymentDialog();
            }
        });

    return view;
    }

    public static void refreshBalance(Product p) {
        // TODO: rewrite this method from scratch after introducing database
        float fraction = p.getPrice()/p.getUsers().size();
        for (Tenant i : p.getUsers()) {
            i.setBalance(i.getBalance() - fraction);
        }
        p.getBuyer().setBalance(p.getBuyer().getBalance() + p.getPrice());
        MainTabActivity.temp = MainTabActivity.mListMates;
        Collections.sort(MainTabActivity.temp);
        MainTabActivity.mAdapterBalance.notifyDataSetChanged();
    }

    public void openClearBalanceDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = ClearBalanceDialog.newInstance();
        newDialog.show(ft, "clear_balance");
    }

    public void openDirectPaymentDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = DirectPaymentDialog.newInstance();
        newDialog.show(ft, "direct_payment");
    }

    public static void checkTextViewVisibility() {
        if (MainTabActivity.mListMates.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.delete).setVisible(false).setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.delete:
                BoughtPageFragment.deletable = !BoughtPageFragment.deletable;
                BoughtPageFragment.refreshFilter();
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
