package com.carlodelledonne.tarbula_10.services;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlodelledonne.tarbula_10.R;
import com.carlodelledonne.tarbula_10.TobuyPageFragment;

import java.util.List;

/**
 * Created by Carlo on 05/12/15.
 */
public class TobuyTabAdapter extends ArrayAdapter<Prodotto> {

    private List<Prodotto> objects;
    //private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    public TobuyTabAdapter(Context context, int textViewResourceId, List<Prodotto> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (FrameLayout) inflater.inflate(R.layout.list_tobuy, null);
        }
        Prodotto p = objects.get(position);
        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.titolo);
          /*TextView buyer = (TextView) v.findViewById(R.id.sottotitolo);
            TextView price = (TextView) v.findViewById(R.id.prezzo);
            TextView date = (TextView) v.findViewById(R.id.data);*/
            ImageView image = (ImageView) v.findViewById(R.id.delete_icon);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (name != null){
                name.setText(p.getName());
            }
          /*if (buyer != null){
                buyer.setText(" " + p.getBuyer().toString());
            }
            if (price != null){
                price.setText("€ " + p.getPriceString());
                price.setTextColor(Color.parseColor("#ff7505")); // green
            }
            if (date != null){
                date.setText(p.getDateString());
            }*/
            if (image != null) {
                int filter = Color.parseColor("#da0000");
                image.setColorFilter(filter);
                final Prodotto toRemove = p;
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TobuyPageFragment.removeElement(toRemove);
                    }
                });
                if (!TobuyPageFragment.deletable)
                    image.setVisibility(View.INVISIBLE);
                else image.setVisibility(View.VISIBLE);
                image.setClickable(TobuyPageFragment.deletable);
            }
        }
        return v;
    }

}
