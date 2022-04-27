package com.onurdinc.mesajlasmaa;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {
    Context context;//listview ait layouta erişebilmek için aldık
    List<MesajModel> list;
    Activity activity;//chat activity bölümüne geçebilmek için aldık
    String userName;//2 Kişi sohbet edeceği için tanımladık
    Boolean state;//2 li durumlar için tanımladık
    int view_gonderen=1,view_alinan=2;
    public MesajAdapter(Context context, List<MesajModel> list, Activity activity, String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName=userName;
        state=false;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Layout tanımlaması yapacağız
        View view;
        if(viewType==view_gonderen){
                view=LayoutInflater.from(context).inflate(R.layout.gonderen,parent,false);
                return new ViewHolder(view);}
        else
        {
            view=LayoutInflater.from(context).inflate(R.layout.alinan,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).getText().toString());//atama yapıyoruz
        //listemizindeki elemanların textini yazdırdık
    }

    @Override
    public int getItemCount() {
        return list.size();//liste döndürülüyor
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            if(state==true)
            {
                textView=itemView.findViewById(R.id.gonderen_textView); //mesajı gonderen devreye girdi
            }else{
                textView=itemView.findViewById(R.id.alinan_textView);
            }
        }
    }

    @Override
    public int getItemViewType(int position) { //2 layoutu alıp mesaj atan ve alan şeklinde ayırmak için
        if(list.get(position).getFrom().equals(userName))
        {
            state=true;
            return view_gonderen;
        }
        else
        {
            state=false;
            return view_alinan;
        }
    }
}
