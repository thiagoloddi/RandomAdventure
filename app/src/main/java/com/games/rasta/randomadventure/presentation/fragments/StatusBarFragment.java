package com.games.rasta.randomadventure.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.games.rasta.randomadventure.R;

public class StatusBarFragment extends Fragment {

  public static final String NAME = "STATUS_BAR_FRAGMENT";

  public static StatusBarFragment newInstance() {
    StatusBarFragment fragment = new StatusBarFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  public StatusBarFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_status_bar, container, false);
  }

}
