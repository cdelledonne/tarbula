package com.carlodelledonne.tarbula_10.services;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.carlodelledonne.tarbula_10.R;

import java.util.List;

/**
 * Created by Carlo on 05/12/15.
 */
public class BalanceTabAdapter extends ArrayAdapter<Inquilino> {

    private List<Inquilino> objects;
    //private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

    public BalanceTabAdapter(Context context, int textViewResourceId, List<Inquilino> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (ActivatedRelativeLayout) inflater.inflate(R.layout.list_balance, null);
        }
        Inquilino p = objects.get(position);
        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.titolo);
            TextView price = (TextView) v.findViewById(R.id.prezzo);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (name != null){
                name.setText("  " + p.getName());
            }
            if (price != null){
                float a = Math.round(p.getBalance()*100)/100f;
                String s = String.valueOf(a);
                int i;
                for (i=0; i<s.length(); i++) {
                    if(s.charAt(i) == '.')
                        break;
                }
                int decimals = s.length() - (i+1);
                if (decimals == 1) s += "0";
                price.setText("€ " + s);
                if (Math.round(p.getBalance()*100) >= 0)
                    price.setTextColor(Color.argb(255, 24, 156, 15)); // green
                else
                    price.setTextColor(Color.argb(255, 240, 0, 0)); // red
            }
        }
        return v;
    }

}
