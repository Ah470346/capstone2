package com.example.landandproperty4d.screen.postproperty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.screen.viewmap4d.DownloadDataJSON;
import com.example.landandproperty4d.screen.viewmap4d.ParserJSON;

import java.text.Normalizer;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.map4d.map4dsdk.annotations.MFMarker;
import vn.map4d.map4dsdk.annotations.MFMarkerOptions;
import vn.map4d.map4dsdk.camera.MFCameraUpdateFactory;
import vn.map4d.map4dsdk.maps.LatLng;
import vn.map4d.map4dsdk.maps.MFSupportMapFragment;
import vn.map4d.map4dsdk.maps.Map4D;
import vn.map4d.map4dsdk.maps.OnMapReadyCallback;

public class LandLocation extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private MFSupportMapFragment fragment ;
    private Map4D map4D ;
    private SearchView searchViewLandLocation ;
    private Boolean clear = true;
    private MFMarker marker ;
    private Button buttonLandLocationDone;
    private String duongdan="https://api.map4d.vn/v2/api/place/text-search?key=8a6fe395ee75d80245e77d565c6a19f2&text=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land_location);
        fragment = (MFSupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map3DLandLocation);
        fragment.getMapAsync(this);
        buttonLandLocationDone = findViewById(R.id.buttonDoneLandLocation);
        searchViewLandLocation = findViewById(R.id.searchLandLocation);
        buttonLandLocationDone.setOnClickListener(this);
        searchViewLandLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(clear == false){
                    marker.remove();
                    clear = true;
                }
                String duongdanmoi = duongdan + removeAccent(searchViewLandLocation.getQuery().toString());
                String duongdancuoi = duongdanmoi.replace(" ","%20");
                DownloadDataJSON dataJSON = new DownloadDataJSON();
                dataJSON.execute(duongdancuoi);
                try {
                    String a = dataJSON.get();
                    ParserJSON parserJSON = new ParserJSON(a);
                    Double lat = parserJSON.laytoadoLAT();
                    Double lng = parserJSON.laytoadoLNG();
                    if(lat != null && lng !=null) {
                        marker = map4D.addMarker(new MFMarkerOptions().position(new LatLng(lat, lng)));
                        LatLng point = new LatLng(parserJSON.laytoadoLAT(), parserJSON.laytoadoLNG());
                        map4D.animateCamera(MFCameraUpdateFactory.newLatLngZoom(point, 14));
                        clear = false;
                    } else {
                        Toast.makeText(LandLocation.this,"Không tìm thấy địa chỉ này",Toast.LENGTH_LONG).show();}
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                searchViewLandLocation.clearFocus();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onMapReady(final Map4D map4D) {
        map4D.setMinZoomPreference(5.f);
        map4D.enable3DMode(true);
        map4D.setOnMapClickListener(new Map4D.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(clear == false){
                    marker.remove();
                    clear=true;
                }
                double a = Math.ceil(latLng.getLatitude()*1000000)/1000000;
                double b = Math.ceil(latLng.getLongitude()*1000000)/1000000;
                LatLng point = new LatLng(latLng.getLatitude(), latLng.getLongitude());
                marker =map4D.addMarker(new MFMarkerOptions().position(new LatLng(a,b))) ;
                clear = false;
                Log.d("hh",""+a+" "+b);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDoneLandLocation :
                if(clear == false){
//                    Intent intent = new Intent(LandLocation.this, PostPropertyActivity.class);
//                    intent.putExtra("market",""+marker.getPosition());
                    finish();
                } else { Toast.makeText(LandLocation.this,"Bạn chưa chọn địa điểm",Toast.LENGTH_LONG).show();}
                break;
        }

    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }
}

