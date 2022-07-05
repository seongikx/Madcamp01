package com.example.bottomtab;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomtab.YabawiActivity;

import java.util.ArrayList;
import java.util.Random;

public class YabawiActivity extends AppCompatActivity {

    Random random = new Random();
    Yabawi yabawi11, yabawi12, yabawi13;
    ImageButton start_btn;
    TextView countDown_tv;
    TextView level;
    ViewCircleMovingAnimation viewCircleMovingAni1 = new ViewCircleMovingAnimation();
    ViewCircleMovingAnimation viewCircleMovingAni2 = new ViewCircleMovingAnimation();
    ArrayList<Yabawi> yabawiArrayList = new ArrayList<>();
    ImageView allheart;
    ImageView twoheart;
    ImageView oneheart;
    ImageView zeroheart;
    TextView score;

    int score1 = 0;
    int heart = 3;
    int lev = 1;
    int playIndex = 5;
    int playCount = 0;
    float duration = 0.7F;

    GameStartReadyThread gameStartReadThread;
    boolean gameStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yabawi_main);

        yabawi11 = new Yabawi(findViewById(R.id.yabawi11_frameLayout), findViewById(R.id.ball1), findViewById(R.id.cup1));
        yabawi12 = new Yabawi(findViewById(R.id.yabawi12_frameLayout), findViewById(R.id.ball2), findViewById(R.id.cup2));
        yabawi13 = new Yabawi(findViewById(R.id.yabawi13_frameLayout), findViewById(R.id.ball3), findViewById(R.id.cup3));
        start_btn = findViewById(R.id.start_btn);
        countDown_tv = findViewById(R.id.countDown_tv);
        level = findViewById(R.id.level);
        allheart = findViewById(R.id.heart);
        twoheart = findViewById(R.id.heart2);
        oneheart = findViewById(R.id.heart3);
        zeroheart = findViewById(R.id.heart4);
        score = findViewById(R.id.scoreboard);


        yabawiArrayList.add(yabawi11);
        yabawiArrayList.add(yabawi12);
        yabawiArrayList.add(yabawi13);


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gameStart) {
                    if (!viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying) {
                        gameStart = true;
                        for (int i = 0; i < yabawiArrayList.size(); i++) {
                            yabawiArrayList.get(i).isHaveBall = false;
                        }
                        yabawiArrayList.get(random.nextInt(yabawiArrayList.size())).isHaveBall = true;
                        allYabawiCupOpen();
                        countDown_tv.setVisibility(view.VISIBLE);
                        gameStartReadThread = new GameStartReadyThread();
                        gameStartReadThread.start();

                    }
                }
            }
        });

        yabawi11.setOnYabawiClickListener(new Yabawi.OnYabawiClickListener() {

            @Override
            public void onClick(Yabawi yabawi) {
                allYabawiCupOpen();
                resultYabawiGame(yabawi.isHaveBall);
            }
        });

        yabawi12.setOnYabawiClickListener(new Yabawi.OnYabawiClickListener() {
            @Override
            public void onClick(Yabawi yabawi) {
                allYabawiCupOpen();
                resultYabawiGame(yabawi.isHaveBall);
            }
        });

        yabawi13.setOnYabawiClickListener(new Yabawi.OnYabawiClickListener() {
            @Override
            public void onClick(Yabawi yabawi) {
                allYabawiCupOpen();
                resultYabawiGame(yabawi.isHaveBall);
            }
        });
    }

    private void startAnimationCircle(Yabawi yabawi1, Yabawi yabawi2){
        boolean yabawi1_isHaveBall = yabawi1.isHaveBall;
        boolean yabawi2_isHaveBall = yabawi2.isHaveBall;
        yabawi1.isHaveBall = yabawi2_isHaveBall;
        yabawi2.isHaveBall = yabawi1_isHaveBall;
        viewCircleMovingAni1.setMovingAnimationFinishListener(new ViewCircleMovingAnimation.MovingAnimationFinishListenger() {
            @Override
            public void onFinish(float fromXPosition, float fromYPosition, float toXPosition, float toYPosition) {
                if(!viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying){
                    if(playCount <= playIndex){
                        Yabawi[] yabawis = getRandomYabawi();
                        startAnimationCircle(yabawis[0], yabawis[1]);
                    }else {
                        playCount = 0;
                        gameStart = false;
                    }
                    playCount ++;
                }

            }
        });
        viewCircleMovingAni2.setMovingAnimationFinishListener(new ViewCircleMovingAnimation.MovingAnimationFinishListenger() {
            @Override
            public void onFinish(float fromXPosition, float fromYPosition, float toXPosition, float toYPosition) {
                if(!viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying){
                    if(playCount <= playIndex){
                        Yabawi[] yabawis = getRandomYabawi();
                        startAnimationCircle(yabawis[0], yabawis[1]);
                    }else {
                        playCount = 0;
                        gameStart = false;
                    }
                    playCount ++;
                }
            }
        });
        viewCircleMovingAni1.startViewCircleMovingAnimation(yabawi1.getView(),yabawi1.getView().getX(),yabawi1.getView().getY(),
                yabawi2.getView().getX(),yabawi2.getView().getY(),duration,true);
        viewCircleMovingAni2.startViewCircleMovingAnimation(yabawi2.getView(),yabawi2.getView().getX(),yabawi2.getView().getY(),
                yabawi1.getView().getX(),yabawi1.getView().getY(),duration,false);
    }

    private Yabawi [] getRandomYabawi(){
        Yabawi[] yabawis = new Yabawi[2];
        int randomInt1;
        int randomInt2;
        while(true){
            randomInt1 = random.nextInt(yabawiArrayList.size());
            randomInt2 = random.nextInt(yabawiArrayList.size());
            if(randomInt1 != randomInt2){
                break;
            }
        }
        yabawis[0] = yabawiArrayList.get(randomInt1);
        yabawis[1] = yabawiArrayList.get(randomInt2);

        return yabawis;
    }

    private void allYabawiCupOpen(){
        if(!viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying) {
            for (int i = 0; i < yabawiArrayList.size(); i++) {
                yabawiArrayList.get(i).openYabawiCup();
            }
        }
    }
    private void allYabawiCupClose(){
        if(!viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying) {
            for (int i = 0; i < yabawiArrayList.size(); i++) {
                yabawiArrayList.get(i).closeYabawicup();
            }
        }
    }

    public void gameStart(){
        if( !viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying){
            allYabawiCupClose();
            Yabawi []  yabawis = getRandomYabawi();
            startAnimationCircle(yabawis[0],yabawis[1]);
        }
    }

    private void resultYabawiGame(Boolean isHaveBall) {
        if (!viewCircleMovingAni1.isPlaying && !viewCircleMovingAni2.isPlaying) {
            if (!gameStart) {
                countDown_tv.setVisibility(View.VISIBLE);
                if (isHaveBall) {
                    if(duration == 0.3F){
                        countDown_tv.setText("모든 단계를 클리어 하셨습니다.");
                        lev = 1;
                        level.setText("축하드립니다!");
                        yabwiSpeedfree();
                        score1++;
                        score.setText("클리어 횟수 :"+ score1);
                    }
                    else {countDown_tv.setText("정답입니다.");
                        level.setText("성공!");
                        lev++;
                        yabwiSpeedUP();}
                } else {
                    countDown_tv.setText("틀렸습니다.");
                    level.setText("실패ㅠㅠ");
                    lev--;
                    heart--;
                    HeartScore();
                    yabwiSpeedDown();
                    if(heart <= -1 ){
                        level.setText("게임 오버 !");
                        countDown_tv.setText("게임에 재능이 없습니다..");
                        yabwiSpeedfree();
                        lev = 1;
                    }
                }
            }
        }
    }

    private void yabwiSpeedUP(){
        duration -= 0.2F;
    }
    private void yabwiSpeedDown(){
        duration +=0.2F;
    }
    private void yabwiSpeedfree(){
        duration = 0.7F;
    }

    private void HeartScore(){
        if(heart == 2){
            allheart.setVisibility(View.INVISIBLE);
        }else if(heart == 1){
            twoheart.setVisibility(View.INVISIBLE);
        }else if(heart ==0){
            oneheart.setVisibility(View.INVISIBLE);
        }
    }

    private void HeartRecovery(){
        allheart.setVisibility(View.VISIBLE);
        twoheart.setVisibility(View.VISIBLE);
        oneheart.setVisibility(View.VISIBLE);
    }



    class GameStartReadyThread extends Thread{
        GameStartReadyHandler handler = new GameStartReadyHandler();
        @Override
        public void run(){
            super.run();
            for(int i=3; i>-1; i--){
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("i", i);
                message.setData(bundle);
                handler.sendMessage(message);
                bundle = null;
                try {
                    Thread.sleep(333);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class GameStartReadyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);
            countDown_tv.setText(String.valueOf(msg.getData().getInt("i")));
            level.setText(lev+"단계");
            if(heart <= -1){
                HeartRecovery();
                heart = 3;
            }
            if(msg.getData().getInt("i") <= 0){
                countDown_tv.setVisibility(View.INVISIBLE);
                gameStart();
            }
        }
    }
}
