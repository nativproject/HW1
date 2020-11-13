package com.example.hw1_nativsibony;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Random rand = new Random();
    private final Field[] fields = R.drawable.class.getDeclaredFields();
    ;
    private ArrayList<String> monsterList = new ArrayList<>();
    private Button main_BTN_hit;
    private TextView main_LBL_score_left, main_LBL_score_right, main_LBL_player_left, main_LBL_player_right;
    private ImageView main_SVG_card_left, main_SVG_card_right, main_SVG_player_left, main_SVG_player_right;
    private int score_right = 0, score_left = 0, image_id;
    // cdhs = clubs diamonds hearts spades  jqka = jack queen king ace
    private final String card_type = "cdhs", card_num = "jqka23456789", Draw = "It's A Draw!", keep_play = "f";
    private String player, card;
    private boolean flag = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlayersList();
        findViews();
        initViews();
    }

    private void initViews() {
        main_LBL_score_left.setText("" + score_left);
        main_LBL_score_right.setText("" + score_right);
        main_BTN_hit.setOnClickListener(this);
        randomPlayer(main_SVG_player_left, main_LBL_player_left);
        randomPlayer(main_SVG_player_right, main_LBL_player_right);
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

    private String isPlayable() {
        int strike_from_left = fixCardValues(nextCard(main_SVG_card_left)),
                strike_from_right = fixCardValues(nextCard(main_SVG_card_right));
        score_left += strike_from_right;
        score_right += strike_from_left;

//        Log.d("ddd", "left: " + score_left);
//        Log.d("ddd", "right: " + score_right);

        if (score_left >= 100 && score_right >= 100) {
            main_LBL_score_left.setText("" + 100);
            main_LBL_score_right.setText("" + 100);
            return Draw;
        } else if (score_right >= 100) {
            main_LBL_score_right.setText("" + 100);
            return main_LBL_player_left.getText().toString();
        } else if (score_left >= 100) {
            main_LBL_score_left.setText("" + 100);
            return main_LBL_player_right.getText().toString();
        } else {
            main_LBL_score_left.setText("" + score_left);
            main_LBL_score_right.setText("" + score_right);
            return keep_play;
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
        card = "ornamental_" + type_res + "_" + num_res;
        image_id = img.getResources().getIdentifier(card, "drawable", getPackageName());
        img.setImageResource(image_id);
        int res = num_res - '0';
        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initPlayersList() {
        int i = 1;
        for (Field field : fields)
            if (field.getName().startsWith("m" + i))
                monsterList.add(field.getName());

//        for (String id : monsterList)
//            Log.d("ddd", "" + id);

    }

    private void randomPlayer(ImageView img, TextView tv) {
        player = monsterList.get(rand.nextInt(monsterList.size()));
        image_id = img.getResources().getIdentifier(player, "drawable", getPackageName());
        img.setImageResource(image_id);
        String[] name = player.split("_");
        tv.setText(name[1]);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void openWinnerActivity(String winner) {
        Intent intent = new Intent(this, WinnerActivity.class);
        intent.putExtra("winner", winner);
        startActivity(intent);
    }

    // TODO: 11/11/2020 this not working correctly
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        String left = main_LBL_player_left.getText().toString(),
                right = main_LBL_player_right.getText().toString();
        if (v.getId() == R.id.main_BTN_hit) {
            if (!flag) {
                if (!isPlayable().equals(keep_play)) {
                    if (isPlayable().equals(Draw)) {
                        openWinnerActivity(Draw);
                        flag = true;
                    } else if (isPlayable().equals(left)) {
                        openWinnerActivity(left);
                        flag = true;
                    } else {
                        openWinnerActivity(right);
                        flag = true;
                    }
                }
            }
        }
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