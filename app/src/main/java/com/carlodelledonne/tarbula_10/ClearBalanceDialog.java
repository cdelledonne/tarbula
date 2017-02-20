package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.carlodelledonne.tarbula_10.services.Tenant;

/**
 * Created by Carlo on 15/12/15.
 */
public class ClearBalanceDialog extends DialogFragment {

    static ClearBalanceDialog newInstance() {
        return new ClearBalanceDialog();
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

        Button buttonConfirm = (Button) v.findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBalance();
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

    public void clearBalance() {
        // TODO: update method after introducing database
        MainTabActivity.mListBought.clear();
        BoughtPageFragment.refreshFilter();
        BoughtPageFragment.checkTextViewVisibility();
        // TODO: storage
        //StorageUtility.storeList(getActivity(), MainTabActivity.mListBought,
        //        MainTabActivity.BOUGHT_LIST_FILE);
        for (Tenant i : MainTabActivity.mListMates)
            i.setBalance(0);
        MainTabActivity.mAdapterBalance.notifyDataSetChanged();
        // TODO: storage
        //StorageUtility.storeList(getActivity(), MainTabActivity.mListMates,
        //        MainTabActivity.MATES_LIST_FILE);
    }

}
