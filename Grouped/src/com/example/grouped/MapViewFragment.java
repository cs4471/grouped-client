package com.example.grouped;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grouped.models.Member;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment extends Fragment {
	   //39.9930, -82.9985
	   private ArrayList<Member> members = new ArrayList<Member>();
	   private LatLng defaultLatLng = new LatLng(39.233956, -77.484703);
	   private GoogleMap map;
	   private int zoomLevel = 15;
		  	
		public MapViewFragment() {
			//Build Array list member
			for (int i = 0; i < 10; i++) {
				Member member = new Member();
				if(i==0){
					member.setMe(true);
					member.setNickname("ME");
				} else {
					member.setNickname("Other"+i);
					member.setMe(false);
				}
				member.setStatus("Status"+i);
				member.setLat("39.993"+i);
				member.setLng("-82.998"+i);
				members.add(member);
			}
		}
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
		@SuppressLint("NewApi")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		View rootView = inflater.inflate(
					R.layout.fragment_map, container,
					false);
			try {
		        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		        if (map!=null){
		           map.getUiSettings().setCompassEnabled(true);
		           map.setTrafficEnabled(true);
		 

		           // Move the camera instantly to me
		           for (Member member : members) { 
		           LatLng latLng = new LatLng(Float.parseFloat(member.getLat()), Float.parseFloat(member.getLng()));
		           String title = member.getNickname();
		           String status = member.getStatus();
		        	   
		           if (member.isMe()){
		           map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
		        	   title = "Me";
		           }
		           map.addMarker(new MarkerOptions().position(latLng)
		                .title(title)
		                .snippet(status)
		                .icon(BitmapDescriptorFactory.defaultMarker()));
	           }
		         }
		     }catch (NullPointerException e) {
		         e.printStackTrace();
		     }
			return rootView;
		}
	}