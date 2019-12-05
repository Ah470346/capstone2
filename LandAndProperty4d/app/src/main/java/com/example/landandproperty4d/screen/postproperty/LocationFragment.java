package com.example.landandproperty4d.screen.postproperty;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.screen.viewmap4d.DownloadDataJSON;
import com.example.landandproperty4d.screen.viewmap4d.ParserJSON;

import java.text.Normalizer;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import vn.map4d.map4dsdk.annotations.MFMarker;
import vn.map4d.map4dsdk.annotations.MFMarkerOptions;
import vn.map4d.map4dsdk.camera.MFCameraUpdateFactory;
import vn.map4d.map4dsdk.maps.LatLng;
import vn.map4d.map4dsdk.maps.MFMapView;
import vn.map4d.map4dsdk.maps.Map4D;
import vn.map4d.map4dsdk.maps.OnMapReadyCallback;


public class LocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback{
        private Map4D map4D ;
        private SearchView searchViewLocation ;
        private Boolean clear = true;
        private MFMapView map3DLocation ;
        private MFMarker marker;
        private Button buttonLocationDone;
        private EditText editTextLocation;
        private double a,b;
        private String duongdan="https://api.map4d.vn/v2/api/place/text-search?key=8a6fe395ee75d80245e77d565c6a19f2&text=";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custome_fragment_location,container,false);
        map3DLocation = view.findViewById(R.id.map3DLocation);
        map3DLocation.getMapAsync(this);
        buttonLocationDone = view.findViewById(R.id.buttonDoneLocation);
        searchViewLocation = view.findViewById(R.id.searchLocation);
        editTextLocation = getActivity().findViewById(R.id.editTextLandPlaces);
        buttonLocationDone.setOnClickListener(this);
        searchViewLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(clear == false){
                    marker.remove();
                    clear = true;
                }
                String duongdanmoi = duongdan + removeAccent(searchViewLocation.getQuery().toString());
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
                        Toast.makeText(getContext(),"Không tìm thấy địa chỉ này",Toast.LENGTH_LONG).show();}
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                searchViewLocation.clearFocus();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDoneLocation :
                if(clear == false){
                    editTextLocation.setText(a + "," + b);
                } else { Toast.makeText(getContext(),"Bạn chưa chọn địa điểm",Toast.LENGTH_LONG).show();}
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onMapReady(final Map4D map4D) {
        this.map4D = map4D;
        map4D.setMinZoomPreference(5.f);
        map4D.enable3DMode(false);
        map4D.setOnMapClickListener(new Map4D.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(clear == false){
                    marker.remove();
                    clear=true;
                }
                LatLng point = new LatLng(latLng.getLatitude(), latLng.getLongitude());
                marker = map4D.addMarker(new MFMarkerOptions().position(new LatLng(latLng.getLatitude(),latLng.getLongitude()))) ;
                a = latLng.getLatitude();
                b = latLng.getLongitude();
                clear = false;
                Log.d("hh",""+latLng.getLatitude() +" "+latLng.getLongitude());
            }
        });
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        map3DLocation.onDestroy();
    }
}
