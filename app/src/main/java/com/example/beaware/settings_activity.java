
package com.example.beaware;

import android.app.Activity;
import android.os.Bundle;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.ImageView;

import exportkit.xd.R;

    public class settings_activity extends Activity {

LinearLayout profile , password;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		profile = findViewById(R.id.llProfile);
		password = findViewById(R.id.llPassword);
		profile.setOnClickListener(view -> {
			Intent intent = new Intent(this , profile_activity.class);
			startActivity(intent);
		});
		password.setOnClickListener(view -> {

			Intent intent = new Intent(this , change_password_activity.class);
			startActivity(intent);

		});

	}
}
	
	