package com.example.bottomtab;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.CustomViewHolder> {

    private ArrayList<Contact> arrayList = new ArrayList<>();

    //생성자 선언
    public ContactAdapter(ArrayList<Contact> arrayList ) {
        this.arrayList = arrayList;

    }
    public ContactAdapter() {

    }
    @NonNull
    @Override
    //처음으로 생성될 때
    public ContactAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.CustomViewHolder holder, int position) {
        Log.d("holder", "------ holder = " + (holder.photoid));
        Log.d("holder", "------ holder = " + (arrayList.get(position).getName()));

        holder.photoid = (ImageView)holder.itemView.findViewById(R.id.photoid);
        holder.phonenum = (TextView)holder.itemView.findViewById(R.id.phonenum);
        holder.name = (TextView)holder.itemView.findViewById(R.id.name);


        holder.photoid.setImageResource(R.drawable.img2);
        holder.phonenum.setText(arrayList.get(position).getPhonenum());
        holder.name.setText(arrayList.get(position).getName());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String curName = holder.name.getText().toString();
                String curNum = holder.phonenum.getText().toString();
                //리스트뷰 클릭했을때 나타남
                Toast.makeText(view.getContext(),curName,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(),SubActivity.class);
                intent.putExtra("name",curNum);
                intent.putExtra("number",curName);
                Log.d("send, receive","name :" +curName);
                Log.d("send, receive","number : " +curNum);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView photoid;
        protected TextView phonenum;
        protected TextView name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            // Log.d("찾아야돼",itemView.findViewById(R.id.photoid).toString());
            this.photoid = (ImageView)itemView.findViewById(R.id.photoid);
            this.phonenum = (TextView)itemView.findViewById(R.id.phonenum);
            Log.d("찾아야돼",""+((ImageView)itemView.findViewById(R.id.photoid)==null));
            this.name = (TextView)itemView.findViewById(R.id.name);
        }
    }
}
