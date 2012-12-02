package com.robodex;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

public class Robodex extends Application {
    public static final boolean DEBUG = true;

    public static Context sAppContext;

    @Override
    public void onCreate() {
    	super.onCreate();
    	sAppContext = this.getApplicationContext();
    }

//    public static String getString(Cursor c, String column) {
//    	String result = "";
//    	int index = c.getColumnIndex(column);
//    	if (index >= 0) result = c.getString(index);
//    	if (result.equals("null")) return "";
//    	return result;
//    }
}
