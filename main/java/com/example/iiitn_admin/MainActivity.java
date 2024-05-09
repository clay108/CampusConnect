package com.example.iiitn_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.example.iiitn_admin.notice.DeleteNoticeActivity;
import com.example.iiitn_admin.notice.Upload_Notice;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView uploadNotice,addGalleryImage,addEbook,deleteNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadNotice=findViewById(R.id.addNotice);
        uploadNotice.setOnClickListener(this);
        addGalleryImage=findViewById(R.id.addGalleryImage);
        addGalleryImage.setOnClickListener(this);
        addEbook=findViewById(R.id.addEbook);
        addEbook.setOnClickListener(this);
        deleteNotice=findViewById(R.id.deleteNotice);
        deleteNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.addNotice:
                intent=new Intent(MainActivity.this, Upload_Notice.class);
                startActivity(intent);
                break;
            case R.id.addGalleryImage:
                intent=new Intent(MainActivity.this,UploadImage.class);
                startActivity(intent);
                break;
            case R.id.addEbook:
                intent=new Intent(MainActivity.this, UploadEbook.class);
                startActivity(intent);
                break;
            case R.id.deleteNotice:
                intent=new Intent(MainActivity.this, DeleteNoticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}