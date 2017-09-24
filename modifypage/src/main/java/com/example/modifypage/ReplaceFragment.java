package com.example.modifypage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modifypage.base.BasePager;

/**
 * Created by Timmy on 2017/9/21.
 */

public  class ReplaceFragment extends Fragment {
    private   BasePager currPager;
    public ReplaceFragment() {
        this.currPager=null;
    }
    @SuppressLint("ValidFragment")
    public  ReplaceFragment(BasePager pager) {
        this.currPager=pager;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return currPager.rootview;
    }
}
