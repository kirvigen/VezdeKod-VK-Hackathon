package com.kirvigen.vezdekod_vkhackathon.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public void addFragments(Fragment fragment) {
        fragments.add(fragment);
    }

    public FragmentAdapter(FragmentActivity fm) {
        super(fm);
    }

    public Fragment getFragment(int page){
        return fragments.get(page);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
