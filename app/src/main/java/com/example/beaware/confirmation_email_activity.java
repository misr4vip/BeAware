
package com.example.beaware;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.beaware.Constans.Utility;
import com.google.firebase.auth.FirebaseAuth;

import exportkit.xd.R;


    public class confirmation_email_activity extends Activity {
		EditText etConfirmationEmail;
		TextView tvEmailConfirmationSend;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email_confirmation);
		etConfirmationEmail = findViewById(R.id.etConfirmationEmail);
		tvEmailConfirmationSend = findViewById(R.id.tvEmailConfirmationSend);
		tvEmailConfirmationSend.setOnClickListener(view -> {
			if (Utility.isNotEmpty(etConfirmationEmail))
			{
				FirebaseAuth.getInstance().sendPasswordResetEmail(etConfirmationEmail.getText().toString())
						//callback
						.addOnSuccessListener(task->{
							Toast.makeText(this , "email send successfully check your email ",Toast.LENGTH_LONG).show();
						}).addOnFailureListener(error ->{
					Toast.makeText(this , error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
				});
			}else
			{
				Toast.makeText(this , "you must enter valid email address",Toast.LENGTH_LONG).show();
			}
		});
	
	}
}
	
	