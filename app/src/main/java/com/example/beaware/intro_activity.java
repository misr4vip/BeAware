
package com.example.beaware;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import exportkit.xd.R;

    public class intro_activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);


		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(getApplicationContext(), welcome_activity.class);
				startActivity(i);
			}
		},3000);
		

	
	}
}
	
	