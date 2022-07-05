package com.example.bottomtab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//어뎁터 클래스에서 뷰홀더와 어뎁터를 구현한다.
//어뎁터는 필요에 따라 뷰 홀더객체를 만들고, 이러한 뷰에 데이터를 설정하기도 한다.
//뷰를 요청 한 후에, 어댑터에서 메서드를 호출하여, 뷰를 뷰의 데이터에바인딩한다.
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList ;

    //생성자 선언
    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //리스트 뷰가 처음으로 생성될 때
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 원하는 레이아웃을 띄워주기
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;

        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // View view = inflater.inflate(R.layout.list_item, parent, false);
        // return new ItemViewHolder(view);

    }

    //바인딩 : 뷰를 데이터에 연결하는 프로세스
    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        //각각의 객체 속성을 꺼내서
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_content.setText(arrayList.get(position).getTv_content());

        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        //보통 onBind 함수 안에 모두 넣어줍니다.
        //holder.onBind(items.get(position));
        //우리코드로는 hodler.onBind(arrayList.get(position));

        //현재 클릭한 사람의 이름을 가져오기
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.tv_name.getText().toString();
                //리스트뷰 클릭했을때 나타남
                Toast.makeText(view.getContext(),curName,Toast.LENGTH_SHORT).show();
            }
        });

        //longclick을 눌렀을때 라스트 뷰 삭제
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

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

        protected ImageView iv_profile;
        protected TextView tv_name;
        protected TextView tv_content;
        // protected LinearLayout numberContainer;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = (ImageView)itemView.findViewById(R.id.iv_profile);
            this.tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            this.tv_content=(TextView)itemView.findViewById(R.id.tv_content);

        }
    }
}
