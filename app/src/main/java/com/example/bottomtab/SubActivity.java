package com.example.bottomtab;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SubActivity extends AppCompatActivity {
    TextView username;
    TextView usernumber;
    Button call;
    Button message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        username = findViewById(R.id.username);
        call  = findViewById(R.id.call);
        message = findViewById(R.id.message);
        usernumber = findViewById(R.id.usernumber);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        Log.d("receive, send","name :"+ name);
        Log.d("receive, send","number: " +number);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"권한 승인이 필요합니다.",Toast.LENGTH_SHORT).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                Toast.makeText(this,"전화권환이  필요합니다.",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"전화권환이  필요합니다.",Toast.LENGTH_SHORT).show();
               // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c= view.getContext();
                String tel = "tel:"+number;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(tel));
                try{
                    c.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, MessageSendActivity.class);
                intent.putExtra("str",number);
                startActivity(intent);

            }
        });

        //startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));

        usernumber.setText(number);
        username.setText(name);


    }
    }

