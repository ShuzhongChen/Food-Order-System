package com.shuzhongchen.foodordersystem.view.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.view.child.ShotListFragment;

/**
 * Created by shuzhongchen on 5/3/18.
 */

public class BaseFragment extends Fragment {

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_base, container, false);

        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        /** Important: Must use the child FragmentManager or you will see side effects. */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        return root;
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt(ShotListFragment.POSITION_KEY, position);
            return ShotListFragment.newInstance(args);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "Drink";

            switch (position) {
                case 0:
                    break;
                case 1:
                    title = "Appetizer";
                    break;
                case 2:
                    title = "Main Course";
                    break;
                default:
                    title = "Dessert";
            }

            return title;
        }

    }
}
