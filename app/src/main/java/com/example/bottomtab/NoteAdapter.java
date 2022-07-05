package com.example.bottomtab;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

//어뎁터 클래스에서 뷰홀더와 어뎁터를 구현한다.
//어뎁터는 필요에 따라 뷰 홀더객체를 만들고, 이러한 뷰에 데이터를 설정하기도 한다.
//뷰를 요청 한 후에, 어댑터에서 메서드를 호출하여, 뷰를 뷰의 데이터에바인딩한다.
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.CustomViewHolder> {

    private ArrayList<Note> arrayList ;
    private NoteDatabase db2;
    //생성자 선언
    public NoteAdapter(ArrayList<Note> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    //리스트 뷰가 처음으로 생성될 때
    public NoteAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 원하는 레이아웃을 띄워주기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        db2 = Room.databaseBuilder(view.getContext(), NoteDatabase.class, "note-db").allowMainThreadQueries().build();
        return holder;
    }

    //바인딩 : 뷰를 데이터에 연결하는 프로세스
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.CustomViewHolder holder, int position) {
        //각각의 객체 속성을 꺼내서
        int id = holder.getItem(position).getId();
        holder.tv_name.setText(arrayList.get(position).getContent());

        if (db2.noteDao().getcomplete(id)==true){
            Log.d("--complete -",""+db2.noteDao().getcomplete(id));
            holder.tv_name.setPaintFlags(holder.tv_name.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.checkbox.setChecked(true);
        }
        else {
            holder.tv_name.setPaintFlags(0);
            Log.d("--not complete -",""+db2.noteDao().getcomplete(id));
            holder.checkbox.setChecked(false);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("-- 삭제 -","id"+holder.getItem(position).getId());
                remove(holder.getAdapterPosition());
                Log.d("-- 삭제 -","id"+holder.getItem(position).getId());
                Toast.makeText(view.getContext(), " 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        holder.deleteTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db2.noteDao().delete(holder.getItem(position).getId());
                remove(holder.getAdapterPosition());
                holder.getAdapterPosition();
              db2.noteDao().delete(holder.getAdapterPosition());
            }
        });
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkbox.isChecked()==true){
                    holder.tv_name.setPaintFlags(holder.tv_name.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else {
                    holder.tv_name.setPaintFlags(0);
                }
                 boolean current = (db2.noteDao().getcomplete(holder.getItem(position).getId()));
                db2.noteDao().setcomplete(!current, id);
            }
        });
    }

    private Note getItem(int position) {
            return arrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    //삭제 메서드
    public void remove(int position){
        try{
            arrayList.remove(position);
            //새로고침
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }


    //뷰홀더를 확장하여 뷰홀더를 재정의 함
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_name;
        protected Button deleteTodo;
        protected CheckBox checkbox;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            this.deleteTodo = (Button)itemView.findViewById(R.id.deleteTodo);
            this.checkbox = (CheckBox)itemView.findViewById(R.id.checkbox);
        }


        public Note getItem(int position) {
            return arrayList.get(position);
        }
    }
}









