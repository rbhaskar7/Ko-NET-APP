package com.example.ko_net.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ko_net.Fragments.CallsFragment;
import com.example.ko_net.Fragments.ChatsFragment;
import com.example.ko_net.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: new ChatsFragment();
            case 1: new StatusFragment();
            case 2: new CallsFragment();
            default: new ChatsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0) {
            title = "CHATS";
        }
        if(position == 1) {
            title = "STATUS";
        }
        if(position == 2) {
            title = "CALLS";
        }

        return super.getPageTitle(position);
    }
}
