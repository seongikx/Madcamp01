# Madcamp1

- 텝 3개로 구성된 안드로이드 앱 입니다.
- 첫번째 탭에서는 전화번호부를 통해 전화를 걸 수 있고, 문자를 보낼 수 있습니다.
- 두번째 탭에서는 다이어리 기능을 구현했습니다. 
- 세번째 탭에서는 각종 게임을 즐길 수 있는 페이지를 구성했습니다.  <br/> 




## 어플리케이션 소개<br/> <br/> 
## TAB1 - 연락처
- 휴대폰에 저장돼 있는 연락처를 불러올 수 있고, 연락처 추가도 가능하다.
- 텝1의 연락처 페이지를 통해서 전화를 걸 수 있고, 문자를 보낼 수 있다.

![image](https://user-images.githubusercontent.com/77230391/177314056-5245b87b-b8eb-4b02-a9ed-244eab812e72.png)<br/> <br/> <br/> 



## TAB2 - 다이어리

- 휴대폰에 저장돼 있는 사진을들 선택하고, 원하는 날짜를 선택하여, 사진 일기를 작성할 수 있다.
- 사진 일기 뿐만 아니라 , 왼쪽 상단의 ToDo로 이동하는 페이지를 통하여 , ToDo를 작성하여 오늘 일과를 기록할 수 있다.
- ToDo는 앱 내부저장소에 저장돼므로, 사라지지 않는다.

![image](https://user-images.githubusercontent.com/77230391/177312445-49c01d1c-ca1e-4564-8687-b511f27baffc.png)  <br/> <br/> <br/> 


## TAB3 - 게임 
## Major Features
   같은 그림 찾기 게임을 할 수 있습니니다.
   야바위 게임을 할 수 있습니다.
   두더지 잡기 게임을 할 수 있습니다.
   가위,바위,보 게임을 할 수 있습니다.
   
#  설명
  
## 같은 그림 찾기 게임 
 이미지 리스트를 배열형식으로 받았습니다.
 각 자리에 인덱스 부여, 이미지 리스트 총 8 칸에 4쌍의 같은 이미지 데이터 등록하여 카드를 섞었습니다.
 뒤집힌 카드는 중복으로 클릭을 못하게 방지하였고 , 두 장의 선택한 카드가 매치되지 않을 시 카드를 다시 뒤집는 기능을 넣었습니다.
 기회 3번 , 클리어 횟수를 xml에 표현하였습니다.<br/> <br/>
    
## 야바위 게임
 컵과 공을 View.VISIBLE/INVISIBLE을 사용하여 게임을 표현하였습니다.
  duration으로 섞는 속도를 조절하였고 맞추면 속도 업, 틀리면 속도 다운 하여주어 level 단계를 왼쪽 상단에 표현하였습니다.
  두개의 컵을 섞는 애니메이션은 라디안 함수를 사용하여 다음과 같이 구현하였습니다. 
        
        
       
            xPosition = fromXPosition;
            yPosition = fromYPosition;
            centerXPosition = fromXPosition + (toXPosition - fromXPosition)/2;
            centerYPosition = fromYPosition + (toYPosition - fromYPosition)/2;
            radius = (float) Math.sqrt(Math.pow(centerXPosition - toXPosition, 2) + Math.pow(centerYPosition - toYPosition, 2));
            oneCycleDegree = 180 / (duration * 250);
            if (fromXPosition == toXPosition && fromYPosition > toYPosition) {
                degree = (float) Math.toDegrees(Math.acos((fromXPosition - centerXPosition) / radius));
            } else {
                degree = (float) Math.toDegrees(Math.acos((fromXPosition - centerXPosition) / radius)) * -1;
            }
            isFinish = false;
            while (true) {
                if (xPosition >= toXPosition - 5 / duration && xPosition <= toXPosition + 5 / duration
                        && yPosition >= toYPosition - 5 / duration && yPosition <= toYPosition + 5 / duration) {
                    xPosition = toXPosition;
                    yPosition = toYPosition;
                    isFinish = true;
                }
           
 목숨과 클리어 횟수로 점수를 표현하였습니다.
            

![image](https://user-images.githubusercontent.com/77230391/177317044-b0a3f8cf-e63e-4b6f-b6f5-2dd13bdbad75.png)  <br/> <br/> <br/> 
            
### 두더지게임
            Thread handler를 이용하였습니다.
            각 두더지마다 쓰레드를 주고 랜덤으로 튀어나오는 횟수와 시간을 부여했습니다.
            두더지게임을 위한 두더지사진 2장 (들어가있는 모습/튀어나온 모습) 을 구해 넣어놓고 배열에 아이디를 넣었습니다.
            게임이 끝나면 결과성적을 가지고 ResultActivity.java로 이동하게 하였습니다.
            기록을 저장하기 위해 SharedPreference 을 사용하였고, 신기록을 달성하는 경우 문구를 띄워주게 하였습니다.<br/> <br/>
            
            
            
### 가위바위보

 CountDownTimer 라이브러리를 이용하여 COM이 사용자가 가위/바위/보를 선택하는 순간 결정되어 논리를 비교를 구현하였습니다.<br/> <br/>

![image](https://user-images.githubusercontent.com/77230391/177317131-558f2606-b2a9-4552-a94d-916b5a5d02d6.png)

