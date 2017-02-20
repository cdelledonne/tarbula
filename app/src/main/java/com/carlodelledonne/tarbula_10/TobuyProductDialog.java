package com.carlodelledonne.tarbula_10;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carlodelledonne.tarbula_10.services.Product;

/**
 * Created by Carlo on 17/11/15.
 */
public class TobuyProductDialog extends DialogFragment {

    private EditText editText;

    static TobuyProductDialog newInstance() {
        return new TobuyProductDialog();
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
        View v = inflater.inflate(R.layout.dialog_tobuy, container, false);

        editText = (EditText) v.findViewById(R.id.tobuy_edit_text);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        Button buttonConfirm = (Button) v.findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    addProductToTobuyList(editText.getText().toString());
                    dismiss();
                } else {
                    Toast toast = Toast.makeText(
                            getActivity(), getString(R.string.empty_name_toast), Toast.LENGTH_LONG);
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

        return v;
    }

    public void addProductToTobuyList(String name) {
        // TODO: update method after introducing database
        final Product p = Product.newProdotto(name);
        MainTabActivity.mListTobuy.add(p);
        MainTabActivity.mAdapterTobuy.notifyDataSetChanged();
        TobuyPageFragment.checkTextViewVisibility();
        // TODO: spostare le operazioni di Storage negli onPause/onStop
        //StorageUtility.storeList(getActivity(), MainTabActivity.mListTobuy,
        //        MainTabActivity.TOBUY_LIST_FILE);
    }
}
