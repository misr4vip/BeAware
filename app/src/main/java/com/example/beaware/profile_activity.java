package com.example.beaware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.beaware.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import exportkit.xd.R;

    public class profile_activity extends Activity {

    	EditText name  , mobile ;
    	TextView email;
    	Button btnProfileSave , btnSignOut;
    	String userId;
		userModel user;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		SharedPreferences sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
		userId = sharedPref.getString("userId","none");
		name = findViewById(R.id.etProfileName);
		email = findViewById(R.id.tvProfileEmail);
		mobile = findViewById(R.id.etProfilePhone);
		btnSignOut = findViewById(R.id.btnSignOut);
		btnProfileSave = findViewById(R.id.btnProfileSave);
		if (userId.length() > 0 && !userId.equalsIgnoreCase("none"))
		{
			FirebaseDatabase.getInstance().getReference().child("users").child(userId).get().addOnSuccessListener(dataSnapshot -> {
				 user = dataSnapshot.getValue(userModel.class);
				name.setText(user.getName());
				mobile.setText(user.getPhone());
				email.setText(user.getEmail());
			}).addOnFailureListener(error->{

				Toast.makeText(this , error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			});
		}

		btnSignOut.setOnClickListener(view -> {

			FirebaseAuth.getInstance().signOut();
			Intent intent = new Intent(this , intro_activity.class);
			startActivity(intent);
		});
		btnProfileSave.setOnClickListener(view -> {
			user.setPhone(mobile.getText().toString().trim());
			user.setName(name.getText().toString().trim());
			FirebaseDatabase.getInstance().getReference().child("users").child(userId).setValue(user).addOnSuccessListener(task->{
				Toast.makeText(this ,"Profile Updated Successfully",Toast.LENGTH_LONG).show();
			}).addOnFailureListener(error ->{
				Toast.makeText(this , error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
			});
		});
	
	}
}
	
	