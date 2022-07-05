package com.example.bottomtab;

//어뎁터 클래스에서 뷰홀더와 어뎁터를 구현한다.
//어뎁터는 필요에 따라 뷰 홀더객체를 만들고, 이러한 뷰에 데이터를 설정하기도 한다.
//뷰를 요청 한 후에, 어댑터에서 메서드를 호출하여, 뷰를 뷰의 데이터에바인딩한다.

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.CustomViewHolder> {
    private ArrayList<ImageData> arrayList;

    public ImageAdapter(ArrayList<ImageData> arrayList) {
        this.arrayList = arrayList;
    }


    public void setImageList(ArrayList<ImageData> arrayList){
        this.arrayList = arrayList ;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.CustomViewHolder holder, int position) {
        Bitmap b = getBitmapFromString(arrayList.get(position).getImg());
        holder.img.setImageBitmap(b);
        holder.img_name.setText(arrayList.get(position).getImg_name());

        //longclick을 눌렀을때 라스트 뷰 삭제
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });


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

    public void remove(int position){
        try{
            arrayList.remove(position);
            //새로고침
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected ImageView img;
        protected TextView img_name;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = (ImageView)itemView.findViewById(R.id.img);
            this.img_name=(TextView) itemView.findViewById(R.id.img_name);
        }
    }
}
