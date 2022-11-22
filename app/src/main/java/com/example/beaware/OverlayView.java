package com.example.beaware;
import android.content.Intent;
import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.beaware.Models.Box;

import java.util.ArrayList;



public class OverlayView extends View{
    ArrayList<Box> boundingBox;
Context context;
    public OverlayView(Context context) {
        super(context);
        this.context = context;
        boundingBox = new ArrayList();
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        boundingBox = new ArrayList();
    }

    public OverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        boundingBox = new ArrayList();
    }

    Paint paint = new Paint();
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeMiter(100f);
        for (Box box: boundingBox) {
            if (box.isMask){
                paint.setColor(Color.GREEN);
            } else {
                paint.setColor(Color.RED);
            }
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(30f);
            canvas.drawText(box.label, box.rectF.left, box.rectF.top-9F, paint);
            canvas.drawRoundRect(box.rectF, 2F, 2F, paint);
            if (box.isMask)
            {

                navigateToNext("Great you Can Enter :)",true);

            }else
            {
                navigateToNext("Sorry You cann't enter :(",false);
            }
        }

    }

    public void navigateToNext(String msg , Boolean haveMask)
    {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(context,message_activity.class);
            if (haveMask)
            {
                intent.putExtra("msg","AcceptToEntrance");
            }else
            {
                intent.putExtra("msg","RefusedToEntrance");
            }

            context.startActivity(intent);
        },5000);
    }
    public void setBoundingBox(ArrayList<Box> boxes)
    {
        boundingBox = boxes;
    }
}





