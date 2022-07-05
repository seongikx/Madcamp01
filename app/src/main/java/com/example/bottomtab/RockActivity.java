package com.example.bottomtab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RockActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rock_main);


        TextView textView = findViewById(R.id.text01);
        textView.setText("시작버튼을 누르세요!");
        TextView resut_textView = findViewById(R.id.resultText);

        MyButtonLis myButtonLis = new MyButtonLis();
        myButtonLis.chageText = resut_textView;


        //시작버튼
        Button button = findViewById(R.id.button01);
        button.setText("가위바위보 게임 시작");
        myButtonLis.start = button;

        //가위버튼
        ImageButton button1 = findViewById(R.id.button2);
        myButtonLis.btn01 = button1;
        button1.setOnClickListener(myButtonLis);


        //바위버튼
        ImageButton button2 = findViewById(R.id.button3);
        myButtonLis.btn02 = button2;
        button2.setOnClickListener(myButtonLis);
        //보 버튼
        ImageButton button3 = findViewById(R.id.button4);
        myButtonLis.btn03 = button3;
        button3.setOnClickListener(myButtonLis);


        Atimer atimer = new Atimer(60000,200);
        atimer.textView = textView;

        myButtonLis.atimer = atimer;

        button.setOnClickListener(myButtonLis);


    }


}

class MyButtonLis implements View.OnClickListener
{
    Atimer atimer;
    TextView chageText;


    Button start;
    ImageButton btn01;
    ImageButton btn02;
    ImageButton btn03;

    ImageView title2;
    ImageView title3;
    ImageView title4;

    @Override
    public void onClick(View view) {


        if (view.equals(start))
        {
            atimer.start();
            chageText.setText("시작");

        }else if(view.equals(btn01))
        {

            //chageText.setText("가위");

            atimer.cancel();
            if(atimer.i==1)
            {
                chageText.setText("LOST");
            }else if(atimer.i==2)
            {
                chageText.setText("WIN");
            }else
            {
                chageText.setText("SAME");
            }

        }else if (view.equals(btn02))
        {
            chageText.setText("바위");


            atimer.cancel();
            if(atimer.i==2)
            {
                chageText.setText("LOST");
            }else if(atimer.i==0)
            {
                chageText.setText("WIN");
            }else
            {
                chageText.setText("SAME");
            }



        }else if (view.equals(btn03))
        {
            chageText.setText("보");
            atimer.cancel();

            if(atimer.i==0)
            {
                chageText.setText("LOST");
            }else if(atimer.i==1)
            {
                chageText.setText("WIN");
            }else
            {
                chageText.setText("SAME");
            }
        }
    }

}

class Atimer extends CountDownTimer
{
    TextView textView;
    int i=0;




    ArrayList<String> list =new  ArrayList<String>();


    public Atimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        list.add("가위"); //0
        list.add("바위");  //1
        list.add("보");    //2
    }


    @Override
    public void onTick(long l) {


        i++;
        if(i>=list.size()) i=0;
        //textView.setText(list.get(i));
        textView.setText(list.get(i));


    }



    @Override
    public void onFinish() {

        textView.setText("END");
    }
}