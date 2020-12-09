package com.example.hw1_nativsibony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

public class WinnerActivity extends AppCompatActivity implements View.OnClickListener {
    Button winner_BTN_new_game;
    TextView winner_LBL, isWinner_LBL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        findViews();
        initViews();
    }

    private void initViews() {
        winner_BTN_new_game.setOnClickListener(this);
        String winner = getIntent().getExtras().getString("winner");
        if (winner.equals("It's A Draw!"))
            isWinner_LBL.setText("");
        winner_LBL.setText(winner);
    }

    private void findViews() {
        winner_LBL = findViewById(R.id.winner_LBL);
        isWinner_LBL = findViewById(R.id.isWinner_LBL);
        winner_BTN_new_game = (Button) findViewById(R.id.winner_BTN_new_game);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.winner_BTN_new_game)
            openMainActivity();
    }

    public void openMainActivity() {
        this.finish();
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }
}