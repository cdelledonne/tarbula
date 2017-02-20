package com.carlodelledonne.tarbula_10.services;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlodelledonne.tarbula_10.BoughtPageFragment;
import com.carlodelledonne.tarbula_10.R;

import java.util.List;

/**
 * Created by Carlo on 05/12/15.
 */
public class BoughtTabAdapter extends ArrayAdapter<Product> {

    private List<Product> objects;
    //private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    public BoughtTabAdapter(Context context, int textViewResourceId, List<Product> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (FrameLayout) inflater.inflate(R.layout.list_bought, null);
        }
        Product p = objects.get(position);
        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.titolo);
            TextView buyer = (TextView) v.findViewById(R.id.sottotitolo);
            TextView price = (TextView) v.findViewById(R.id.prezzo);
            TextView date = (TextView) v.findViewById(R.id.data);
            ImageView image = (ImageView) v.findViewById(R.id.delete_icon);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (name != null){
                name.setText(p.getName());
            }
            if (buyer != null){
                buyer.setText(" " + p.getBuyer().toString());
            }
            if (price != null){
                price.setText("€ " + p.getPriceString());
                price.setTextColor(Color.parseColor("#ff7505")); // green
            }
            if (date != null){
                date.setText(p.getDateString());
            }
            if (image != null) {
                int filter = Color.parseColor("#da0000");
                image.setColorFilter(filter);
                final Product toRemove = p;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BoughtPageFragment.removeProductFromBoughtList(toRemove);
                    }
                });
                if (!BoughtPageFragment.deletable)
                    image.setVisibility(View.INVISIBLE);
                else image.setVisibility(View.VISIBLE);
            }
        }
        return v;
    }

}
