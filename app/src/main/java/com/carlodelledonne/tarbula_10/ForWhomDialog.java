package com.carlodelledonne.tarbula_10;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.carlodelledonne.tarbula_10.services.Inquilino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlo on 07/12/15.
 */
public class ForWhomDialog extends DialogFragment {

    private CheckBox[] checkList;

    static ForWhomDialog newInstance() {
        return new ForWhomDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
        setStyle(style, theme);
        checkList = new CheckBox[MainTabActivity.mListMates.size()];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_for_whom, container, false);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linear_container);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int checkBoxHeightDP = 45; // altezza in dp
        //int checkBoxMarginDP = 5; //margine in dp
        int checkBoxHeightPixel = (int) (checkBoxHeightDP * scale + 0.5f);
        //int checkBoxMarginPixel = (int) (checkBoxMarginDP * scale + 0.5f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, checkBoxHeightPixel);
        //params.setMargins(checkBoxMarginPixel,checkBoxMarginPixel,checkBoxMarginPixel,checkBoxMarginPixel);
        for (int i=0; i<MainTabActivity.mListMates.size(); i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(MainTabActivity.mListMates.get(i).toString());
            checkBox.setSingleLine(true);
            checkBox.setTextSize(18);
            checkBox.setLayoutParams(params);
            checkBox.setBackground(getResources().getDrawable(R.drawable.checkbox_dialog));
            //checkBox.setBackgroundColor(getResources().getColor(R.color.checkbox_background));

            //checkBox.setButtonDrawable(R.drawable.my_checkbox);
            if (BoughtProductDialog.forWhom.contains(MainTabActivity.mListMates.get(i)))
                checkBox.setChecked(true);
            linearLayout.addView(checkBox);
            checkList[i] = checkBox;
        }
        Button okButton = (Button) v.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Inquilino> list = new ArrayList<Inquilino>();
                for (int i=0; i<checkList.length; i++)
                    if (checkList[i].isChecked())
                        list.add(MainTabActivity.mListMates.get(i));
                if (!list.isEmpty()) {
                    BoughtProductDialog.forWhom = list;
                    if (list.size() < MainTabActivity.mListMates.size())
                        BoughtProductDialog.textViewForWhom.setText
                                (getString(R.string.for_how_many, list.size()));
                    if (list.size() == 1)
                        BoughtProductDialog.textViewForWhom.setText
                                (getString(R.string.for_one_person));
                    if (list.size() == MainTabActivity.mListMates.size())
                        BoughtProductDialog.textViewForWhom.setText
                                (getString(R.string.for_all_people));
                    dismiss();
                }
                else {
                    Toast toast = Toast.makeText(
                            getActivity(), getString(R.string.no_people_selected_toast), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return v;
    }
}
