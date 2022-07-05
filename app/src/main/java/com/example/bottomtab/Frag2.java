package com.example.bottomtab;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//fragment는 onCreateView로 생성한다.
public class Frag2 extends Fragment {
    private View rootView;

    private ArrayList<ImageData> arrayList;
    private ArrayList<ImageData> arrayList2;
    HashMap<String, ArrayList<ImageData>> diary = new HashMap<String, ArrayList<ImageData>>();
    private ImageAdapter imageAdapter;
    private RecyclerView recyclerView;

    private TextView img_name;
    private ImageView select_img;
    private Button select_button;
    private EditText select_img_name;
    private ArrayList<ImageData> imgarraylist;

    TextView dateText;
    TextView gotoTodo;
    DatePickerDialog datePickerDialog;
    private Map<String, ArrayList<ImageData>> map;
    private Map<String, ArrayList<ImageData>> returnmap;
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
    Gson gson = new Gson();
    private String json;
    MainActivity mainactivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //xml파일과 연결 시도
        rootView = inflater.inflate(R.layout.frag2, container, false);
        //     checkSelfPermission();
        //리싸이클러뷰 갖고오기
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv2);
        img_name = (TextView)rootView.findViewById(R.id.img_name);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        gotoTodo =(TextView) rootView.findViewById(R.id.gotoTodo);
        select_img = (ImageView) rootView.findViewById(R.id.select_img);
        select_button = (Button) rootView.findViewById(R.id.select_button);
        select_img_name = (EditText) rootView.findViewById(R.id.select_img_name);
        mainactivity=(MainActivity)getActivity();



        //sharedData
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);     // test 이름의 기본모드 설정
        SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언

        //데이터넣을 array생성
        arrayList = new ArrayList<>();

        //데이터 넣을 어뎁터 생성
        imageAdapter = new ImageAdapter(arrayList);
        recyclerView.setAdapter(imageAdapter);

        dateText = (TextView) rootView.findViewById(R.id.date_text_view);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        dateText.setText(date);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });


        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("SAVED CLICK", "클릭");
                      Log.d("SAVED CLICK", ""+((BitmapDrawable) select_img.getDrawable()).getBitmap());
               Bitmap bitmap = ((BitmapDrawable) select_img.getDrawable()).getBitmap();
               String img = getStringFromBitmap(bitmap);
               if(select_img_name.getText().toString()==""){
                   arrayList.add(new ImageData(img, ""));
               }
               else{arrayList.add(new ImageData(img,  select_img_name.getText().toString()));}
                imageAdapter.notifyDataSetChanged();
            }
        });

        gotoTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainactivity.change_to_todo();

            }
        });

        //리사이클러뷰에 어뎁터 넣기
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap bm = BitmapFactory.decodeStream(is);
                is.close();
                //   Log.d("bitstream", bm.toString());
                select_img.setImageBitmap(bm);
//                String title = select_img_name.getText().toString();
//                Bitmap bitmap = ((BitmapDrawable) select_img.getDrawable()).getBitmap();
//                String img = getStringFromBitmap(bitmap);
//                arrayList.add(new ImageData(img, title));
//                imageAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 101 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getContext(), "취소", Toast.LENGTH_SHORT).show();
        }

    }
    public String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
    public Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }





}