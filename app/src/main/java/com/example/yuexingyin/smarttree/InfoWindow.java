package com.example.yuexingyin.smarttree;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;

/**
 * Created by YuexingYin on 2/29/16.
 */
class InfoWindow implements GoogleMap.InfoWindowAdapter {

    LayoutInflater inflater=null;
    Context context;

    InfoWindow(LayoutInflater inflater, Context context) {
        this.inflater=inflater;
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view=inflater.inflate(R.layout.info_window, null);

        Geocoder geocoder = new Geocoder(context);

        LatLng latLng = marker.getPosition();

        List<Address> addressList= null;

        try {
            //Get the address's info from geocoder
             addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address address = addressList.get(0);
        //String countryName = address.getCountryName();
        String featureName = marker.getTitle();
        String localityName = address.getLocality();
        String addressLine = address.getAddressLine(0);
        String phoneNumber = address.getPhone();
        String postCode = address.getPostalCode();

        //find all the columns in info_window.xml
        TextView feature=(TextView)view.findViewById(R.id.feature_name);
        TextView locality=(TextView)view.findViewById(R.id.locality);
        TextView addressName=(TextView)view.findViewById(R.id.address);
        TextView post=(TextView)view.findViewById(R.id.post_code);
        TextView phone=(TextView)view.findViewById(R.id.phone);

        //pass all the parameters to xml
        feature.setText(featureName);
        locality.setText(localityName);
        addressName.setText(addressLine);
        phone.setText(phoneNumber);
        post.setText(postCode);


        return view;
    }
}
