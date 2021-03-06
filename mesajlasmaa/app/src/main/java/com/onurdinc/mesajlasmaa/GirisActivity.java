package com.onurdinc.mesajlasmaa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {
    EditText kullaniciAdiEditText,password;
    Button kayitolButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
    }
    public void tanimla()
    {

        kullaniciAdiEditText=(EditText)findViewById(R.id.kullaniciAdiEditText);
        kayitolButton=(Button)findViewById(R.id.kayitolButton);//Buton tanımlamalarını yaptık
        firebaseDatabase=FirebaseDatabase.getInstance();//Veritabanımızı referans alıyor
        reference=firebaseDatabase.getReference();//referans aldığımız database üzerinden read ve write yapacağız
        kayitolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=kullaniciAdiEditText.getText().toString();
                kullaniciAdiEditText.setText("");//edittext içindeki değeri sildi
                ekle(username);


            }
        });
    }
    public void ekle(final String kadi)//dışarıdan fonksiyonun içine gönderirken final yapmak zorundayız
    {
        reference.child("Kullanıcılar").child(kadi).child("kullaniciadi").setValue(kadi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Başarıyla giriş yaptınız",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(GirisActivity.this,MainActivity.class);
                    intent.putExtra("kadi",kadi);//kullanıcı adını gönderdik
                    startActivity(intent);}
                else{
                    Toast.makeText(getApplicationContext(),"başarısız",Toast.LENGTH_LONG).show();
                }
            }
        });



}}
