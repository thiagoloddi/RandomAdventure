package com.games.rasta.randomadventure.presentation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.games.rasta.randomadventure.R;

public class HomeActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    createView();

  }

  private void createView() {
    Button newGameButton = (Button) findViewById(R.id.button_new_game);

    newGameButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        newGame();
      }
    });
  }

  private void newGame() {
    Intent intent = new Intent(this, GameActivity.class);
    startActivity(intent);
  }
}
