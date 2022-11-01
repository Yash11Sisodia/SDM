package com.downloadmanager.speeddownloadmanager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


    Fragment frg=null;

 switch (position){
     case 0:
         frg=new fragD3();
         break;
     case 1:
         frg=new fragD2();
         break;
     case 2:
         frg=new fragD3();
         break;
 }
        return frg;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
