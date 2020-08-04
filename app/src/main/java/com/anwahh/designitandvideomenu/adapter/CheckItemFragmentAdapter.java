package com.anwahh.designitandvideomenu.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CheckItemFragmentAdapter extends FragmentPagerAdapter {
	public ArrayList<Fragment> listFragments;

	public CheckItemFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	
	public CheckItemFragmentAdapter(FragmentManager fm, ArrayList<Fragment> listFragments) {
		// TODO Auto-generated constructor stub
		super(fm);
		this.listFragments=listFragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return listFragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFragments.size();
	}

}
