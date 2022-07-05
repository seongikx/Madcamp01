package com.example.bottomtab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//fragment는 onCreateView로 생성한다.
public class Frag5 extends Fragment {
    TextView username;
    TextView usernumber;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //xml파일과 연결 시도
        view = inflater.inflate(R.layout.frag5,container,false);
        return view;

    }
}
