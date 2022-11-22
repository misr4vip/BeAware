
package com.example.beaware;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import exportkit.xd.R;



public class scan_QR_activity extends Activity {

	private CodeScanner mCodeScanner;
	CodeScannerView scannerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qr);
		 scannerView = findViewById(R.id.scanner_view);

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
		{
			startScanning();
		}
	}

	private void startScanning() {

		mCodeScanner = new CodeScanner(this, scannerView);
		mCodeScanner.setDecodeCallback(result ->{
			runOnUiThread(() ->{
				Boolean canEntrance = true;
				if (result.getText().equalsIgnoreCase("3Doses"))
				{
					Toast.makeText(getApplicationContext(), result.getText() + " Great You Can move To Next Step", Toast.LENGTH_LONG).show();
					canEntrance = true;
					Intent intent = new Intent(this, scan_face_activity.class);
					startActivity(intent);
				}else if (result.getText().equalsIgnoreCase("2Doses") || result.getText().equalsIgnoreCase("1Doses"))
				{
					canEntrance = false;
					Toast.makeText(getApplicationContext(), result.getText() + " Sorry You must have 3 Doses to enter", Toast.LENGTH_LONG).show();
				}else if (result.getText().equalsIgnoreCase("ill"))
				{
					canEntrance = false;
					Toast.makeText(getApplicationContext(), result.getText() + " you made a big mistake to be here now ", Toast.LENGTH_LONG).show();
				}else
				{
					canEntrance = false;
					Toast.makeText(getApplicationContext(), " error in geting Data", Toast.LENGTH_LONG).show();
				}


				if (!canEntrance)
				{
					Intent intent = new Intent(this,message_activity.class);
					intent.putExtra("msg","RefusedToEntrance");
					startActivity(intent);
				}


					});
				}

		);
		scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
	}
	@Override
	protected void onResume() {
		super.onResume();
		mCodeScanner.startPreview();
	}

	@Override
	protected void onPause() {
		mCodeScanner.releaseResources();
		super.onPause();
	}


}
	
	