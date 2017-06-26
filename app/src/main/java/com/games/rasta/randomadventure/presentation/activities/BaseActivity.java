package com.games.rasta.randomadventure.presentation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.R;

public class BaseActivity extends AppCompatActivity {

  private GameApplication application;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);

    if(getSupportActionBar() != null)
      getSupportActionBar().hide();

    this.application = (GameApplication) getApplication();
  }

  public GameApplication getGameApplication() {
    return this.application;
  }
}
