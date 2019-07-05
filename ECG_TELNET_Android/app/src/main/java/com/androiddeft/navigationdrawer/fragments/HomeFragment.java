package com.androiddeft.navigationdrawer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androiddeft.navigationdrawer.FirebaseActivity;
import com.androiddeft.navigationdrawer.R;
import com.androiddeft.navigationdrawer.constants.NavigationDrawerConstants;


public class HomeFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(NavigationDrawerConstants.TAG_HOME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout. fragment_gallery, container, false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);


        return view;
    }





}
