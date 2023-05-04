package com.example.maths;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences shrd;
    int eligible;
    boolean soundAllowed;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shrd=getSharedPreferences("pref",MODE_PRIVATE);
        eligible=shrd.getInt("eligible",1);
        soundAllowed=shrd.getBoolean("sound",true);
        setSound();
        int[] ids ={R.id.btn,R.id.levels,R.id.sounds_off,R.id.restart,R.id.exit};
        int[] textids={R.id.textView1,R.id.correct};
        Typeface type = Typeface.createFromAsset(getAssets(), "pala.ttf");
        for (int id : ids) {
            Button btn = findViewById(id);
            btn.setTypeface(type);
        }
        for (int id : textids) {
            TextView txt = findViewById(id);
            txt.setTypeface(type);
        }
    }
    public void play(View view){
        SoundEff();
        Intent intent=new Intent(this,levelCreater.class);
        intent.putExtra(levelCreater.key,Integer.toString(eligible));
        startActivity(intent);
    }
    public void levels(View view){
        SoundEff();
        Intent intent=new Intent(this,levels.class);
        startActivity(intent);
    }
    public void soundToggle(View view){
        editor=shrd.edit();
        soundAllowed= !soundAllowed;
        editor.putBoolean("sound",soundAllowed);
        editor.apply();
        setSound();
        SoundEff();
    }
    public void restart(View view){
        SoundEff();
        new AlertDialog.Builder(this)
                .setTitle("Clear Data?")
                .setMessage("If you clear the data, you will restart the game from the first level.")
                .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor=shrd.edit();
                        eligible=1;
                        editor.putInt("eligible",eligible);
                        editor.apply();
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
    public void exit(View view){
        SoundEff();
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Exit Game? ")
                .setPositiveButton("Exit",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
    public void SoundEff()
    {
        if(soundAllowed)
        {
            MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.btn_click);
            mp.start();
        }
    }
    public void setSound()
    {
        Button btn=findViewById(R.id.sounds_off);
        Button ideal=findViewById(R.id.restart);
        int padding_left=ideal.getPaddingLeft();
        int drawable_padding=ideal.getCompoundDrawablePadding();

        Drawable img;
        if(soundAllowed)
        {
            btn.setText(R.string.soundOn);
             img= getResources().getDrawable(R.drawable.ic_volume_up_black_24dp);
        }
        else{
            btn.setText(R.string.soundOff);
            img= getResources().getDrawable(R.drawable.ic_volume_off_black_24dp);
        }
        btn.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);
        btn.setCompoundDrawablePadding(drawable_padding);
        btn.setPadding(padding_left,0,padding_left,0);
    }
}
