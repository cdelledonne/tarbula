package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.carlodelledonne.tarbula_10.services.StorageUtility;

import java.util.Collections;

/**
 * Created by Carlo on 15/12/15.
 */
public class DeleteTenantDialog extends DialogFragment {

    static DeleteTenantDialog newInstance() {
        return new DeleteTenantDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: gestire l'elevation del titolo del fragment nel .xml
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_clear_balance, container, false);

        TextView textView = (TextView) v.findViewById(R.id.tobuy_edit_text);
        textView.setText(getString(R.string.delete_mate_text));

        Button buttonConfirm = (Button) v.findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMate();
                dismiss();
            }
        });
        Button buttonCancel = (Button) v.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    public void deleteMate() {
        // TODO: update method after introducing database
        int a = Math.round(SettingsActivity.toRemove.getBalance() * 100);
        if (Math.round(SettingsActivity.toRemove.getBalance() * 100) == 0) {
            MainTabActivity.mListMates.remove(SettingsActivity.toRemove);
            StorageUtility.storeList(getContext(), MainTabActivity.mListMates, MainTabActivity.MATES_LIST_FILE);
            MainTabActivity.mAdapterMates.notifyDataSetChanged();
            MainTabActivity.temp = MainTabActivity.mListMates;
            Collections.sort(MainTabActivity.temp);
            MainTabActivity.mAdapterBalance.notifyDataSetChanged();
            BalancePageFragment.checkTextViewVisibility();
            SettingsActivity.checkTextViewVisibility();
        }
        else {
            Toast mioToast = Toast.makeText(getContext(),
                    getString(R.string.nonnull_mates_balance_toast), Toast.LENGTH_LONG);
            mioToast.show();
        }
        SettingsActivity.toRemove = null;
    }

}
