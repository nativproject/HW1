package com.example.hw1_nativsibony;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Random rand = new Random();
    private final Handler mHanbler = new Handler();
    private final Field[] fields = R.drawable.class.getDeclaredFields();
    private ArrayList<String> monsterList = new ArrayList<>();
    private Button main_BTN_hit;
    private DatabaseReference dbRef;
    private TextView main_LBL_score_left, main_LBL_score_right, main_LBL_player_left, main_LBL_player_right;
    private ImageView main_SVG_card_left, main_SVG_card_right, main_SVG_player_left, main_SVG_player_right;
    private ProgressBar health_right, health_left;
    private int score_right = 0, score_left = 0, image_id;
    // cdhs: clubs diamonds hearts spades,  jqka: jack queen king ace
    private final String card_type = "cdhs", card_num = "jqka23456789", Draw = "It's A Draw!", keep_play = "ok", MODE = "MODE";
    private String player, card, gameType;
    private boolean flag = false;
    private View v;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlayersList();
        findViews();
        initViews();
        gameType = getIntent().getExtras().getString(MODE);
        if (gameType.equals("1")) {
            main_BTN_hit.setVisibility(View.GONE);
            startRunning(v);
        }
    }

    private void startRunning(View v) {
        mToastRunnable.run();
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            autoPlay();
            mHanbler.postDelayed(this, 500);
        }
    };

    private void autoPlay() {
        String status = isPlayable();
        String left = main_LBL_player_left.getText().toString(),
                right = main_LBL_player_right.getText().toString();
        if (!flag) {
            if (!status.equals(keep_play)) {
                if (status.equals(Draw)) {
                    openWinnerActivity(Draw);
                    flag = true;
                } else if (status.equals(left)) {
                    updateData(left);
                    openWinnerActivity(left);
                    flag = true;
                } else {
                    updateData(right);
                    openWinnerActivity(right);
                    flag = true;
                }
            }
        }
    }

    private void initViews() {
        main_LBL_score_left.setText("" + score_left);
        main_LBL_score_right.setText("" + score_right);
        main_BTN_hit.setOnClickListener(this);
        randomPlayer(main_SVG_player_left, main_LBL_player_left);
        randomPlayer(main_SVG_player_right, main_LBL_player_right);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        health_right = (ProgressBar) findViewById(R.id.health_right);
        health_left = (ProgressBar) findViewById(R.id.health_left);
        health_right.setProgressTintList(ColorStateList.valueOf(Color.RED));
        health_left.setProgressTintList(ColorStateList.valueOf(Color.RED));

    }

    private String isPlayable() {
        int strike_from_left = fixCardValues(nextCard(main_SVG_card_left)),
                strike_from_right = fixCardValues(nextCard(main_SVG_card_right));
        score_left += strike_from_right;
        health_right.setProgress(100 - score_right);
        score_right += strike_from_left;
        health_left.setProgress(100 - score_left);

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
        for (Field field : fields) {
            if (field.getName().startsWith("m1")) {
                monsterList.add(field.getName());
            }
        }
    }

    private void randomPlayer(ImageView img, TextView tv) {
        player = monsterList.get(rand.nextInt(monsterList.size()));
        image_id = img.getResources().getIdentifier(player, "drawable", getPackageName());
        img.setImageResource(image_id);
        String[] name = player.split("_");
        tv.setText(name[1]);
    }

    public void openWinnerActivity(String winner) {
        Intent intent = new Intent(getApplicationContext(), WinnerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        this.finish();
        intent.putExtra("winner", winner);
        startActivity(intent);
    }

    private void updateData(String label) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("HighScore");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue().toString();
                    Integer score = ds.child("score").getValue(Integer.class);
                    if (label.equals(name)) {
                        ds.child("score").getRef().setValue(score + 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                        updateData(left);
                        openWinnerActivity(left);
                        flag = true;
                    } else {
                        updateData(right);
                        openWinnerActivity(right);
                        flag = true;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (gameType.equals("0")) {
            super.onBackPressed();
            this.finish();
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
        this.finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}