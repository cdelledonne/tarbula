package com.carlodelledonne.tarbula_10;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlodelledonne.tarbula_10.services.Prodotto;
import com.github.clans.fab.FloatingActionButton;

/**
 * Created by Carlo on 29/11/15.
 */
public class TobuyPageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    static TextView textView;
    static Prodotto toRemove;
    public static boolean deletable;

    private int mPage;

    public static TobuyPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TobuyPageFragment fragment = new TobuyPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        toRemove = null;
        deletable = false;
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_tobuy, container, false);
        ListView listView = (ListView) view.findViewById(R.id.tobuy_list);
        textView = (TextView) view.findViewById(R.id.no_tobuy_element_text);
        checkTextViewVisibility();
        listView.setAdapter(MainTabActivity.mAdapterTobuy);
        // TODO: considerare la seguente possibilità
        /* inserire un'icona aggiuntiva su ogni elemento della listview, toccando la quale
        * sia possibile eliminare un elemento della lista, con conseguente visualizzazione
        * della snackbar per permettere di annullare l'eliminazione */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boughtProductFromListDialog(position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missingProductDialog();
            }
        });

        return view;
    }

    public void boughtProductFromListDialog(int position) {
        toRemove = MainTabActivity.mListTobuy.get(position);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = BoughtProductDialog.newInstance();
        newDialog.show(ft, "bought_fromlist");
    }

    public void missingProductDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = TobuyProductDialog.newInstance();
        newDialog.show(ft, "new");
    }

    public static void checkTextViewVisibility() {
        if (MainTabActivity.mListTobuy.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.INVISIBLE);
    }

    public static void removeElement(Prodotto p) {
        MainTabActivity.mListTobuy.remove(p);
        if (MainTabActivity.mListTobuy.isEmpty())
            deletable = false;
        MainTabActivity.mAdapterTobuy.notifyDataSetChanged();
        checkTextViewVisibility();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.delete:
                if (!MainTabActivity.mListTobuy.isEmpty())
                    TobuyPageFragment.deletable = !TobuyPageFragment.deletable;
                MainTabActivity.mAdapterTobuy.notifyDataSetChanged();
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
