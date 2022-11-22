package com.example.beaware.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.beaware.Models.NotifictionModel;
import com.example.beaware.Models.userModel;
import com.example.beaware.activateSecurity_Activity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import exportkit.xd.R;

public class AdminHomeAdapter extends ArrayAdapter<userModel>{
    private int resourceLayout;
    private Context mContext;

    public AdminHomeAdapter(Context context, int resource, ArrayList<userModel> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }
    @Override
    public void remove(@Nullable userModel object) {
        super.remove(object);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);

        }
        userModel p = getItem(position);
        if (p != null) {
            TextView tvActivated = v.findViewById(R.id.tvActivated);
            ImageView btnDelete = v.findViewById(R.id.btnDelete);
            TextView tvName = v.findViewById(R.id.tvName);
            TextView tvEmail = v.findViewById(R.id.tvEmail);
            tvName.setText(p.getName());
            tvEmail.setText(p.getEmail());
            if (p.getActive())
            {
                tvActivated.setText("Activated");
                tvActivated.setTextColor(Color.GREEN);
            }else
            {
                tvActivated.setText("DisActivated");
                tvActivated.setTextColor(Color.RED);
            }

            tvActivated.setOnClickListener(view -> {
               // Intent intent = new Intent(mContext,)
                if (!p.getActive() && !p.getCvUrl().equalsIgnoreCase("none") && p.getCvUploaded())
                {
                   Intent intent = new Intent(mContext, activateSecurity_Activity.class);
                   intent.putExtra("userId", p.getUserId());
                    intent.putExtra("email", p.getEmail());
                    intent.putExtra("phone", p.getPhone());
                    intent.putExtra("Name", p.getName());


                   mContext.startActivity(intent);
                }else
                {
                    Toast.makeText(mContext,"Sorry This security don't uplad cv yet",Toast.LENGTH_LONG).show();
                }
            });
            btnDelete.setOnClickListener(view -> {

                FirebaseDatabase.getInstance().getReference().child("users").child(p.getUserId()).removeValue().addOnSuccessListener(task->{
                    Toast.makeText(mContext, "security removed successfully", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(error->{
                    Toast.makeText(mContext, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
            });

        }
        return v;
    }

}
