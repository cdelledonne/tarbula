package com.carlodelledonne.tarbula_10.services;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.carlodelledonne.tarbula_10.BalancePageFragment;
import com.carlodelledonne.tarbula_10.BoughtPageFragment;
import com.carlodelledonne.tarbula_10.R;
import com.carlodelledonne.tarbula_10.TobuyPageFragment;

/**
 * Created by Carlo on 29/11/15.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[PAGE_COUNT];

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabTitles[0] = context.getString(R.string.tab1_label);
        tabTitles[1] = context.getString(R.string.tab2_label);
        tabTitles[2] = context.getString(R.string.tab3_label);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result;
        switch (position) {
            case 0:
                result = TobuyPageFragment.newInstance(position + 1);
                break;
            case 1:
                result = BoughtPageFragment.newInstance(position + 1);
                break;
            case 2:
                result = BalancePageFragment.newInstance(position + 1);
                break;
            default:
                result = TobuyPageFragment.newInstance(position + 1);
                break;
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
