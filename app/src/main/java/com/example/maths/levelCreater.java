package com.example.maths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class levelCreater extends AppCompatActivity{
    SharedPreferences shrd;
    Boolean soundAllowed;
    public static final String key="WhichLevel";
    String LevelNo,ques,lev,anss,hintt;
    TextView answer,stat,question,levelNo;
    String ans="";
    Typeface type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelques);

        shrd=getSharedPreferences("pref",MODE_PRIVATE);
        soundAllowed=shrd.getBoolean("sound",true);

        answer= findViewById(R.id.answer);
        stat=findViewById(R.id.status);
        answer.setText(ans);
        question=findViewById(R.id.question);
        levelNo=findViewById(R.id.levelNo);

        Intent intent=getIntent();
        LevelNo=intent.getStringExtra(key);
        ques="level"+LevelNo+"Q";
        lev="level"+LevelNo;
        anss="level"+LevelNo+"A";
        hintt="level"+LevelNo+"hint";

        question.setText(getStringResourceByName(ques));
        levelNo.setText(getStringResourceByName(lev).toUpperCase());
        Button enter=findViewById(R.id.enter);
        type=Typeface.createFromAsset(getAssets(),"pala.ttf");
        setButtons();
        question.setTypeface(type);
        levelNo.setTypeface(type);
        answer.setTypeface(type);
        stat.setTypeface(type);
        enter.setTypeface(type);
    }

    public void setButtons()
    {
        for(int i=0;i<=9;i++)
        {
            String id="l"+i;
            int resid=getResources().getIdentifier(id,"id",getPackageName());
            Button but=findViewById(resid);
            String val=Integer.toString(i);
            but.setText(val);
            but.setTag(i);
            but.setBackgroundColor(getResources().getColor(R.color.buttonBackground));
            but.setTextColor(getResources().getColor(R.color.buttonText));
            but.setTextSize(14);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
            int px=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,getResources().getDisplayMetrics());
            params.setMargins(px,px,px,px);
            but.setLayoutParams(params);
            but.setTypeface(type);
        }
    }
    public void enter(View view){
        int TrueAns=Integer.parseInt(getStringResourceByName(anss));
        if(Integer.parseInt(ans)==TrueAns)
        {

            stat.setText("");

            int numm=Integer.parseInt(LevelNo);
            if(numm==65){
                Intent intent=new Intent(this, completed.class);
                startActivity(intent);
            }
            else{

                numm+=1;

                Intent intent=new Intent(this, NextLevel.class);
                intent.putExtra(NextLevel.key,Integer.toString(numm));
                startActivity(intent);
            }
        }
        else{
            if(soundAllowed)
            {
                MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.wrong_ans);
                mp.start();
            }
            stat.setText(R.string.wrong);
        }
        ans="";
        answer.setText(ans);
        Button btn=(Button)view;
        btn.setTypeface(type);
    }
    public void hint(View view){
        stat.setText(getStringResourceByName(hintt));
        sound_eff();
    }
    private String getStringResourceByName(String aString){
        String packageName = getPackageName();
        int resId=getResources().getIdentifier(aString,"string",packageName);
        if(resId==0){return getString(R.string.exit);}
        return getString(resId);
    }



    public void type(View view){
        if(ans.length()<7) {
            ans = ans + view.getTag().toString();
            answer.setText(ans);
            sound_eff();
        }

    }
    public void back(View view){
        Intent intent=new Intent(this, levels.class);
        startActivity(intent);
    }
    public void empty(View view){
        ans="";
        answer.setText(ans);
    }
    public void sound_eff(){
        if(soundAllowed)
        {
            MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.btn_click);
            mp.start();
        }
    }
}
