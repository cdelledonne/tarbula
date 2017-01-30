package com.carlodelledonne.tarbula_10;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
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

import java.util.List;

/**
 * Created by Carlo on 17/11/15.
 */
public class BoughtProductDialog extends DialogFragment {

    private EditText editTextName, editTextDesc, editTextQuant, editTextPrice;
    private TextView textViewName;
    static TextView textViewByWhom, textViewForWhom;
    static List<Inquilino> forWhom;
    static Inquilino byWhom;
    private boolean thereIsName = false, thereIsPrice = false;

    static BoughtProductDialog newInstance() {
        return new BoughtProductDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
        setStyle(style, theme);
        forWhom = MainTabActivity.mListMates;
        byWhom = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        if (getParentFragment() instanceof TobuyPageFragment) {
            v = inflater.inflate(R.layout.dialog_boughtfromlist, container, false);
            textViewName = (TextView) v.findViewById(R.id.text_view_name);
            textViewName.setText(TobuyPageFragment.toRemove.getName());
            thereIsName = true;
        }
        else {
            v = inflater.inflate(R.layout.dialog_new_expense, container, false);
            editTextName = (EditText) v.findViewById(R.id.edit_text_name);
            editTextName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        }
        editTextDesc = (EditText) v.findViewById(R.id.edit_text_desc);
        editTextQuant = (EditText) v.findViewById(R.id.edit_text_quant);
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
                if (thereIsName && thereIsPrice && byWhom != null) {
                    if (getParentFragment() instanceof TobuyPageFragment) {
                        removeProductFromTobuyList();
                        addProductToBoughtList(textViewName.getText().toString());
                    } else /* if (getParentFragment() instanceof BoughtPageFragment) */ {
                        addProductToBoughtList(editTextName.getText().toString());
                    }
                    dismiss();
                } else {
                    Toast toast = Toast.makeText(
                            getActivity(), getString(R.string.mandatory_fields_toast), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        Button buttonCancel = (Button) v.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TobuyPageFragment.toRemove = null;
                dismiss();
            }
        });

        if (getParentFragment() instanceof BoughtPageFragment) {
            editTextName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    thereIsName = (s.length()>0);
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
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

    public void removeProductFromTobuyList() {
        MainTabActivity.mListTobuy.remove(TobuyPageFragment.toRemove);
        MainTabActivity.mAdapterTobuy.notifyDataSetChanged();
        TobuyPageFragment.toRemove = null;
        TobuyPageFragment.checkTextViewVisibility();
    }

    public void addProductToBoughtList(String name) {
        final Prodotto p = Prodotto.newProdotto(name);
        p.addBuyer(byWhom);
        if (!TextUtils.isEmpty(editTextDesc.getText()))
            p.addDescription(editTextDesc.getText().toString());
        else p.addDescription(getString(R.string.no_description));
        if ((!TextUtils.isEmpty(editTextQuant.getText())) &&
                (Integer.parseInt(editTextQuant.getText().toString())) > 0)
            p.addQuantity(Integer.parseInt(editTextQuant.getText().toString()));
        p.addPrice(Math.round(p.getQuantity() *
                Float.parseFloat(editTextPrice.getText().toString()) * 100) / 100f);
        p.addUsers(forWhom);
        MainTabActivity.mListBought.add(0, p);
        MainTabActivity.mAdapterBought.notifyDataSetChanged();
        BalancePageFragment.refreshBalance(p);
        BoughtPageFragment.refreshFilter();
    }

    public void openByWhomDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = ByWhomDialog.newInstance(0);
        newDialog.show(ft, "by_whom");
    }

    public void openForWhomDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        DialogFragment newDialog = ForWhomDialog.newInstance();
        newDialog.show(ft, "for_whom");
    }

}