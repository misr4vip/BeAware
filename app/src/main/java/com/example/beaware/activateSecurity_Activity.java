package com.example.beaware;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beaware.Constans.DownloadFileFromURL;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import exportkit.xd.R;

    public class activateSecurity_Activity extends Activity {

TextView name , mobile , email;
Button activate , downloadCv;
String userId ;
String Url;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activate_security);
		Intent intent = getIntent();
		userId = intent.getStringExtra("userId");
		String Email = intent.getStringExtra("email");
		String phone = intent.getStringExtra("phone");
		String Name = intent.getStringExtra("Name");
		name = findViewById(R.id.tvActiveName);
		mobile = findViewById(R.id.tvActivePhone);
		email = findViewById(R.id.tvActiveEmail);
		name.setText(Name);
		mobile.setText(phone);
		email.setText(Email);
		activate = findViewById(R.id.btnActivate);
		downloadCv = findViewById(R.id.btnDownloadCv);
		downloadCv.setOnClickListener(view -> {

			FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("cvUrl").get()
					.addOnSuccessListener(dataSnapshot -> {
						Url = dataSnapshot.getValue(String.class);
						new DownloadFileFromURL(this,Name).execute(Url);
					}).addOnFailureListener(error->{
				Toast.makeText(this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			});
		});
		activate.setOnClickListener(view -> {
			FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("active").setValue(true)
					.addOnSuccessListener(task->{
						Toast.makeText(this,"security Activated successfully",Toast.LENGTH_LONG).show();
					}).addOnFailureListener(error->{
				Toast.makeText(this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			});
		});

	}

}
	
	