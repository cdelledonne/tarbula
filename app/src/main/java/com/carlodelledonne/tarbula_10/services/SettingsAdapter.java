package com.carlodelledonne.tarbula_10.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlodelledonne.tarbula_10.MainTabActivity;
import com.carlodelledonne.tarbula_10.R;
import com.carlodelledonne.tarbula_10.SettingsActivity;

import java.util.List;

/**
 * Created by Carlo on 05/12/15.
 */
public class SettingsAdapter extends ArrayAdapter<Tenant> {

    private List<Tenant> objects;
    //private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    public SettingsAdapter(Context context, int textViewResourceId, List<Tenant> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (ActivatedRelativeLayout) inflater.inflate(R.layout.list_mates, null);
        }
        Tenant p = objects.get(position);
        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.titolo);
            ImageView image = (ImageView) v.findViewById(R.id.image);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (name != null){
                name.setText("  " + p.getName());
            }
            final int pos = position;
            if (image != null){
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SettingsActivity.toRemove = MainTabActivity.mListMates.get(pos);
                        SettingsActivity.openDeleteMateDialog();
                    }
                });
            }
        }
        return v;
    }

}
