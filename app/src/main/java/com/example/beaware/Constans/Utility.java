package com.example.beaware.Constans;

import android.widget.EditText;

public class Utility {

    public static String EditTextToStirng(EditText et)
    {
        return et.getText().toString().trim();
    }
    public static Boolean isNotEmpty(EditText et)
    {
        return et.getText().toString().trim().length() > 0 ;
    }
    public static Boolean isValidPassword(EditText et )
    {
        return et.getText().toString().trim().length() > 5 ;
    }
}
