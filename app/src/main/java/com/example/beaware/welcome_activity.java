
package com.example.beaware;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import exportkit.xd.R;

    public class welcome_activity extends Activity {

	TextView visitor,security,admin;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		visitor = findViewById(R.id.tvVistor);
		security = findViewById(R.id.tvSecurity);
		admin = findViewById(R.id.tvAdmin);
		visitor.setOnClickListener(view -> {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED)
			{
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},123);
			}else
			{
				NavigateToScanQrCode();
			}

		});

		security.setOnClickListener(view -> {
			Intent i = new Intent(this ,signIn_activity.class );
			i.putExtra("userType","security");
			startActivity(i);

		});
		admin.setOnClickListener(view -> {
			Intent i = new Intent(this ,signIn_activity.class );
			i.putExtra("userType","admin");
			startActivity(i);

		});
	}

		@Override
		public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			if (requestCode == 123)
			{
				if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
				{
					NavigateToScanQrCode();
				}else
				{
					Toast.makeText(this,"sorry you must enable camera to use this feature",Toast.LENGTH_LONG).show();
				}
			}
		}

		private void NavigateToScanQrCode() {
			Intent i = new Intent(this,scan_QR_activity.class);
			startActivity(i);
		}


	}
	
	