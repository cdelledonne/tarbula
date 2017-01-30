package com.carlodelledonne.tarbula_10;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlodelledonne.tarbula_10.services.Inquilino;
import com.carlodelledonne.tarbula_10.services.StorageUtility;

import java.util.Collections;

public class SettingsActivity extends AppCompatActivity {

    private EditText editText;
    static TextView textView;
    public static Inquilino toRemove;
    private static FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        manager = getSupportFragmentManager();

        // TODO: nascondere la tastiera all'apertura dell'activity
        editText = (EditText) findViewById(R.id.new_edittext);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(MainTabActivity.mAdapterMates);
        textView = (TextView) findViewById(R.id.no_mates_text);
        checkTextViewVisibility();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void addInquilino(View view) {
        Inquilino nuovo = Inquilino.newInquilino(editText.getText().toString());
        if (!TextUtils.isEmpty(editText.getText())){
            MainTabActivity.mListMates.add(nuovo);
            StorageUtility.storeList(this, MainTabActivity.mListMates, MainTabActivity.MATES_LIST_FILE);
            MainTabActivity.mAdapterMates.notifyDataSetChanged();
            MainTabActivity.temp = MainTabActivity.mListMates;
            Collections.sort(MainTabActivity.temp);
            MainTabActivity.mAdapterBalance.notifyDataSetChanged();
            BalancePageFragment.checkTextViewVisibility();
            checkTextViewVisibility();
        }
        else {
            Toast mioToast = Toast.makeText(
                    SettingsActivity.this, getString(R.string.empty_name_toast), Toast.LENGTH_LONG);
            mioToast.show();
        }
        editText.getText().clear();
        // all'interno dell'if chiudo la tastiera
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void checkTextViewVisibility() {
        if (MainTabActivity.mListMates.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.INVISIBLE);
    }

    public static void openDeleteMateDialog() {
        FragmentTransaction ft = manager.beginTransaction();
        DialogFragment newDialog = DeleteMateDialog.newInstance();
        newDialog.show(ft, "delete_mate");
    }
}
