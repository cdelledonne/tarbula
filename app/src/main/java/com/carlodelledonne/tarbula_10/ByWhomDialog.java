package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Carlo on 07/12/15.
 */
public class ByWhomDialog extends DialogFragment {

    private RadioButton[] radioList;
    private int mFlag;

    static ByWhomDialog newInstance(int flag) {
        ByWhomDialog result = new ByWhomDialog();
        result.mFlag = flag;
        return result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
        setStyle(style, theme);
        radioList = new RadioButton[MainTabActivity.mListMates.size()];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_by_whom, container, false);
        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int rabioButtonHeightDP = 45; // altezza in dp
        //int rabioButtonMarginDP = 5; //margine in dp
        int rabioButtonHeightPixel = (int) (rabioButtonHeightDP * scale + 0.5f);
        //int rabioButtonMarginPixel = (int) (rabioButtonMarginDP * scale + 0.5f);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, rabioButtonHeightPixel);
        //params.setMargins(rabioButtonMarginPixel,rabioButtonMarginPixel,rabioButtonMarginPixel,rabioButtonMarginPixel);
        for (int i=0; i<MainTabActivity.mListMates.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(MainTabActivity.mListMates.get(i).toString());
            radioButton.setSingleLine(true);
            radioButton.setTextSize(18);
            radioButton.setLayoutParams(params);
            radioButton.setBackground(getResources().getDrawable(R.drawable.checkbox_dialog));
            //radioButton.setBackgroundColor(getResources().getColor(R.color.checkbox_background));
            radioGroup.addView(radioButton);
            radioList[i] = radioButton;
        }
        for (int i=0; i<MainTabActivity.mListMates.size(); i++) {
            if (getParentFragment() instanceof DirectPaymentDialog) {
                if (mFlag == 1) {
                    if (MainTabActivity.mListMates.get(i).equals(DirectPaymentDialog.byWhom))
                        radioList[i].setChecked(true);
                } else {
                    if (MainTabActivity.mListMates.get(i).equals(DirectPaymentDialog.forWhom))
                        radioList[i].setChecked(true);
                }
            } else {
                if (MainTabActivity.mListMates.get(i).equals(BoughtProductDialog.byWhom))
                    radioList[i].setChecked(true);
            }
        }

        Button okButton = (Button) v.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<radioList.length; i++) {
                    if (getParentFragment() instanceof DirectPaymentDialog) {
                        if (mFlag == 1) {
                            if (radioList[i].isChecked())
                                DirectPaymentDialog.byWhom = MainTabActivity.mListMates.get(i);
                        } else {
                            if (radioList[i].isChecked())
                                DirectPaymentDialog.forWhom = MainTabActivity.mListMates.get(i);
                        }
                    } else {
                        if (radioList[i].isChecked())
                            BoughtProductDialog.byWhom = MainTabActivity.mListMates.get(i);
                    }
                }
                if (getParentFragment() instanceof DirectPaymentDialog) {
                    if (mFlag == 1) {
                        if (DirectPaymentDialog.byWhom != null)
                            DirectPaymentDialog.textViewByWhom.setText(getResources().getString(
                                    R.string.by_whom, DirectPaymentDialog.byWhom.toString()));
                    } else {
                        if (DirectPaymentDialog.forWhom != null)
                            DirectPaymentDialog.textViewForWhom.setText(getResources().getString(
                                    R.string.for_whom, DirectPaymentDialog.forWhom.toString()));
                    }
                } else {
                    if (BoughtProductDialog.byWhom != null)
                        BoughtProductDialog.textViewByWhom.setText(getResources().getString(
                                R.string.by_whom, BoughtProductDialog.byWhom.toString()));
                }
                dismiss();
            }
        });

        return v;
    }
}
