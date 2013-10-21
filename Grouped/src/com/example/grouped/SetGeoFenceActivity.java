package com.example.grouped;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
 

public class SetGeoFenceActivity extends Activity implements OnInfoWindowClickListener {
 

   private LatLng defaultLatLng = new LatLng(39.233956, -77.484703);
   private GoogleMap map;
   private int zoomLevel = 7;
  

  @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
  @SuppressLint("NewApi") 
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_geo_fence);
 

    try {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        if (map!=null){
           map.getUiSettings().setCompassEnabled(true);
           map.setTrafficEnabled(true);
           map.setMyLocationEnabled(true);
 

           // Move the camera instantly to defaultLatLng.
           map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, zoomLevel));
 

           map.addMarker(new MarkerOptions().position(defaultLatLng)
                .title("This is the title")
                .snippet("This is the snippet within the InfoWindow")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.checkmark)));
 

           map.setOnInfoWindowClickListener(this);
 

         }
 

     }catch (NullPointerException e) {
         e.printStackTrace();
     }
 

  }
 

   @Override
   public void onPause() {
                if (map != null){
                                map.setMyLocationEnabled(false);
                                map.setTrafficEnabled(false);
                }
                super.onPause();
   }

 

   @Override
   public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(this, SetGeoFenceActivity.class);
                intent.putExtra("snippet", marker.getSnippet());
                intent.putExtra("title", marker.getTitle());
                intent.putExtra("position", marker.getPosition());
                startActivity(intent);
   }
 

}