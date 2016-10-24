package com.catlivevideo.living.catlivevideo;

import android.app.Application;

import org.xutils.x;

public class Myapplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
		//x.Ext.setDebug(BuildConfig.DEBUG);
	}
}
