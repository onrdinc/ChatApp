package com.onurdinc.mesajlasmaa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    String userName,otherName;
    TextView chatUserName;
    ImageView backImage,sendImage;
    EditText chatEditText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView chatRecyView;
    MesajAdapter mesajAdapter;
    List<MesajModel> list;//mesaj model tipinde liste oluşturduk


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        loadMesaj();
    }
    public void tanimla(){
        list=new ArrayList<>();
        userName=getIntent().getExtras().getString("username");
        otherName=getIntent().getExtras().getString("othername");
        Log.i("alinan değerler :",userName+"--"+otherName );
        //kim kiminle iletişim halinde olacak belirlendi

        chatUserName=(TextView)findViewById(R.id.chatUserName);
        backImage=(ImageView)findViewById(R.id.backImage);
        sendImage=(ImageView)findViewById(R.id.sendImage);
        chatEditText=(EditText)findViewById(R.id.chatEditText);
        chatUserName.setText(otherName);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChatActivity.this,MainActivity.class);
                intent.putExtra("kadi",userName);//ismi gönderyoruz
                startActivity(intent);

            }
        });
        firebaseDatabase=FirebaseDatabase.getInstance();//Veritabanımızı referans alıyor
        reference=firebaseDatabase.getReference();//referans aldığımız database üzerinden read ve write yapacağız
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mesaj=chatEditText.getText().toString();
                chatEditText.setText("");
                MesajGönder(mesaj);

            }
        });
        chatRecyView=(RecyclerView)findViewById(R.id.chatRecyView);//tanımlamasını yapıyoruz recyview layout manager ile çalışır
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(ChatActivity.this,1);
        //1 satırda 1 mesaj görünsün istiyoruz
        chatRecyView.setLayoutManager(layoutManager);
        mesajAdapter=new MesajAdapter(ChatActivity.this,list,ChatActivity.this,userName);
        //from ile karşılaştırmak için username verdik
        chatRecyView.setAdapter(mesajAdapter);



    }
    public void MesajGönder(String text)
    {
        final String key=reference.child("Mesajlar").child(userName).child(otherName).push().getKey();
        //Gönderilen mesajdan sonra tekrar mesaj gönderildiğinde eski mesajın yerini yeni mesaj
        //almaması için her mesaja birer key veriyoruz
        final Map messageMap=new HashMap();//mesajın içeriğini oluşturuyoruz
        messageMap.put("text",text);//mesaj
        messageMap.put("from",userName);//mesaj kimden gelmiş
        reference.child("Mesajlar").child(userName).child(otherName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())//EĞER BAŞARILIYSA MESAJ HEM BİZDE HEM KARŞIDA TUTULUYOR
                {
                    reference.child("Mesajlar").child(otherName).child(userName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }

    public void loadMesaj(){
        reference.child("Mesajlar").child(userName).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MesajModel mesajModel=dataSnapshot.getValue(MesajModel.class);//dataları alıyoruz
                list.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();//Eğer güncelleme varsa adapter da güncelleme yapılır
                chatRecyView.scrollToPosition(list.size()-1);//yazıların üst tarafta kalması için
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


