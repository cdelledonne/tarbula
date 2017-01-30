package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Carlo on 03/01/16.
 */
public class ItemDetailDialog extends DialogFragment {

    static ItemDetailDialog newInstance() {
        return new ItemDetailDialog();
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
        View v = inflater.inflate(R.layout.dialog_bought_item_detail, container, false);

        TextView textView = (TextView) v.findViewById(R.id.detail_textview);

        String name = (getString(R.string.name, BoughtPageFragment.detail.getName()));

        String description = (getString(R.string.description, BoughtPageFragment.detail.getDescription()));

        float priceUnit = BoughtPageFragment.detail.getPrice() / BoughtPageFragment.detail.getQuantity();
        String priceUnitString = MainTabActivity.priceToString(priceUnit);
        float priceTot = BoughtPageFragment.detail.getPrice();
        String priceTotString = MainTabActivity.priceToString(priceTot);
        String price = (getString(R.string.price, BoughtPageFragment.detail.getQuantity(),
                priceUnitString, priceTotString));

        String buyer = (getString(R.string.buyer, BoughtPageFragment.detail.getBuyer().toString()));

        String usersString = "";
        for (int j=0; j<BoughtPageFragment.detail.getUsers().size(); j++) {
            usersString += BoughtPageFragment.detail.getUsers().get(j).toString();
            if (j<BoughtPageFragment.detail.getUsers().size()-1)
                usersString += ", ";
        }
        String users = (getString(R.string.users, usersString));

        String detailText = name + "\n\n" + description + "\n\n" + price + "\n\n" + buyer +
                "\n\n" + users;

        textView.setText(detailText);

        Button buttonCancel = (Button) v.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

}

