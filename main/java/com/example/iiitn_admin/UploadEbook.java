package com.example.iiitn_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class UploadEbook extends AppCompatActivity {
    private CardView addPdf;
    private EditText pdfTitle;
    private TextView pdfTextView;
    private Button uploadPdfBtn;
    private final int REQ=1;
    private DatabaseReference databasereference;
    private StorageReference storageReference;
    String downloadUrl="",title;
    private ProgressDialog pd;
    String pdfname;
    private Uri pdfData;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ebook);
        databasereference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);
        addPdf=findViewById(R.id.addPdf);
        pdfTitle=findViewById(R.id.pdfTitle);
        uploadPdfBtn=findViewById(R.id.uploadPdfBtn);
        pdfTextView=findViewById(R.id.pdfTextView);
        pd=new ProgressDialog(this);
        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=pdfTitle.getText().toString();
                if(title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }
                else if(pdfData==null)
                    Toast.makeText(UploadEbook.this, "Please Upload Pdf", Toast.LENGTH_SHORT).show();
                else
                    uploadPdf();
            }
        });
    }

    private void uploadPdf() {
        pd.setTitle("Please wait.....");
        pd.setMessage("Uploading pdf");
        pd.show();
        StorageReference reference=storageReference.child("pdf/"+pdfname+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String valueOf) {
        String uniqueKey=databasereference.child("pdf").push().getKey();
        HashMap data =new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);
        databasereference.child("pdf").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this, "Pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadEbook.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),REQ);

    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            pdfData=data.getData();
            if(pdfData.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor=UploadEbook.this.getContentResolver().query(pdfData,null,null,null,null);
                    if(cursor!=null&&cursor.moveToFirst()){
                        pdfname=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(pdfData.toString().startsWith("file://")){
                pdfname=new File(pdfData.toString()).getName();
            }
            pdfTextView.setText(pdfname);
        }
    }
}