package com.games.rasta.randomadventure.presentation;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.games.rasta.randomadventure.GameApplication;
import com.games.rasta.randomadventure.R;
import com.games.rasta.randomadventure.engine.MapFactory;
import com.games.rasta.randomadventure.models.Island;

public class HomeActivity extends AppCompatActivity {
  Island map;
  LinearLayout view;
  EditText editText;
  TextView textView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    GameApplication application = (GameApplication) getApplication();
    Double alpha = 0.1 + 0.8*Math.random();
    this.map = application.getMapFactory().createMap(alpha);
    this.view = (LinearLayout) findViewById(R.id.map_container);

    this.textView = (TextView) findViewById(R.id.text_view_alpha);
    this.editText = (EditText) findViewById(R.id.edit_text_alpha);
    textView.setText(alpha.toString());
    Button button = (Button) findViewById(R.id.button_new_map);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String text = editText.getText().toString();
        Double alpha = Math.random();
        if(!text.equals("")) {
          alpha = Double.parseDouble(text);
        }

        if(alpha < 0.1 || alpha >= 1)
          alpha = 0.1 + 0.9*Math.random();
        textView.setText(alpha.toString());
        map = ((GameApplication) getApplication()).getMapFactory().createMap(alpha);
        drawMap();
      }
    });

    this.drawMap();


  }

  public void drawMap() {
    view.removeAllViews();
    for(int i = 0; i < map.size(); i++) {
      LinearLayoutCompat layout = new LinearLayoutCompat(this);
      layout.setOrientation(LinearLayoutCompat.HORIZONTAL);
      for(int j = 0; j < map.size(); j++) {
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new LinearLayoutCompat.LayoutParams(30, 30));

        ImageView img = new ImageView(this);
        img.setLayoutParams(new RelativeLayout.LayoutParams(15, 15));
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) img.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.ALIGN_PARENT_END);

        img.setImageDrawable(getResources().getDrawable(R.drawable.river_bottom_right, null));

        rl.addView(img);

        int tile = map.get(j, i).getType();
        int bgColor;
        switch(tile) {
          case MapFactory.SEA_WATER: bgColor = R.drawable.tile_sea_water; break;
          case MapFactory.LAND: bgColor = R.drawable.tile_grass; break;
          case MapFactory.CLEAR_WATER: bgColor = R.drawable.tile_clear_water; break;
          case MapFactory.BEACH: bgColor = R.drawable.tile_beach; break;
          case MapFactory.FOREST: bgColor = R.drawable.tile_forest; break;
          case MapFactory.HILL: bgColor = R.drawable.tile_hill; break;
          default: bgColor = R.drawable.tile_null; break;

        }

        Drawable background = getResources().getDrawable(bgColor, null);
//        background.setS

        rl.setBackground(background);
        layout.addView(rl);
      }
      view.addView(layout);
    }
  }
}
