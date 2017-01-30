package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Carlo on 07/12/15.
 */
public class FilterDialog extends DialogFragment {

    private RadioButton[] radioList;

    static FilterDialog newInstance() {
        return new FilterDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
        setStyle(style, theme);
        radioList = new RadioButton[MainTabActivity.mListMates.size() + 1];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_by_whom, container, false);
        ((TextView) v.findViewById(R.id.title_text_view)).setText(getString(R.string.filter_dialog));
        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int rabioButtonHeightDP = 45; // altezza in dp
        //int rabioButtonMarginDP = 5; //margine in dp
        int rabioButtonHeightPixel = (int) (rabioButtonHeightDP * scale + 0.5f);
        //int rabioButtonMarginPixel = (int) (rabioButtonMarginDP * scale + 0.5f);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, rabioButtonHeightPixel);
        //params.setMargins(rabioButtonMarginPixel,rabioButtonMarginPixel,rabioButtonMarginPixel,rabioButtonMarginPixel);
        RadioButton radioButton = new RadioButton(getActivity());
        radioButton.setText(getString(R.string.everyone));
        radioButton.setTextSize(18);
        radioButton.setLayoutParams(params);
        radioButton.setBackground(getResources().getDrawable(R.drawable.checkbox_dialog));
        //radioButton.setBackgroundColor(getResources().getColor(R.color.checkbox_background));
        radioGroup.addView(radioButton);
        radioList[0] = radioButton;
        for (int i=0; i<MainTabActivity.mListMates.size(); i++) {
            radioButton = new RadioButton(getActivity());
            radioButton.setText(MainTabActivity.mListMates.get(i).toString());
            radioButton.setSingleLine(true);
            radioButton.setTextSize(18);
            radioButton.setLayoutParams(params);
            radioButton.setBackground(getResources().getDrawable(R.drawable.checkbox_dialog));
            //radioButton.setBackgroundColor(getResources().getColor(R.color.checkbox_background));
            radioGroup.addView(radioButton);
            radioList[i+1] = radioButton;
        }
        for (int i=0; i<MainTabActivity.mListMates.size(); i++) {
            if (MainTabActivity.mListMates.get(i).equals(BoughtPageFragment.filter))
                radioList[i+1].setChecked(true);
        }
        if (BoughtPageFragment.filter == null)
            radioList[0].setChecked(true);

        Button okButton = (Button) v.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=1; i<radioList.length; i++)
                    if (radioList[i].isChecked())
                        BoughtPageFragment.filter = MainTabActivity.mListMates.get(i-1);
                if (radioList[0].isChecked())
                    BoughtPageFragment.filter = null;
                if (BoughtPageFragment.filter != null)
                    BoughtPageFragment.textViewFilter.setText(
                            getString(R.string.buyer, BoughtPageFragment.filter.toString()));
                else
                    BoughtPageFragment.textViewFilter.setText(
                            getString(R.string.buyer, getString(R.string.everyone)));

                BoughtPageFragment.refreshFilter();
                dismiss();
            }
        });

        return v;
    }
}
