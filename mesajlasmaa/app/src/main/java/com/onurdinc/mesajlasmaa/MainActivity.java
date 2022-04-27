package com.onurdinc.mesajlasmaa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String>list;
    String username;
    RecyclerView userRecyView;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanimla();
        listele();
    }
    public void tanimla()
    {
        username=getIntent().getExtras().getString("kadi");//kullanıcı adını almış olduk
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        list=new ArrayList<>();//Listemizi bellekte tanımladık
        userRecyView=(RecyclerView)findViewById(R.id.userRecyView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(MainActivity.this,2);
        //1 satırda 2 kullanıcı listelenmesini istedik
        userRecyView.setLayoutManager(layoutManager);
        userAdapter=new UserAdapter(MainActivity.this,list,MainActivity.this,username);
        userRecyView.setAdapter(userAdapter);


    }

    public void listele()
    {
        reference.child("Kullanıcılar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals(username)){//kullanıcının kendi ismini kullanıcılar sayfasında listelememek için
                    list.add(dataSnapshot.getKey());
                    Log.i("Kullanıcı",dataSnapshot.getKey());
                    userAdapter.notifyDataSetChanged();//Listemiz güncellendikçe adapter de güncellendi
                }


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
