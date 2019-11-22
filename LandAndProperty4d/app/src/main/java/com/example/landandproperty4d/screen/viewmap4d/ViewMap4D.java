package com.example.landandproperty4d.screen.viewmap4d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import vn.map4d.map4dsdk.annotations.MFMarker;
import vn.map4d.map4dsdk.annotations.MFMarkerOptions;
import vn.map4d.map4dsdk.annotations.MFPolygon;
import vn.map4d.map4dsdk.annotations.MFPolygonOptions;
import vn.map4d.map4dsdk.camera.MFCameraUpdate;
import vn.map4d.map4dsdk.camera.MFCameraUpdateFactory;
import vn.map4d.map4dsdk.maps.LatLng;
import vn.map4d.map4dsdk.maps.MFSupportMapFragment;
import vn.map4d.map4dsdk.maps.Map4D;
import vn.map4d.map4dsdk.maps.OnMapReadyCallback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.screen.postdetail.PostDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class ViewMap4D extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Toolbar toolbarViewMap4D;
    private SearchView searchViewMap;
    private Button mode3d;
    private MFSupportMapFragment fragmentMap4d;
    private Map4D map4D;
    private boolean mode3D = true;
    private boolean polygonAdded = true;
    private final List<LatLng> pointsList = new ArrayList<>();
    private final List<LatLng> pointsList1 = new ArrayList<>();
    private final List<LatLng> pointsList2 = new ArrayList<>();
    private final List<LatLng> pointsList3 = new ArrayList<>();
    private final List<LatLng> holePath = new ArrayList<>();
    private MFPolygon polygon;
    private MFPolygon polygon1;
    private MFPolygon polygon2;
    private MFPolygon polygon3;
    private MFMarker marker;
    boolean clear = true;
    private String duongdan="https://api.map4d.vn/v2/api/place/text-search?key=8a6fe395ee75d80245e77d565c6a19f2&text=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map4_d);
        init();

        toolbarViewMap4D.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(ViewMap4D.this, HomeActivity.class);
                        User user = dataSnapshot.getValue(User.class);
                        intent.putExtra("ruler",user.getRuler());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        searchViewMap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(clear == false){
                    marker.remove();
                }
                String duongdanmoi = duongdan + removeAccent(searchViewMap.getQuery().toString());
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
                } else {Toast.makeText(getApplicationContext(),"Không tìm thấy địa chỉ này",Toast.LENGTH_LONG).show();}
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                searchViewMap.clearFocus();
                return true;
        }


            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void init() {
        mode3d = findViewById(R.id.btnEnable3D);
        mode3d.setOnClickListener(this);
        searchViewMap = findViewById(R.id.searchViewMap);
        toolbarViewMap4D = findViewById(R.id.toolBarViewMap4D);
        setSupportActionBar(toolbarViewMap4D);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentMap4d = (MFSupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3D);
        fragmentMap4d.getMapAsync(ViewMap4D.this);


    }


    private void creatPointsList() {
        pointsList.add(new LatLng(16.061187,108.223963));
        pointsList.add(new LatLng(16.061633,108.223292));
        pointsList.add(new LatLng(16.063773,108.223496));
        pointsList.add(new LatLng(16.063732,108.224084));
        pointsList.add(new LatLng(16.061646,108.224043));
        pointsList.add(new LatLng(16.061187,108.223963));

        pointsList1.add(new LatLng(16.067405,108.221000));
        pointsList1.add(new LatLng(16.067354,108.222539));
        pointsList1.add(new LatLng(16.066249,108.222483));
        pointsList1.add(new LatLng(16.066369,108.220892));
        pointsList1.add(new LatLng(16.067405,108.221000));

        pointsList2.add(new LatLng(16.072102,108.224942));
        pointsList2.add(new LatLng(16.072015,108.224286));
        pointsList2.add(new LatLng(16.072024,108.224268));
        pointsList2.add(new LatLng(16.072003,108.224076));
        pointsList2.add(new LatLng(16.072003,108.224076));
        pointsList2.add(new LatLng(16.073188,108.223869));
        pointsList2.add(new LatLng(16.073219,108.223815));
        pointsList2.add(new LatLng(16.075479,108.223584));
        pointsList2.add(new LatLng(16.075626,108.224245));
        pointsList2.add(new LatLng(16.072102,108.224942));

        pointsList3.add(new LatLng(16.072575,108.220220));
        pointsList3.add(new LatLng(16.072359,108.218451));
        pointsList3.add(new LatLng(16.071189,108.218664));
        pointsList3.add(new LatLng(16.071401,108.220378));
        pointsList3.add(new LatLng(16.072575,108.220220));

    }

    private void addPolygontoMap() {
        creatPointsList();
        polygon = map4D.addPolygon(new MFPolygonOptions()
                .add(pointsList.toArray(new LatLng[pointsList.size()]))
                .fillColor("#ff0000")
                .alpha(0.5f));

        polygon1 = map4D.addPolygon(new MFPolygonOptions()
                .add(pointsList1.toArray(new LatLng[pointsList1.size()]))
                .fillColor("#ff0000")
                .alpha(0.5f));

        polygon2 = map4D.addPolygon(new MFPolygonOptions()
                .add(pointsList2.toArray(new LatLng[pointsList2.size()]))
                .fillColor("#ff0000")
                .alpha(0.5f));

        polygon3 = map4D.addPolygon(new MFPolygonOptions()
                .add(pointsList3.toArray(new LatLng[pointsList3.size()]))
                .fillColor("#ff0000")
                .alpha(0.5f));

    }

    @Override
    public void onMapReady(Map4D map4D) {
        this.map4D = map4D;
        map4D.setMinZoomPreference(5.f);
        map4D.enable3DMode(true);
        addPolygontoMap();
        ;
        map4D.setOnMapClickListener(new Map4D.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ((TextView) findViewById(R.id.lat)).setText("Lat:    " + latLng.getLatitude());
                ((TextView) findViewById(R.id.lng)).setText("Lng:   " + latLng.getLongitude());
            }
        });

        map4D.setOnMapModeChange(new Map4D.OnMapModeChangeListener() {
            @Override
            public void onMapModeChange(boolean is3DMode) {
                Toast.makeText(getApplicationContext(), is3DMode ? "2D->3D" : "3D->2D", Toast.LENGTH_SHORT).show();
                if (is3DMode) {
                    ((Button) findViewById(R.id.btnEnable3D)).setText("mode2D");
                }
                else {
                    ((Button) findViewById(R.id.btnEnable3D)).setText("mode3D");
                }
                mode3D = is3DMode;
            }
        });
        map4D.setOnPolygonClickListener(new Map4D.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(MFPolygon polygon) {
                Intent intent = new Intent(ViewMap4D.this, PostDetail.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnable3D: {
                if (map4D != null) {
                    map4D.enable3DMode(!mode3D);
                }
                break;
            }
        }
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }
}
