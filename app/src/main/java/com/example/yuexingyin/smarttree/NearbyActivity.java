package com.example.yuexingyin.smarttree;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback {

    private  GoogleMap map;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if(checkPermission())
            map.setMyLocationEnabled(true);

    }

    //check use permission
    public boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else
            return true;

    }

    //Call this method while user pushing the "Search" button
    public void searchDestination(View view) throws IOException {
        EditText search = (EditText) findViewById(R.id.search_text);
        String location = search.getText().toString();
        List<Address> addressList=null;
        if(location != null || !location.equals("")){

            //After input finished, hide the keyboard
            hideKeyboard(view);

            //Create the Geocoder client, which is used to getting the longitude and latitude of the location
            Geocoder geocoder = new Geocoder(this);
            addressList = geocoder.getFromLocationName(location, 1);

            //get the Name of the address
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());

            //draw a marker on the destination
            drawMarker(latLng);

            //Move this camera to the current marker
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }else{

            hideKeyboard(view);
            Toast.makeText(this, "No this place", Toast.LENGTH_LONG);
        }

    }

    //Call this method to draw a marker
    public void drawMarker(LatLng latLng){

        //Need to remove the marker after user have already draw a marker
        if(marker != null){
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        marker = map.addMarker(markerOptions);

        //define the info window of the marker
        InfoWindow info = new InfoWindow(getLayoutInflater(),this);
        //info.getInfoContents(marker);
        map.setInfoWindowAdapter(info);

    }

    //Call this method to hide keyboard
    private void hideKeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
    }


}
