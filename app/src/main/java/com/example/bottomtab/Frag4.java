package com.example.bottomtab;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Frag4 extends Fragment {
    private View rootView;
    TextView dateText;
    TextView gotoGallery;
    DatePickerDialog datePickerDialog;
    MainActivity mainactivity;
    EditText todo;


    LinearLayoutManager layoutManager;
    RecyclerView todoRV;
    NoteAdapter adapter;
    private ArrayList<Note> arrayList;
    private NoteDatabase db2;

    List<Note> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //xml파일과 연결 시도
        rootView = inflater.inflate(R.layout.frag4,container,false);

        Log.d("시작?","시작?");
        mainactivity=(MainActivity)getActivity();
        gotoGallery = (TextView)rootView.findViewById(R.id.gotoGallery);

        dateText = (TextView) rootView.findViewById(R.id.select_date);
        todo = (EditText)rootView.findViewById(R.id.todo);
        Button saveButton = (Button)rootView.findViewById(R.id.saveButton);

        todoRV = (RecyclerView)rootView.findViewById(R.id.todoRV);
        layoutManager = new LinearLayoutManager(getContext());
        todoRV.setLayoutManager(layoutManager);

        arrayList  =new ArrayList<>();
        adapter = new NoteAdapter(arrayList);
        todoRV.setAdapter(adapter);


        db2 = Room.databaseBuilder(getContext(), NoteDatabase.class, "note-db")
                .allowMainThreadQueries()
                .build();

        if (db2.noteDao().select()!=null){
            list = db2.noteDao().select();
            Log.d("터짐","없는데");
            for(Note note:list){
                arrayList.add(note);
                Log.d("note->complete",""+note.isCompleted());
                adapter.notifyDataSetChanged();
            }
        }
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
                        String date = year + "년 " + month + "월 " + day +  "일 " ;
                        dateText.setText(date);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });
        gotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainactivity.change_to_gallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String Todo= todo.getText().toString();
                String today = dateText.getText().toString();
                Note note = new Note(today,Todo,false);
               arrayList.add(note);
               db2.noteDao().insert(note);
                adapter.notifyDataSetChanged();
                todo.setText("");
            }
        });



        return rootView;
    }

}
