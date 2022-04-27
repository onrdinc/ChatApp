package com.onurdinc.mesajlasmaa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;//listview ait layouta erişebilmek için aldık
    List<String> list;
    Activity activity;//chat activity bölümüne geçebilmek için aldık
    String userName;//2 Kişi sohbet edeceği için tanımladık

    public UserAdapter(Context context, List<String> list, Activity activity,String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName=userName;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Layout tanımlaması yapacağız
        View view=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).toString());//atama yapıyoruz
        //listemizindeki elemanların textini yazdırdık
        holder.userAnaLayout.setOnClickListener(new View.OnClickListener() {//useranalyouta tıklarsak
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,ChatActivity.class);//chat aktivitiye gitti
                intent.putExtra("username",userName);//chat aktivitye giderken yanında bilgiler götürdü
                intent.putExtra("othername",list.get(position).toString());//mesahlaştığımız kişinin ismi
                activity.startActivity(intent);



            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();//liste döndürülüyor
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout userAnaLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.userName); //tanımlama işlemi gerçekleştirildi
            userAnaLayout=itemView.findViewById(R.id.userAnaLayout);//tanımlama işlemini yaptık
        }
    }
}
