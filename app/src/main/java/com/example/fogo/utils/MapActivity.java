//package com.example.fogo.utils;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private TextView txtPosition, txtOk;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map_activity);
//
//        txtPosition = findViewById(R.id.location_map);
//        txtOk = findViewById(R.id.confirm_map);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.view_map);
//        mapFragment.getMapAsync(this);
//
//        this.txtOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MapActivity.this, MainActivity.class));
//                MapActivity.this.finish();
//            }
//        });
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        LatLng hanoi = new LatLng(21.027763, 105.834160);
//        mMap.addMarker(new MarkerOptions()
//                .position(hanoi)
//                .title("Mark in Hanoi"));
//        txtPosition.setText("Tọa độ (Lat: " + 21.027763 + "  " + "Lng: " + 105.834160 + ") Ha Noi"); //cai nay set default position marker
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(hanoi));
//
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { //cai nay xu ly zoom vs get new position
//            @Override
//            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title("Unknown location");
//                txtPosition.setText("Tọa độ (Lat: " + String.format("%.6g%n", latLng.latitude) + "  " + "Lng: " + String.format("%.6g%n", latLng.longitude) + ") Unknown");
//                mMap.clear();
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                mMap.addMarker(markerOptions);
//            }
//        });
//    }
//}

// day co the thoi :v OK
// oke het roi nhe :v OK t lamf tis hoi tiep um