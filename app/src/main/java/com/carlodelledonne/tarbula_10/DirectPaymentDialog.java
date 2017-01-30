package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carlodelledonne.tarbula_10.services.DecimalDigitsInputFilter;
import com.carlodelledonne.tarbula_10.services.Inquilino;
import com.carlodelledonne.tarbula_10.services.Prodotto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlo on 15/12/15.
 */
public class DirectPaymentDialog extends DialogFragment{
    private EditText editTextPrice;
    static TextView textViewByWhom, textViewForWhom;
    static Inquilino byWhom, forWhom;
    private boolean thereIsPrice = false;

    static DirectPaymentDialog newInstance() {
        return new DirectPaymentDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
        setStyle(style, theme);
        forWhom = null;
        byWhom = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.dialog_direct_payment, container, false);
        editTextPrice = (EditText) v.findViewById(R.id.edit_text_price);
        textViewByWhom = (TextView) v.findViewById(R.id.by_whom);
        textViewByWhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openByWhomDialog();
            }
        });
        textViewForWhom = (TextView) v.findViewById(R.id.for_whom);
        textViewForWhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForWhomDialog();
            }
        });

        Button buttonConfirm = (Button) v.findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thereIsPrice && byWhom != null && forWhom != null && byWhom != forWhom) {
                    addProductToBoughtList(getString(R.string.payment_to, forWhom.getName()));
                    dismiss();
                } else if (!thereIsPrice) {
                    Toast toast = Toast.makeText(
                            getActivity(), getString(R.string.mandatory_fields_toast), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(
                            getActivity(), getString(R.string.sender_equals_addressee_toast), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        Button buttonCancel = (Button) v.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        editTextPrice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        editTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                thereIsPrice = (s.length()>0);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return v;
    }

    public void addProductToBoughtList(String name) {
        final Prodotto p = Prodotto.newProdotto(name);
        p.addPrice(Float.parseFloat(editTextPrice.getText().toString()));
        p.addBuyer(byWhom);
        /*if (!TextUtils.isEmpty(editTextDesc.getText()))
            p.addDescription(editTextDesc.getText().toString());
        else p.addDescription("Nessuna descrizione");*/
        List<Inquilino> list = new ArrayList<>();
        list.add(forWhom);
        p.addUsers(list);
        MainTabActivity.mListBought.add(0, p);
        MainTabActivity.mAdapterBought.notifyDataSetChanged();
        BoughtPageFragment.checkTextViewVisibility();
        // TODO: spostare le operazioni di Storage negli onPause/onStop
        //StorageUtility.storeList(getActivity(), MainTabActivity.mListBought,
        //        MainTabActivity.BOUGHT_LIST_FILE);
        BalancePageFragment.refreshBalance(p);
        BoughtPageFragment.refreshFilter();
        // TODO: storage
        //StorageUtility.storeList(getActivity(), MainTabActivity.mListMates,
        //        MainTabActivity.MATES_LIST_FILE);
    }

    public void openByWhomDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = ByWhomDialog.newInstance(1);
        newDialog.show(ft, "by_whom");
    }

    public void openForWhomDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = ByWhomDialog.newInstance(2);
        newDialog.show(ft, "for_whom");
    }
}
