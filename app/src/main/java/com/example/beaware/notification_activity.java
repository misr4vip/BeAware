
package com.example.beaware;

import android.app.Activity;
import android.os.Bundle;


import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import com.example.beaware.Adapters.NotifictionAdapter;
import com.example.beaware.Models.NotifictionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import exportkit.xd.R;

    public class notification_activity extends Activity {

    	ListView lvNotifiction;
    	TextView setting;
    	ArrayList<NotifictionModel> notifictionModels;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		lvNotifiction = findViewById(R.id.lvNotifiction);
		setting = findViewById(R.id.tvSetting);
		int day ,month , year ;
		Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		notifictionModels = new ArrayList<>();
		FirebaseDatabase.getInstance().getReference().child("Notification").child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(day))
				.get().addOnSuccessListener(dataSnapshot -> {

			for (DataSnapshot data:dataSnapshot.getChildren()) {

				notifictionModels.add(data.getValue(NotifictionModel.class));
			}
			lvNotifiction.setAdapter(new NotifictionAdapter(this,R.layout.notifiction_cell,notifictionModels));
		});

		setting.setOnClickListener(v->{

			Intent intent = new Intent(this ,settings_activity.class);
			startActivity(intent);
		});

	}
}
	
	