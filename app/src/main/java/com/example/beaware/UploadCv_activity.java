
package com.example.beaware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

import exportkit.xd.R;

    public class UploadCv_activity extends Activity {

View attatchPdfBtn;
TextView sendPdfToFireBase,tvPdfName;
boolean fileAttatchedSuccessfully = false;
Uri fileUri;
String userId;
		Boolean isCompleted;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploadcv);
		tvPdfName = findViewById(R.id.tvPdfName);
		attatchPdfBtn = findViewById(R.id.attatchPdfBtn);
		sendPdfToFireBase = findViewById(R.id.sendPdfToFireBase);
		SharedPreferences sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
		userId = sharedPref.getString("userId","none");
		attatchPdfBtn.setOnClickListener(view -> {

			selectPdf();
		});
		sendPdfToFireBase.setOnClickListener(view -> {
			if (fileAttatchedSuccessfully)
			{
				sendFileToFirebase(fileUri);
				new Handler().postDelayed(() -> {
					Intent i = new Intent(getApplicationContext() , intro_activity.class);
					startActivity(i);
				},5000);


			}
		});
	
	}

		private void sendFileToFirebase(Uri file) {

			StorageReference storageReference = FirebaseStorage.getInstance().getReference("cvPDF");
			storageReference.child(userId).putFile(file).addOnSuccessListener(task-> task.getStorage().getDownloadUrl().addOnSuccessListener(task1 -> {

				   FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("cvUploaded").setValue(true);
				   FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("cvUrl").setValue(task1.toString());
				   Toast.makeText(this,"cv Uploaded Successfully",Toast.LENGTH_LONG).show();

		   }).addOnFailureListener(error->{
			   Toast.makeText(this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			   isCompleted = false;
		   }));

		}

		private void selectPdf() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("application/pdf");
		startActivityForResult(Intent.createChooser(intent,"select pdf file"),12);
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == 12 && resultCode ==RESULT_OK && data.getData() != null)
			{
				fileAttatchedSuccessfully = true;
				fileUri = data.getData();
				tvPdfName.setText("file name :" + data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

			}
		}
	}
	
	