
package com.example.beaware;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.beaware.Constans.Utility;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import exportkit.xd.R;

    public class change_password_activity extends Activity {

	EditText oldPassword , newPassword ,confirmPassword;
	Button save;
	String userId;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		oldPassword = findViewById(R.id.etOldPassword);
		newPassword = findViewById(R.id.etNewPassword);
		confirmPassword = findViewById(R.id.etConfirmPassword);
		save = findViewById(R.id.btnPasswordSave);
		SharedPreferences sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
		userId = sharedPref.getString("userId","none");
		save.setOnClickListener(view -> {

			if (Utility.EditTextToStirng(oldPassword).length() > 0 &&
					Utility.EditTextToStirng(newPassword).length() > 0 &&
					Utility.EditTextToStirng(confirmPassword).length() > 0 )
			{
				if (Utility.EditTextToStirng(newPassword).equalsIgnoreCase(Utility.EditTextToStirng(confirmPassword)))
				{
					FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("password").get().addOnSuccessListener(getPasswordTask->{
						String password = getPasswordTask.getValue().toString();
						if (password.equalsIgnoreCase(oldPassword.getText().toString()))
						{
							FirebaseAuth.getInstance().getCurrentUser().updatePassword(Utility.EditTextToStirng(newPassword)).addOnSuccessListener(task->{
								FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("password").setValue(newPassword.getText().toString());
							});
						}
					}).addOnFailureListener(error ->{
						Toast.makeText(this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					});
				}else
				{
					Toast.makeText(this, "new password and confirmation aren't equal", Toast.LENGTH_SHORT).show();
				}



			}else
			{
				Toast.makeText(this, "all Field's are required", Toast.LENGTH_SHORT).show();
			}
		});

	
	}
}
	
	