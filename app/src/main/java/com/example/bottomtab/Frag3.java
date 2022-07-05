package com.example.bottomtab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag3 extends Fragment {
    private View view;
    private ImageButton ultra_image;
    private ImageButton ultra_image2;
    private ImageButton ultra_image3;
    private ImageButton ultra_image4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3,container,false);

        ultra_image = (ImageButton) view.findViewById(R.id.ultra_image); //fragment에서 findViewByid는 view.을 이용해서 사용
        ultra_image2 = (ImageButton) view.findViewById(R.id.ultra_image2);
        ultra_image3 = (ImageButton) view.findViewById(R.id.ultra_image3); //fragment에서 findViewByid는 view.을 이용해서 사용
        ultra_image4 = (ImageButton) view.findViewById(R.id.ultra_image4);

        ultra_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),YabawiActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        ultra_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PicActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        ultra_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MoleActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        ultra_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RockActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        return view;
    }
}




