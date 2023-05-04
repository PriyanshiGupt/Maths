package com.example.maths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NextLevel extends AppCompatActivity {
    SharedPreferences shrd;
    MediaPlayer mp;
    int eligible;
    boolean soundAllowed;
    SharedPreferences.Editor editor;
    String next_lev;
    public static final String key="next_level";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_level);
        Intent intent=getIntent();
        next_lev=intent.getStringExtra(key);
        shrd=getSharedPreferences("pref",MODE_PRIVATE);
        eligible=shrd.getInt("eligible",1);
        soundAllowed=shrd.getBoolean("sound",true);
        int level_no=Integer.parseInt(next_lev);
        if(level_no==eligible+1)
        {
            editor=shrd.edit();
            eligible++;
            editor.putInt("eligible",eligible);
            editor.apply();
        }
        if(soundAllowed)
        {
            mp=MediaPlayer.create(getApplicationContext(),R.raw.correct_ans);
            mp.start();
        }
        Typeface type=Typeface.createFromAsset(getAssets(),"pala.ttf");
        TextView correct=findViewById(R.id.correct);
        TextView tag_line=findViewById(R.id.tag_line);
        Button btn=findViewById(R.id.next_but);
        btn.setTypeface(type);
        tag_line.setTypeface(type);
        correct.setTypeface(type);

    }
    public void nextLevel(View view)
    {
        if(soundAllowed){
            mp.stop();
            mp=MediaPlayer.create(getApplicationContext(),R.raw.btn_click);
            mp.start();
        }
        Intent intent=new Intent(this, levelCreater.class);
        intent.putExtra(levelCreater.key,next_lev);
        startActivity(intent);
            }
}