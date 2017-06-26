package com.games.rasta.randomadventure.presentation.fragments;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.R;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Map;
import com.games.rasta.randomadventure.models.Tile;
import com.games.rasta.randomadventure.presentation.activities.GameActivity;
import com.games.rasta.randomadventure.presentation.adapters.MapAdapter;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

  public static final String NAME = "MAP_FRAGMENT";
  public static final int NUM_COLUMNS = 15;

  private View mView;
  public GridView mapContainer;

  public MapFragment() {
    // Required empty public constructor
  }

  public static MapFragment newInstance() {
    MapFragment fragment = new MapFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.mView = inflater.inflate(R.layout.fragment_map, container, false);
    drawMap();
    return mView;
  }

  public void drawMap() {
    mapContainer = (GridView) mView.findViewById(R.id.map_container);
    GameActivity activity = (GameActivity) getActivity();

    ViewGroup.LayoutParams lp = mapContainer.getLayoutParams();
    lp.height = activity.getMapWidth();
    lp.width = activity.getMapWidth();
    mapContainer.setLayoutParams(lp);
    mapContainer.setNumColumns(NUM_COLUMNS);
    mapContainer.setAdapter(new MapAdapter(activity.getMap(), MapAdapter.ISLAND, activity));

    RelativeLayout shadow = (RelativeLayout) mView.findViewById(R.id.layout_inner_shadow);
    GradientDrawable gd = (GradientDrawable) shadow.getBackground();
    gd.setGradientRadius(activity.getMapWidth());
    shadow.setLayoutParams(new RelativeLayout.LayoutParams(activity.getMapWidth(), activity.getMapWidth()));
  }
}
