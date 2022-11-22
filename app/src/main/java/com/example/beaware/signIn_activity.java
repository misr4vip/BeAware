package com.example.beaware;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.beaware.Constans.Utility;
import com.example.beaware.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import exportkit.xd.R;

    public class signIn_activity extends Activity {

	EditText username , password ;
	TextView signin,forgetPassword ,createAccount ;
	FirebaseAuth auth ;
	String userType;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		Intent i = getIntent();
		userType = i.getStringExtra("userType");
		createAccount = findViewById(R.id.signInCreateAccountTv);
		if (userType.equalsIgnoreCase("admin"))
		{
			createAccount.setVisibility(View.GONE);
		}
		username = findViewById(R.id.signinUserNameEt);
		password = findViewById(R.id.signinPasswordEt);
		signin = findViewById(R.id.SignInLoginTv);

		forgetPassword = findViewById(R.id.sigInForgetPasswordTv);
		auth = FirebaseAuth.getInstance();
		signin.setOnClickListener(view -> {
			if (Utility.isNotEmpty(username) && Utility.isNotEmpty(password))
			{
				auth.signInWithEmailAndPassword(Utility.EditTextToStirng(username),Utility.EditTextToStirng(password)).addOnSuccessListener(task->{

					SharedPreferences sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("userId", task.getUser().getUid());
					editor.commit();
					FirebaseDatabase.getInstance().getReference().child("users").child(task.getUser().getUid()).get().addOnSuccessListener(dataSnapshot -> {

						userModel currentUser = dataSnapshot.getValue(userModel.class);
						Intent intent;
						if (userType.equalsIgnoreCase("security"))
						{
							if (currentUser.getActive())
							{
								intent = new Intent(getApplicationContext(),notification_activity.class);
								startActivity(intent);
							}else
							{
								if (currentUser.getCvUploaded())
								{
									intent = new Intent(getApplicationContext(),message_activity.class);
									intent.putExtra("msg",currentUser.getAcceptedStatus());
									startActivity(intent);
								}else
								{
									intent = new Intent(getApplicationContext(),UploadCv_activity.class);
									startActivity(intent);
								}
							}

						}else if (userType.equalsIgnoreCase("admin"))
						{
							intent = new Intent(getApplicationContext(),admin_home_activity.class);
							startActivity(intent);
						}

					});


				}).addOnFailureListener(error ->{
					Toast.makeText(this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
				});
			}else
			{
				Toast.makeText(this,"All Field's are Required",Toast.LENGTH_LONG).show();
			}


		});
		createAccount.setOnClickListener(view -> {
			Intent intentCreate = new Intent(this , signUp_activity.class);
			intentCreate.putExtra("userType",userType);
			startActivity(intentCreate);
		});
		forgetPassword.setOnClickListener(view -> {
			Intent intent = new Intent(this, confirmation_email_activity.class);
			startActivity(intent);
		});

	}



}
	
	