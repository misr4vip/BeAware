package com.example.beaware.Models;
import android.graphics.RectF;
public class Box {
   public RectF rectF;
    public String label;
    public Boolean isMask;
    public Box(RectF rectF ,String label , Boolean isMask)
    {
        this.isMask = isMask;
        this.label = label;
        this.rectF = rectF;

    }
}


