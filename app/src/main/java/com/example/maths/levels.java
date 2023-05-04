package com.example.maths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.TextView;

public class levels extends AppCompatActivity {
    SharedPreferences getShared;
    int eligible;
    Boolean soundAllowed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        getShared=getSharedPreferences("pref",MODE_PRIVATE);
        eligible=getShared.getInt("eligible",1);
        soundAllowed=getShared.getBoolean("sound",true);
        setButtons();
        updateEligible();
        TextView txt=findViewById(R.id.levelNo);
        Typeface type=Typeface.createFromAsset(getAssets(),"pala.ttf");
        txt.setTypeface(type);
    }
    public void setButtons()
    {
       for(int i=1;i<=65;i++)
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
            params.setMargins(4,4,4,4);
            but.setLayoutParams(params);
            final int x=i;
            but.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    if(soundAllowed&&x<=eligible)
                    {
                        MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.btn_click);
                        mp.start();
                    }
                    openlevel(view);

                }
            });
            Typeface type = Typeface.createFromAsset(getAssets(), "pala.ttf");
            but.setTypeface(type);
        }
    }
    public  void updateEligible()
    {
        for(int i=1;i<=eligible;i++)
        {
            String id="l"+i;
            int resid=getResources().getIdentifier(id,"id",getPackageName());
            Button but=findViewById(resid);
            but.setBackgroundColor(getResources().getColor(R.color.buttonDarkBackground));
        }
    }
    public void openlevel(View view){
        if(Integer.parseInt(view.getTag().toString())<=eligible){
            Intent intent=new Intent(this,levelCreater.class);
            intent.putExtra(levelCreater.key,view.getTag().toString());
            startActivity(intent);
        }

    }

    public void back(View view){
        Intent intent =new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
