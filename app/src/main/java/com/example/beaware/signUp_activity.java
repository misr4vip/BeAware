
package com.example.beaware;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.beaware.Constans.Utility;
import com.example.beaware.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import exportkit.xd.R;

    public class signUp_activity extends Activity {

	EditText email , phone , password,name ;
	TextView register , login ;
	FirebaseAuth auth  ;
	FirebaseDatabase database ;
	String userType;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sing_up);
		Intent i  = getIntent();
		userType = i.getStringExtra("userType");
		auth = FirebaseAuth.getInstance();
		database = FirebaseDatabase.getInstance();
		email = findViewById(R.id.signUpEmailEt);
		phone = findViewById(R.id.signUpPhoneEt);
		password = findViewById(R.id.signUpPasswordEt);
		name = findViewById(R.id.signUpNameEt);
		register = findViewById(R.id.sing_upRegisterTV);
		login = findViewById(R.id.signupHaveAnAccount);

		login.setOnClickListener(view -> {
			Intent intent = new Intent(getApplicationContext(),signIn_activity.class);
			intent.putExtra("userType",userType);
			startActivity(intent);
		});

		register.setOnClickListener(view -> {

			if (Utility.isNotEmpty(email) && Utility.isNotEmpty(phone) && Utility.isNotEmpty(password) && Utility.isNotEmpty(name))
			{
				if (Utility.isValidPassword(password))
				{
					auth.createUserWithEmailAndPassword(Utility.EditTextToStirng(email),Utility.EditTextToStirng(password))
							.addOnSuccessListener(task -> {

								userModel user = new userModel(task.getUser().getUid(),
										Utility.EditTextToStirng(email),
										Utility.EditTextToStirng(phone),
										Utility.EditTextToStirng(password),
										userType,
										false,
										false,
										"pending",
										Utility.EditTextToStirng(name),
										"none"
								);
								SharedPreferences sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
								SharedPreferences.Editor editor = sharedPref.edit();
								editor.putString("userId", user.getUserId());
								editor.commit();
								database.getReference().child("users").child(user.getUserId()).setValue(user)
										.addOnSuccessListener(ResultTask ->{
											Intent intent = new Intent(getApplicationContext(),signIn_activity.class);
											startActivity(intent);
										}).addOnFailureListener(error->{
									Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
								});



							}).addOnFailureListener(error ->{
						Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
					});
				}else
				{
					Toast.makeText(getApplicationContext(),"password is too short",Toast.LENGTH_LONG).show();
				}

			}else
			{
				Toast.makeText(getApplicationContext(),"All Field's are Requireds",Toast.LENGTH_LONG).show();
			}

		});

	}
}
	
	