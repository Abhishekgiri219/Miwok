package com.example.miwok;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CustomFragmentStateAdapter extends FragmentStateAdapter {
    public CustomFragmentStateAdapter(@NonNull FragmentManager fm, Lifecycle lc) {
        super(fm, lc);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return NumbersFragment.newInstance();
            case 1:return ColorsFragment.newInstance();
            case 2:return FamilyFragment.newInstance();
            default:return PhrasesFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
