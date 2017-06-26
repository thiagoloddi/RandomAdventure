package com.games.rasta.randomadventure.presentation.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.games.rasta.randomadventure.R;
import com.games.rasta.randomadventure.models.Coords;
import com.games.rasta.randomadventure.models.Dungeon;
import com.games.rasta.randomadventure.presentation.activities.GameActivity;
import com.games.rasta.randomadventure.presentation.adapters.MapAdapter;
import com.games.rasta.randomadventure.presentation.controllers.GameController;

public class PadFragment extends Fragment implements View.OnClickListener {

  public static final String NAME = "PAD_FRAGMENT";

  public static final int UP = 1;
  public static final int DOWN = 2;
  public static final int LEFT = 3;
  public static final int RIGHT = 4;

  private FloatingActionButton left;
  private FloatingActionButton right;
  private FloatingActionButton up;
  private FloatingActionButton down;
  private FloatingActionButton explore;

  private RelativeLayout mView;

  public PadFragment() {
    // Required empty public constructor
  }

  public static PadFragment newInstance() {
    PadFragment fragment = new PadFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public RelativeLayout onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    this.mView = (RelativeLayout) inflater.inflate(R.layout.fragment_pad, container, false);
    createView();
    return mView;
  }

  private void createView() {
    this.left = (FloatingActionButton) mView.findViewById(R.id.button_move_left);
    this.right = (FloatingActionButton) mView.findViewById(R.id.button_move_right);
    this.up = (FloatingActionButton) mView.findViewById(R.id.button_move_up);
    this.down = (FloatingActionButton) mView.findViewById(R.id.button_move_down);
    this.explore = (FloatingActionButton) mView.findViewById(R.id.button_explore);

    left.setOnClickListener(this);
    right.setOnClickListener(this);
    up.setOnClickListener(this);
    down.setOnClickListener(this);
    explore.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    GameActivity activity = (GameActivity) getActivity();
    Coords coords = activity.getPlayerPosition();
    if(v == up) {
      coords = new Coords(coords.getX(), coords.getY() - 1);
      activity.setPlayerPosition(coords);
    }
    if(v == down) {
      coords = new Coords(coords.getX(), coords.getY() + 1);
      activity.setPlayerPosition(coords);
    }
    if(v == left) {
      coords = new Coords(coords.getX() - 1, coords.getY());
      activity.setPlayerPosition(coords);
    }
    if(v == right) {
      coords = new Coords(coords.getX() + 1, coords.getY());
      activity.setPlayerPosition(coords);
    }
    if(v == explore) {
      Dungeon dungeon = activity.getGameApplication().getDungeonFactory().createDungeon(coords);
      MapAdapter adapter = new MapAdapter(dungeon, MapAdapter.DUNGEON, activity);
      activity.getMapFragment().mapContainer.setAdapter(adapter);
      adapter.notifyDataSetChanged();
    }
  }
}
