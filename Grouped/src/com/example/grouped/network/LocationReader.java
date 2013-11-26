package com.example.grouped.network;

import java.util.ArrayList;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.example.grouped.models.Member;

public class LocationReader {
	private static LocationReader singleton = null;

	private LocationManager locationManager;
	private Criteria criteria;
	private String provider;

	private LocationReader(LocationManager locationManager) {
		this.criteria = new Criteria();
		this.provider = locationManager.getBestProvider(criteria, false);
		this.locationManager = locationManager;
	}

	public float getDistance(double lat, double lng) {
		Location thisLocation = this.locationManager
				.getLastKnownLocation(provider);
		Location otherLocation = new Location("");
		otherLocation.setLatitude(lat);
		otherLocation.setLongitude(lng);
		return otherLocation.distanceTo(thisLocation);
	}

	public ArrayList<Member> outsideDistance(int maxDistance,
			ArrayList<Member> members) {
		ArrayList<Member> farMembers = new ArrayList<Member>();
		for (Member member : members) {
			float lat = Float.parseFloat(member.getLat());
			float lng = Float.parseFloat(member.getLng());
			if (this.getDistance(lat, lng) > maxDistance) {
				farMembers.add(member);
			}
		}
		return farMembers;
	}

	public double determineLat() {
		Location location = this.locationManager.getLastKnownLocation(provider);
		return location.getLatitude();
	}

	public double determineLong() {
		Location location = this.locationManager.getLastKnownLocation(provider);
		return location.getLongitude();
	}

	public String getProvider() {
		return this.provider;
	}

	public static LocationReader getLocationReader(
			LocationManager locationManager) {
		if (LocationReader.singleton == null) {
			LocationReader.singleton = new LocationReader(locationManager);
		}
		return LocationReader.singleton;
	}
}
