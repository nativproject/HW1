package com.example.hw1_nativsibony;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioPlay {

    public static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;

    public static void playAudio(Context c, int id) {
        mediaPlayer = MediaPlayer.create(c, id);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        mediaPlayer.setVolume(0.05f, 0.05f);
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
    //// TODO: 12/12/2020 Audio resume function

    public static void stopAudio() {
        mediaPlayer.stop();
    }
}
