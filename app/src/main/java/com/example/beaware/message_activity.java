
package com.example.beaware;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.beaware.Models.NotifictionModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

import exportkit.xd.R;

    public class message_activity extends Activity {

    	TextView msg,tvExit;
    	ImageView iv;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		Intent i = getIntent();
		msg = findViewById(R.id.tvVisitorMsg);
		iv = findViewById(R.id.ivVisitorMessage);
		tvExit = findViewById(R.id.tvExit);

		Calendar cal = Calendar.getInstance();
		int day , month , year , hour , minute ;
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);

		String Msg = i.getStringExtra("msg");
		if (Msg.equalsIgnoreCase("AcceptToEntrance"))
		{
			iv.setImageResource(R.drawable.ic_true_foreground);
			msg.setText(R.string.you_can_enter);
			NotifictionModel model = new NotifictionModel(day , month , year , true , hour,minute);
			storeDataToDatabase(model);

		}else if (Msg.equalsIgnoreCase("RefusedToEntrance"))
		{
			iv.setImageResource(R.drawable.ic_false_foreground);
			msg.setText(R.string.sorry_you_can_not_enter);
			NotifictionModel model = new NotifictionModel(day , month , year , false , hour,minute);
			storeDataToDatabase(model);
		}else if(Msg.equalsIgnoreCase("pending"))
		{
			iv.setImageResource(R.drawable.ic_false_foreground);
			msg.setText(R.string.pending);
		}

		tvExit.setOnClickListener(view -> {
			Intent intent = new Intent(this,intro_activity.class);
			startActivity(intent);
		});

	}

	public void storeDataToDatabase(NotifictionModel model)
	{
		FirebaseDatabase.getInstance().getReference().child("Notification").child(String.valueOf(model.getYear())).child(String.valueOf(model.getMonth())).child(String.valueOf(model.getDay())).child(UUID.randomUUID().toString()).setValue(model).addOnSuccessListener(task->{
			Toast.makeText(this,"Successed to store data!",Toast.LENGTH_LONG).show();
		}).addOnFailureListener(error->{
			Toast.makeText(this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();

		});
	}
}
	
	