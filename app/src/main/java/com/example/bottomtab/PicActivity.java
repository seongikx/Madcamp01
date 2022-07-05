package com.example.bottomtab;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class PicActivity extends AppCompatActivity implements View.OnClickListener{

    //버튼 배열
    private ImageButton[] buttons = new ImageButton[8];

    //이미지 리스트
    private ArrayList<Integer> imageList;

    //카드 리스트
    private ArrayList<MemoryCard> cards;


    //결과 텍스트
    private TextView resultText;

    private TextView scoreboard;

    //초기화 버튼
    private ImageButton resetBtn;

    //이전 카드 위치
    int preCardPosition = -1;

    //목숨
    int heart = 3;

    //점수
    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_main);

        resultText = findViewById(R.id.result_text);
        scoreboard = findViewById(R.id.scoreboard);


        //초기화
        init();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                for(int j = 0; j < buttons.length; j++) {
                    buttons[j].setImageResource(R.drawable.question);
                }
                resultText.setText("같은 그림을 맞춰주세요 !");
                scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");
            }
        }, 1000);

        resultText.setText("그림을 외우세요 !");
        scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");




        resetBtn = findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //초기화
                init();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        for(int j = 0; j < buttons.length; j++) {
                            buttons[j].setImageResource(R.drawable.question);
                        }
                        resultText.setText("같은 그림을 맞춰주세요 !");
                        scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");
                    }
                }, 1000);

                resultText.setText("그림을 외우세요 !");
                scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");


            }

        });
    }//onClick

    //초기화
    public void init(){

        imageList = new ArrayList<>();

        //이미지 리스트에 데이터 등록
        imageList.add(R.drawable.blue);
        imageList.add(R.drawable.bluesnail);
        imageList.add(R.drawable.orange);
        imageList.add(R.drawable.red);
        imageList.add(R.drawable.blue);
        imageList.add(R.drawable.bluesnail);
        imageList.add(R.drawable.orange);
        imageList.add(R.drawable.red);

        //순서 섞기
        Collections.shuffle(imageList);

        //카드 초기화
        cards = new ArrayList<>();

        // 버튼 초기화
        for(int i = 0; i < buttons.length; i++){


            String buttonID = "imageBtn" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resourceID);

            //각 버튼에 클릭이벤트 적용
            buttons[i].setOnClickListener(this);

            //카드 리스트에 이미지 담기
            cards.add(new MemoryCard(imageList.get(i), false, false));

            //버튼 보이는거

            buttons[i].setImageResource(imageList.get(i));


            //선명도 설정
            buttons[i].setAlpha(1.0f);
        }


        //결과텍스트 초기화
        resultText.setText("");
    }

    //버튼 클릭이벤트
    @Override
    public void onClick(View view) {

        int id = view.getId();

        int position = 0;

        if(id == R.id.imageBtn0){

            position = 0;

        }else if(id == R.id.imageBtn1){
            position = 1;

        }else if(id == R.id.imageBtn2){
            position = 2;

        }else if(id == R.id.imageBtn3){
            position = 3;

        }else if(id == R.id.imageBtn4){
            position = 4;

        }else if(id == R.id.imageBtn5){
            position = 5;

        }else if(id == R.id.imageBtn6){
            position = 6;

        }else if(id == R.id.imageBtn7){
            position = 7;
        }

        //업데이트 모델
        updateModel(position);

        //업데이트 뷰
        updateView(position);
    }

    //데이터 변경
    private void updateModel(int position){

        MemoryCard card = cards.get(position);

        //나중에 카드 비교 할때 뒤집고 다시 클릭하는거 방지
        if(card.isFaceUp()){

            Toast.makeText(this, "이미 뒤집혔음", Toast.LENGTH_SHORT).show();
            return;
        }


        //뒤집힌 카드: 이전에 뒤집힌 카드 0 또는 2개일때
        if(preCardPosition == -1){
            //카드 초기화
            restoreCard();

            //위치 저장
            preCardPosition = position;

        }else{ //이전에 뒤집힌 카드: 1개일때
            //카드 비교
            checkForMatch(preCardPosition, position);
            preCardPosition = -1;
        }

        //반대의 값을 넣는다. 앞면 -> 뒷면 , 뒷면 -> 앞면
        cards.get(position).setFaceUp(!card.isFaceUp());
    }

    //뷰 변경
    private void updateView(int position){

        MemoryCard card = cards.get(position);

        //뒤집었음 랜덤 이미지로 보여준다.
        if(card.isFaceUp()){
            buttons[position].setImageResource(card.getImageId());

        }else{ //기본 이미지
            buttons[position].setImageResource(R.drawable.question);
        }
    }

    /**
     * 매치되지 않은 카드 초기화
     */
    private void restoreCard(){

        for(int i = 0; i < cards.size(); i++){

            //매치되지 않은거
            if(!cards.get(i).isMatched()){

                //이미지 앞으로
                buttons[i].setImageResource(R.drawable.question);

                //데이터 수정
                cards.get(i).setFaceUp(false);
            }
        }
    }

    /**
     * 카드 이미지 비교
     * @param prePosition 이전 카드 위치
     * @param position 현재 카드 위치
     */
    private void checkForMatch(int prePosition, int position){

        //처음 카드와 두번째 카드 이미지가 같다면
        if(cards.get(prePosition).getImageId() == cards.get(position).getImageId()){

            resultText.setText("매치 성공");
            scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");

            cards.get(prePosition).setMatched(true);
            cards.get(position).setMatched(true);

            //색상변경
            buttons[prePosition].setAlpha(0.3f);
            buttons[position].setAlpha(0.3f);

            //완료 체크
            checkCompletion();
        }else{
            resultText.setText("매치 실패");
            heart --;
            scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");
            if(heart == 0){
                Toast.makeText(this, "목숨끝! 총점수 :"+score, Toast.LENGTH_SHORT).show();

                init();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        for(int j = 0; j < buttons.length; j++) {
                            buttons[j].setImageResource(R.drawable.question);
                        }
                        resultText.setText("같은 그림을 맞춰주세요 !");
                        scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");
                    }
                }, 1000);

                resultText.setText("그림을 외우세요 !");
                scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");


                // 점수 초기화 + 다시시작
                heart = 3;
                score = 0;
            }
        }
    }

    /**
     * 완료 체크
     */
    private void checkCompletion(){

        int count = 0;

        for(int i =0; i < cards.size(); i++){

            if(cards.get(i).isMatched()){

                count++;
            }
        }

        //매치갯수가 카드갯수와 같다면 완료
        if(count == cards.size()){

            resultText.setText("클리어 !");
            score ++;
            scoreboard.setText("[현재 목숨 : "+heart+"]  [총 점수 : "+score+"]   ");
        }
    }

    private void start() throws InterruptedException {
        init();
        for(int i=3; i>0; i--){
            for(int j = 0; j < buttons.length; j++) {
                buttons[j].setImageResource(imageList.get(j));
            }
            sleep(1000);
        }
        for(int i = 0; i < buttons.length; i++)
            buttons[i].setImageResource(R.drawable.question);
    }


}//MainActivity
