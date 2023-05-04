package com.example.maths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class completed extends AppCompatActivity {
    SharedPreferences shrd;
    boolean soundAllowed;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        TextView logo=findViewById(R.id.logo);
        TextView tagLine=findViewById(R.id.tagline);
        Button btn=findViewById(R.id.btn);
          shrd=getSharedPreferences("pref",MODE_PRIVATE);
          soundAllowed=shrd.getBoolean("sound",true);
          if(soundAllowed){
                mp=MediaPlayer.create(getApplicationContext(),R.raw.final_win);
                mp.start();
          }
          Typeface type=Typeface.createFromAsset(getAssets(),"pala.ttf");
          logo.setTypeface(type);
          tagLine.setTypeface(type);
          btn.setTypeface(type);

    }
    public  void back_home(View view)
    {
        if(soundAllowed)
        {
            mp.stop();
           MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.btn_click);
           mp.start();
        }
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
