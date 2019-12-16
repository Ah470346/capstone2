package com.example.landandproperty4d.screen.viewmap4d;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.screen.home.BuyerActivity;
import com.example.landandproperty4d.screen.home.HomeActivity;
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
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import vn.map4d.map4dsdk.annotations.MFBitmapDescriptorFactory;
import vn.map4d.map4dsdk.annotations.MFMarker;
import vn.map4d.map4dsdk.annotations.MFMarkerOptions;
import vn.map4d.map4dsdk.annotations.MFPolygon;
import vn.map4d.map4dsdk.annotations.MFPolygonOptions;
import vn.map4d.map4dsdk.camera.MFCameraUpdateFactory;
import vn.map4d.map4dsdk.maps.LatLng;
import vn.map4d.map4dsdk.maps.MFMapView;
import vn.map4d.map4dsdk.maps.Map4D;
import vn.map4d.map4dsdk.maps.OnMapReadyCallback;


public class ViewMap4D extends AppCompatActivity implements OnMapReadyCallback , View.OnClickListener {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Toolbar toolbarViewMap4D;
    private SearchView searchViewMap;
    private Button mode3d;
    private MFMapView map3D ;
    private Map4D map4D;
    private boolean mode3D = true;
    private boolean polygonAdded = true;
    private final List<LatLng> holePath = new ArrayList<>();
    private MFMarker marker;
    boolean clear = true;
    private int a ;
    MFPolygon mfPolygon ;
    private String duongdan="https://api.map4d.vn/v2/api/place/text-search?key=8a6fe395ee75d80245e77d565c6a19f2&text=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map4_d);
        toolbarViewMap4D = findViewById(R.id.toolBarViewMap4D);
        setSupportActionBar(toolbarViewMap4D);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        toolbarViewMap4D.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user.getRuler().equals("1") || user.getRuler().equals("3")){
                            Intent intent = new Intent(ViewMap4D.this, HomeActivity.class);
                            intent.putExtra("ruler", user.getRuler());
                            startActivity(intent);
                        } else if (user.getRuler().equals("2")){
                            Intent intent = new Intent(ViewMap4D.this, BuyerActivity.class);
                            intent.putExtra("ruler", user.getRuler());
                            startActivity(intent);
                        }
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
        map3D = findViewById(R.id.map3D);
        map3D.getMapAsync(this);
    }

    private void addMakertoMap(){

    }
    private void addPolygontoMap() {
        mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot: nodeChild) {
                    for (DataSnapshot snapshot: postSnapshot.getChildren()) {
                        final PostProperty post = snapshot.getValue(PostProperty.class);
                        post.setId(snapshot.getKey());
                        marker = map4D.addMarker(new MFMarkerOptions()
                                .position(LoadMaker(post.getLocation()))
                                .icon(MFBitmapDescriptorFactory.fromResource(R.drawable.maker_64))
                                .title(post.getTitle())
                                .snippet(post.getPrice()));
                        a = 0;
                        LoadPoint(post.getLat(),post.getLng());
                        Log.d("nek",""+a);
                        if(a == 0) {
                            snapshot.getRef().child("polygonid").setValue("" + mfPolygon.getId());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onMapReady(Map4D map4D) {
        this.map4D = map4D;
        map4D.setMinZoomPreference(5.f);
        map4D.enable3DMode(true);
        addPolygontoMap();
        LinkProperty();

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
            public void onPolygonClick(final MFPolygon polygon) {
                mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                        for (DataSnapshot postSnapshot: nodeChild) {
                            for (DataSnapshot snapshot: postSnapshot.getChildren()) {
                                PostProperty post = snapshot.getValue(PostProperty.class);
                                post.setId(snapshot.getKey());
                                if(!post.getPolygonid().equals("")) {
                                    long a = Long.parseLong(post.getPolygonid());
                                    if (polygon.getId() == a) {
                                        final Intent intent = new Intent(ViewMap4D.this, PostDetail.class);
                                        intent.putExtra("screen", "view");
                                        intent.putExtra("key", snapshot.getKey());
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
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

    private void LoadPoint (String lat , String lng){
        if(lat.equals("") || lng.equals("")){
            a = 9;
        } else {
            List<LatLng> pointsListL = new ArrayList<>();
            String[] arrlat = lat.split(",");
            String[] arrlng = lng.split(",");
            for (int i = 0; i < arrlat.length; i++) {
                pointsListL.add(new LatLng(Double.parseDouble(arrlat[i]), Double.parseDouble(arrlng[i])));
            }
            mfPolygon = map4D.addPolygon(new MFPolygonOptions()
                    .add(pointsListL.toArray(new LatLng[pointsListL.size()]))
                    .fillColor("#ff0000")
                    .alpha(0.5f));
        }
    }
    private LatLng LoadMaker (String m){
        LatLng maker ;
        String[] array = m.split(",");
        maker = new LatLng(Double.parseDouble(array[0]),Double.parseDouble(array[1]));
        return maker;
    }
    private void LinkProperty (){
        mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot: nodeChild) {
                    for (DataSnapshot snapshot: postSnapshot.getChildren()) {
                        PostProperty post = snapshot.getValue(PostProperty.class);
                        post.setId(snapshot.getKey());
                        Intent intent = getIntent();
                        if(post.getPolygonid().equals(intent.getStringExtra("maker")) && intent.getExtras() !=null) {
                            map4D.animateCamera(MFCameraUpdateFactory.newLatLngZoom(LoadMaker(post.getLocation()), 18));
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map3D.onDestroy();
    }
}

