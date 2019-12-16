package com.example.landandproperty4d.screen.yournews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class YourNewsActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarYourNew;
    private String interest;
    private TextView emptyYourNew ;
    private CatLoadingView progressBarCat;
    private RecyclerView recyclerYourNew;
    private ArrayList<PostProperty> listpost = new ArrayList<>();
    private ArrayList<String> listString = new ArrayList<>();
    private YourNewAdapter yourNewAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_news);
        init();
        progressBarCat.show(getSupportFragmentManager(),"");
        TakeDataPosts();
        toolbarYourNew.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void init(){
        progressBarCat = new CatLoadingView();
        emptyYourNew = findViewById(R.id.emptyYourNew);
        emptyYourNew.setVisibility(View.INVISIBLE);
        toolbarYourNew = findViewById(R.id.toolBarYourNew);
        recyclerYourNew = findViewById(R.id.recyclerYourView);
        setSupportActionBar(toolbarYourNew);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerYourNew.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this ,RecyclerView.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(this , manager.getOrientation());
        recyclerYourNew.addItemDecoration(decoration);
        recyclerYourNew.setLayoutManager(manager);
    }
    public void TakeDataPosts(){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild2 = dataSnapshot.getChildren();
                for (final DataSnapshot chilD: nodeChild2) {
                    final User u = chilD.getValue(User.class);
                    mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                            for (DataSnapshot snapshot: nodeChild1) {
                                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                    final PostProperty post = postSnapshot.getValue(PostProperty.class);
                                    post.setId(postSnapshot.getKey());
                                    if(chilD.getKey().equals(user.getUid())) {
                                        if (post.getCheck().contains(user.getUid())) {
                                            //
                                        } else {
                                            if (removeAccent(post.getAddress().toLowerCase()).contains(removeAccent(u.getPlacesInterest().toLowerCase()))) {
                                                listpost.add(post);
                                                listString.add(LoadImage(post.getImagePost()));
                                            }
                                        }
                                    }
                                }
                            }
                            if(chilD.getKey().equals(user.getUid())){
                                if(listpost.size()==0){
                                    emptyYourNew.setVisibility(View.VISIBLE);
                                }
                            }
                            yourNewAdapter = new YourNewAdapter(listpost,listString, getApplicationContext());
                            recyclerYourNew.setAdapter(yourNewAdapter);
                            Collections.reverse(listpost);
                            Collections.reverse(listString);
                            progressBarCat.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public String LoadImage( String a) {
        String b , c;
        b = a.replace("+","");
        c = b.replaceFirst(" ","");
        String[] arr = c.split(" ");
        return  arr[0];
    }
//    public void checkempty (){
//        yourNewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                check();
//            }
//
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                check();
//            }
//
//            @Override
//            public void onItemRangeRemoved(int positionStart, int itemCount) {
//                super.onItemRangeRemoved(positionStart, itemCount);
//                check();
//            }
//            public void check(){
//                emptyYourNew.setVisibility(yourNewAdapter.getItemCount()==0? View.VISIBLE: View.INVISIBLE);
//            }
//        });
//    }
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }

}
