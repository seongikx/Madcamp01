package com.example.bottomtab;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

public class ViewCircleMovingAnimation {
    View view;

    float fromXPosition;
    float fromYPosition;
    float toXPosition;
    float toYPosition;
    float duration;
    boolean reverse;
    boolean isPlaying = false;


    ViewCircleMovingThread viewCircleMovingThread;

    MovingAnimationFinishListenger listener;

    interface MovingAnimationFinishListenger {
        void onFinish(float fromXPosition, float fromYPosition, float toXPosition, float toYPosition);
    }

    public void setMovingAnimationFinishListener(MovingAnimationFinishListenger listener){
        this.listener = listener;
    }

    public void startViewCircleMovingAnimation(View view, float fromXPosition, float fromYPosition, float toXPosition, float toYPosition, float duration, boolean reverse) {
        this.view = view;
        this.fromXPosition = fromXPosition;
        this.fromYPosition = fromYPosition;
        this.toXPosition = toXPosition;
        this.toYPosition = toYPosition;
        this.duration = duration;
        this.reverse = reverse;
        isPlaying = true;
        viewCircleMovingThread = new ViewCircleMovingThread();
        viewCircleMovingThread.start();
    }

    class ViewCircleMovingThread extends Thread {
        float xPosition;
        float yPosition;
        float centerXPosition;
        float centerYPosition;
        float radius;
        float oneCycleDegree;
        float degree;
        boolean isFinish = false;

        ViewCircleMovingHandler handler = new ViewCircleMovingHandler();
        @Override
        public void run() {
            super.run();
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
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putFloat("xPosition", xPosition);
                bundle.putFloat("yPosition", yPosition);
                bundle.putBoolean("isFinish", isFinish);
                message.setData(bundle);
                handler.sendMessage(message);
                bundle = null;
                if (!isFinish) {
                    if (reverse) {
                        degree -= oneCycleDegree;
                    } else {
                        degree += oneCycleDegree;
                    }
                    xPosition = (float) (Math.cos(Math.toRadians(degree)) * radius + centerXPosition);
                    yPosition = (float) (Math.sin(Math.toRadians(degree)) * radius + centerYPosition);
                } else {
                    break;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ViewCircleMovingHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            float xPosition = msg.getData().getFloat("xPosition");
            float yPosition = msg.getData().getFloat("yPosition");
            boolean isFinish = msg.getData().getBoolean("isFinish");

            view.setX(xPosition);
            view.setY(yPosition);
            if (isFinish) {

                isPlaying = false;
                view.setX(fromXPosition);
                view.setY(fromYPosition);
                listener.onFinish(fromXPosition,fromYPosition,toXPosition,toYPosition);
            }


        }

    }

    public boolean isPlaying(){
        return isPlaying;
    }


}
