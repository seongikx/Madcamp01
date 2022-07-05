package com.example.bottomtab;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

//fragment는 onCreateView로 생성한다.
public class Frag1 extends Fragment {
    private View rootView;

    private ArrayList<Contact> arrayList2;

    private ContactAdapter contactAdapter;


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    EditText addname;
    EditText addnumbers;
    Button create_btn;
    TextView phonenum;
    TextView name;
    MainActivity mainactivity;
    SearchView searchView;

    private ArrayList<Contact> getContactList() {

        //uri만 있다면 , 어떤 데이터든지 갖고올 수 있다.
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID, // 연락처 ID -> 사진 정보 가져오는데 사용
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        }; // 연락처 이름.

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = getActivity().getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);

        ArrayList<Contact> contactlist = new ArrayList<Contact>();

        if (contactCursor.moveToFirst()) {
            do {
                String phonenumber = contactCursor.getString(1).replaceAll("-",
                        "");
                if (phonenumber.length() == 10) {
                    phonenumber = phonenumber.substring(0, 3) + "-"
                            + phonenumber.substring(3, 6) + "-"
                            + phonenumber.substring(6);
                } else if (phonenumber.length() > 8) {
                    phonenumber = phonenumber.substring(0, 3) + "-"
                            + phonenumber.substring(3, 7) + "-"
                            + phonenumber.substring(7);
                }


                Contact acontact = new Contact();
                acontact.setPhotoid(contactCursor.getLong(0));
                acontact.setPhonenum(phonenumber);
                acontact.setName(contactCursor.getString(2));

                contactlist.add(acontact);
            } while (contactCursor.moveToNext());
        }
        Log.d("contactlist",String.valueOf(contactlist.size()));
        return contactlist;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //xml파일과 연결 시도
        rootView = inflater.inflate(R.layout.frag1,container,false);

        addname = (EditText)rootView.findViewById(R.id.addname);
        addnumbers = (EditText)rootView.findViewById(R.id.addnumbers);
        create_btn = (Button)rootView.findViewById(R.id.create_btn);

        phonenum = (TextView)rootView.findViewById(R.id.phonenum);
        name =(TextView)rootView.findViewById(R.id.name);
        searchView=(SearchView)rootView.findViewById(R.id.searchView);
        //리싸이클러뷰 갖고오기
        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mainactivity=(MainActivity)getActivity();

        arrayList2= new ArrayList<>();

        //리사이클러뷰에 어뎁터 넣기
        arrayList2 = getContactList();
        Log.d(null, "--------------------------------" + arrayList2.size());
        // contactAdapter = new ContactAdapter();
        contactAdapter = new ContactAdapter(arrayList2);
        recyclerView.setAdapter(contactAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addname.getText().toString();
                String number = addnumbers.getText().toString();
                Contact contactData = new Contact(Long.valueOf(R.drawable.ic_launcher_background),name,number);
                arrayList2.add(contactData);
                contactAdapter.notifyDataSetChanged();
                addname.setText("");
                addnumbers.setText("");
            }
        });


        return rootView;
    }
    //권한에 대한 응답이 있을때 작동하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //권한을 허용 했을 경우
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int length = permissions.length;
        if (requestCode == 1) {

            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity", "권한 허용 : " + permissions[i]);
                }
            }
        }
        else {
            for (int i = 0; i < length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity", "권한 허용하지않음 : " + permissions[i]);
                    checkSelfPermission();
                }
            }
        }


    }

    public void checkSelfPermission() {

        String temp = "";

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_CONTACTS + " ";
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            temp += Manifest.permission.CALL_PHONE + " ";
        }


        if (TextUtils.isEmpty(temp) == false) {
            // 권한이 허용된게 없으면, 권한요청을 한다.
            //그리고 request코드 1을 반환한다.
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "),1);

        }else {
            // 모두 허용 상태면, 모두 허용됐다고 띄움
            Toast.makeText(getContext(), "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }




}