package com.example.beaware;

import android.app.Activity;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beaware.Adapters.AdminHomeAdapter;
import com.example.beaware.Models.userModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import exportkit.xd.R;

    public class admin_home_activity extends Activity {

    	ListView lvSecurityUsers;
    	ArrayList<userModel> modles;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_home);
		lvSecurityUsers = findViewById(R.id.lvSecurityUsers);
		modles = new ArrayList<>();
		fillModles();
	
	}

		@Override
		protected void onResume() {
			super.onResume();
			if (modles.size() > 0)
			{
				modles.clear();
			}
			fillModles();

		}
		public void fillModles()
		{
			FirebaseDatabase.getInstance().getReference().child("users").get().addOnSuccessListener(dataSnapshot->{
				if (dataSnapshot.exists())
				{
					for (DataSnapshot shot:dataSnapshot.getChildren()) {
						userModel user = shot.getValue(userModel.class);
						if (user.getUserType().equalsIgnoreCase("security") )
						{
							modles.add(user);
						}
					}
					lvSecurityUsers.setAdapter(new AdminHomeAdapter(this,R.layout.admin_security_cell,modles));
				}
			}).addOnFailureListener(error ->{
				Toast.makeText(this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
			});
		}

	}

