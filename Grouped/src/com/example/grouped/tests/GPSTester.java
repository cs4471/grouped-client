package com.example.grouped.tests;

import android.util.Log;

import com.example.grouped.network.LocationReader;

public class GPSTester {

	public static void runTests(LocationReader locationReader) {
		Log.d("GPSTester", "Provider: " + locationReader.getProvider());
		Log.d("GPSTester", "Lat: " + locationReader.determineLat());
		Log.d("GPSTester", "Long: " + locationReader.determineLong());
	}

}
