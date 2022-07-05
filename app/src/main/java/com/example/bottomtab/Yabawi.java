package com.example.bottomtab;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Yabawi {
    public boolean isHaveBall = false;
    OnYabawiClickListener onYabawiClickListener;
    FrameLayout yabawi_frameLayout;
    ImageView ball_iv;
    ImageView cup_iv;
    ImageView tree;

    public interface OnYabawiClickListener{
        void onClick(Yabawi yabawi);
    }

    public Yabawi(FrameLayout yabawi_frameLayout, ImageView ball_iv, ImageView cup_iv) {
        this.yabawi_frameLayout = yabawi_frameLayout;
        this.ball_iv = ball_iv;
        this.cup_iv = cup_iv;
        Yabawi yabawi = this;
        yabawi_frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onYabawiClickListener.onClick(yabawi);
            }
        });
    }

    public void setOnYabawiClickListener(OnYabawiClickListener listener){
        onYabawiClickListener = listener;
    }

    public View getView(){
        return yabawi_frameLayout;
    }

    public void openYabawiCup(){
        cup_iv.setVisibility(View.INVISIBLE);
        if(isHaveBall){
            ball_iv.setVisibility(View.VISIBLE);
        }else{
            ball_iv.setVisibility(View.INVISIBLE);
        }
    }

    public void closeYabawicup(){
        cup_iv.setVisibility(View.VISIBLE);
        ball_iv.setVisibility(View.VISIBLE);
    }

}