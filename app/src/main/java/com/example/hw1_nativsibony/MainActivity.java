package com.example.hw1_nativsibony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Random rand = new Random();
    private Button main_BTN_hit;
    private TextView main_LBL_score_left, main_LBL_score_right;
    private ImageView main_SVG_card_left, main_SVG_card_right;
    private int score_right = 0, score_left = 0;
    private final String card_type = "cdhs"; //clubs diamonds hearts spades
    private final String card_num = "jqka23456789"; //jack queen king ace ...
    private final String IMG_ID_RIGHT = "IMG_ID_RIGHT", IMG_ID_LEFT = "IMG_ID_LEFT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();

        // TODO: 11/11/2020 fix destroyed game after return button is pressed
        if (savedInstanceState != null) {
            int curr_right_img = savedInstanceState.getInt(IMG_ID_RIGHT);
            int curr_left_img = savedInstanceState.getInt(IMG_ID_LEFT);
            main_SVG_card_left.setImageResource(curr_left_img);
            main_SVG_card_left.setImageResource(curr_right_img);
        }
    }

    private void initViews() {
        main_LBL_score_left.setText("" + score_left);
        main_LBL_score_right.setText("" + score_right);
        main_BTN_hit.setOnClickListener(this);
    }

    private void findViews() {
        main_SVG_card_left = findViewById(R.id.main_SVG_card_left);
        main_SVG_card_right = findViewById(R.id.main_SVG_card_right);
        main_LBL_score_left = findViewById(R.id.main_LBL_score_left);
        main_LBL_score_right = findViewById(R.id.main_LBL_score_right);
        main_BTN_hit = (Button) findViewById(R.id.main_BTN_hit);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_BTN_hit) {
            play();
        }
    }

    private void play() {
        if (score_right != 22 && score_left != 22) {
            int res1 = fixCardValue(nextImage(main_SVG_card_left));
            int res2 = fixCardValue(nextImage(main_SVG_card_right));
            if (res1 > res2)
                main_LBL_score_left.setText("" + (++score_left));
            else if (res1 < res2)
                main_LBL_score_right.setText("" + (++score_right));
        }
    }

    private int fixCardValue(int num) {
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

    private int nextImage(ImageView img) {
        char type_res = card_type.charAt(rand.nextInt(card_type.length()));
        char num_res = card_num.charAt(rand.nextInt(card_num.length()));
        String card = "ornamental_" + type_res + "_" + num_res;
        int id = img.getResources().getIdentifier(card, "drawable", getPackageName());
        //Log.d("iddd", "id: " + card);
        img.setImageResource(id);
        int res = num_res - '0';
        return res;
    }

    // TODO: 11/11/2020 this not working correctly
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(IMG_ID_LEFT, main_SVG_card_left.getId());
        outState.putInt(IMG_ID_RIGHT, main_SVG_card_right.getId());
        Log.d("iddd", "id: " + main_SVG_card_left.getId());
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