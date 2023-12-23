package com.example.isangeet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;

public class PlaySong extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaplayer.stop();
        mediaplayer.release();
        updateseek.interrupt();
    }

    private String[] Resource ={"R.drawable.ok1", "R.drawable.ok2"};

    ConstraintLayout constraintLayout;
    TextView textView;
    ImageView previous,play,next,button;
    ArrayList<File> songs;
    MediaPlayer mediaplayer;
    String textContent;
    String tempSongName;
    SeekBar seekbar;

    Thread updateseek ;
    int position;

    int count =0;
    String ok[];

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        textView = findViewById(R.id.textView2);
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        seekbar = findViewById(R.id.seekBar);
        constraintLayout = findViewById(R.id.abhiraj);
          button = findViewById(R.id.buttonF);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if(count%2==1)
                {
                    button.setImageResource(R.drawable.baseline_favorite_border_24);


                }

                else{
                    button.setImageResource(R.drawable.baseline_favorite_24);



                }
                count++;
            }
        });



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songs = (ArrayList)bundle.getParcelableArrayList("songlist");
        textContent = intent.getStringExtra("currentsong");
        textView.setText(textContent);
        textView.setSelected(true);
        for(int i=0;i<songs.size();i++){
            if(songs.get(i).toString().contains(textContent)){
                tempSongName = songs.get(i).toString();
            }

        }
         position = intent.getIntExtra("position", 0);

        Uri uri = Uri.parse(tempSongName);
        mediaplayer = MediaPlayer.create(this , uri);
        mediaplayer.start();
        seekbar.setMax(mediaplayer.getDuration());



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               mediaplayer.seekTo(seekBar.getProgress());
            }
        });

        updateseek = new Thread(){
            @Override
            public void run() {
                int currentposition =0;
                try{
                    while(currentposition<mediaplayer.getDuration())
                    {
                        currentposition = mediaplayer.getCurrentPosition();
                        seekbar.setProgress(currentposition);
                        sleep(800);
                    }
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };

        updateseek.start();


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaplayer.isPlaying())
                {
                    play.setImageResource(R.drawable.play);
                    mediaplayer.pause();
                }
                else{
                    play.setImageResource(R.drawable.pause);
                    mediaplayer.start();
                }


            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mediaplayer.stop();
                mediaplayer.release();

                if(position!=0)
                {
                   position= position-1;
                }
                else {
                    position = songs.size()-1;
                }

                Uri uri = Uri.parse(songs.get(position).toString());
                mediaplayer = MediaPlayer.create(getApplicationContext() , uri);
                mediaplayer.start();
                play.setImageResource(R.drawable.pause);
                seekbar.setMax(mediaplayer.getDuration());
                seekbar.setProgress(0);

                textContent = songs.get(position).getName().toString();
                textView.setText(textContent);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mediaplayer.stop();
                mediaplayer.release();

                if(position!=songs.size()-1)
                {
                    position= position+1;
                }
                else {
                    position = 0;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaplayer = MediaPlayer.create( getApplicationContext() , uri);
                mediaplayer.start();
                play.setImageResource(R.drawable.pause);
                seekbar.setMax(mediaplayer.getDuration());
                seekbar.setProgress(0);

                textContent = songs.get(position).getName().toString();
                textView.setText(textContent);
            }
        });


    }
}