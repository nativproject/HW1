package com.example.hw1_nativsibony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random rand = new Random();
    private Field[] drawables = R.drawable.class.getDeclaredFields();
    private ArrayList<String> monsterList = new ArrayList<>();
    private Button main_BTN_hit;
    private TextView main_LBL_score_left, main_LBL_score_right, main_LBL_player_left, main_LBL_player_right;
    private ImageView main_SVG_card_left, main_SVG_card_right, main_SVG_player_left, main_SVG_player_right;
    private int score_right = 100, score_left = 100, image_id;
    // cdhs = clubs diamonds hearts spades  jqka = jack queen king ace
    private final String card_type = "cdhs", card_num = "jqka23456789";
    private final String IMG_ID_RIGHT = "IMG_ID_RIGHT", IMG_ID_LEFT = "IMG_ID_LEFT";
    private String player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlayersList();
        findViews();
        initViews();
        onClickEvent(main_BTN_hit);
        // TODO: 11/11/2020 fix destroyed game after return button is pressed
    }

    private void initViews() {
        main_LBL_score_left.setText("" + score_left);
        main_LBL_score_right.setText("" + score_right);

        randomPlayer(main_SVG_player_left, main_LBL_player_left);
        randomPlayer(main_SVG_player_right, main_LBL_player_right);

    }

    private void initPlayersList() {
        int i = 1;
        for (Field drawable : drawables) {
            if (drawable.getName().startsWith("m" + i) && i < 27) {
                monsterList.add(drawable.getName());
                i++;
            }
        }
    }

    private void findViews() {
        main_SVG_player_left = findViewById(R.id.main_SVG_player_left);
        main_SVG_player_right = findViewById(R.id.main_SVG_player_right);
        main_SVG_card_left = findViewById(R.id.main_SVG_card_left);
        main_SVG_card_right = findViewById(R.id.main_SVG_card_right);
        main_LBL_score_left = findViewById(R.id.main_LBL_score_left);
        main_LBL_score_right = findViewById(R.id.main_LBL_score_right);
        main_LBL_player_left = findViewById(R.id.main_LBL_player_left);
        main_LBL_player_right = findViewById(R.id.main_LBL_player_right);
        main_BTN_hit = (Button) findViewById(R.id.main_BTN_hit);
    }

    public void onClickEvent(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWinnerActivity();
            }
        });
    }

    private boolean isPlayable() {
        int strike_from_left = fixCardValues(nextCard(main_SVG_card_left)),
                strike_from_right = fixCardValues(nextCard(main_SVG_card_right));
        score_left -= strike_from_right;
        score_right -= strike_from_left;

        // show winner
        if (score_left <= 0 && score_right <= 0) {
            return true;
        } else if (score_right <= 0) {
            return true;
        } else if (score_left <= 0) {
            return true;
        } else {
            main_LBL_score_left.setText("" + score_left);
            main_LBL_score_right.setText("" + score_right);
            return false;
        }
    }

    private int fixCardValues(int num) {
        if (num == 49)          // Ace
            return num - 35;
        else if (num == 59)     // King
            return num - 46;
        else if (num == 65)     // Queen
            return num - 53;
        else if (num == 58)     // Jack
            return num - 47;
        else
            return num;
    }

    private int nextCard(ImageView img) {
        char type_res = card_type.charAt(rand.nextInt(card_type.length()));
        char num_res = card_num.charAt(rand.nextInt(card_num.length()));
        String card = "ornamental_" + type_res + "_" + num_res;
        image_id = img.getResources().getIdentifier(card, "drawable", getPackageName());
        img.setImageResource(image_id);
        int res = num_res - '0';
        return res;
    }

    private void randomPlayer(ImageView img, TextView tv) {
        player = monsterList.get(rand.nextInt(monsterList.size()));
        image_id = img.getResources().getIdentifier(player, "drawable", getPackageName());
        img.setImageResource(image_id);
        String[] name = player.split("_");
        tv.setText(name[1]);
    }

    public void openWinnerActivity() {
        Intent intent = new Intent(this, WinnerActivity.class);
        startActivity(intent);
    }

    // TODO: 11/11/2020 this not working correctly
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}