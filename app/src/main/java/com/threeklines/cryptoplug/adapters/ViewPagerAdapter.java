package com.threeklines.cryptoplug.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.threeklines.cryptoplug.coinfragments.AllCoinsList;
import com.threeklines.cryptoplug.coinfragments.Portfollio;
import com.threeklines.cryptoplug.coinfragments.WatchList;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new AllCoinsList();
        }else if(position == 1){
            return new WatchList();
        }
        return new Portfollio();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "All";
        if (position == 1) return "Watchlist";
        return "Portfolio";
    }

    @Override
    public int getCount() {
        return 3;
    }
}
