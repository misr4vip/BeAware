package com.example.beaware.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.beaware.Models.NotifictionModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import exportkit.xd.R;

public class NotifictionAdapter extends ArrayAdapter<NotifictionModel>{
    private int resourceLayout;
    private Context mContext;
    FirebaseDatabase database;
    DatabaseReference ref;
    public NotifictionAdapter(Context context, int resource, ArrayList<NotifictionModel> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }
    @Override
    public void remove(@Nullable NotifictionModel object) {
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
        NotifictionModel p = getItem(position);
        if (p != null) {
            TextView tvNotifictionTitle =  v.findViewById(R.id.tvNotifictionTitle);
            TextView tvTimeOfNotifiction = v.findViewById(R.id.tvTimeOfNotifiction);
            ImageView ivNotifiction =  v.findViewById(R.id.ivNotifiction);
            TextView tvTextOfNotifiction = v.findViewById(R.id.tvTextOfNotifiction);
            if (p.isAllowed())
            {
                tvNotifictionTitle.setText(R.string.allowed_to_enter_string);
                tvTimeOfNotifiction.setText(p.getHour() + ":" + p.getMinute());
                ivNotifiction.setImageResource(R.drawable.ic_true_foreground);
                tvTextOfNotifiction.setText(R.string.someone_adhered_to_the_full_procedures_string);
                tvNotifictionTitle.setTextColor(v.getResources().getColor(R.color.allowed_to_enter_color));

            }else
            {
                tvNotifictionTitle.setText(R.string.warning_string);
                tvTimeOfNotifiction.setText(p.getHour() + ":" + p.getMinute());
                ivNotifiction.setImageResource(R.drawable.ic_false_foreground);
                tvTextOfNotifiction.setText(R.string.someone_didn_t_committed_with_the_procedures_tried_to_enter_string);
                tvNotifictionTitle.setTextColor(v.getResources().getColor(R.color.warning_color));
            }



        }
        return v;
    }

}
