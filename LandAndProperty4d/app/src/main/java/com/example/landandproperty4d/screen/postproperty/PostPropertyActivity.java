package com.example.landandproperty4d.screen.postproperty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.local.SharedPreferences;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.data.source.MapReponsitory;
import com.example.landandproperty4d.data.source.remote.MapRemoteDataSource;
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.register.RegisterViewModel;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
import com.example.landandproperty4d.utils.CommonUtils;
import com.example.landandproperty4d.utils.MyViewModelFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.roger.catloadinglibrary.CatLoadingView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostPropertyActivity extends AppCompatActivity implements OnClickListener {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    private RecyclerView recyclerViewPostProperty;
    private TextView textViewAddImage, textViewDacDiem, textViewLocation, textViewPrice, textViewDetailInformation;
    private Spinner spinnerTypeOfProperty, spinnerHouseDirection;
    private Toolbar toolbarPostProperty;
    private EditText editTextTitle, editTextLandArea, editTextLadndPlaces, editTextLandAddress, editTextPrice, editTextContact, editTextDetail;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 2;
    private View ActivityRootView;
    private String id = "",lng = "" , lat = "";
    ArrayList<RecyclerViewData> listImage = new ArrayList();

    private Button mButtonPost;
    private String mTypeProperty;
    private String mHomeDirection;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String imageUrl = "+";
    private int index = 0;
    private String polygonid = "";
    private CatLoadingView progressBarCat;
    private PostPropetyViewModel viewModel;
    private ArrayAdapter<String> adapterDirection;
    private ArrayAdapter<String> adapterProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);
        init();
        initData();
        chooseDirection();
        chooseType();
        spinnerTypeOfProperty.setSelection(listImage.indexOf("Bán Nhà Riêng"));

        textViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(textViewAddImage);
            }
        });

        toolbarPostProperty.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.buttonPost :
                progressBarCat.show(getSupportFragmentManager(), "");
                uploadPhoto(listImage);
                break;
            case R.id.editTextLandAddress :
                DialogAddress address = new DialogAddress();
                address.show(getSupportFragmentManager(),"dialog address");
                break;
            case R.id.editPrice :
                DialogPrice price = new DialogPrice();
                price.show(getSupportFragmentManager(),"dialog price");
                break;
            case R.id.editTextContact :
                DialogContact contact = new DialogContact();
                contact.show(getSupportFragmentManager(),"dialog contact");
                break;
            case R.id.editTextLandPlaces :
//                Intent intent = new Intent(PostPropertyActivity.this,LandLocation.class);
////                startActivity(intent);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                LocationFragment fragment = new LocationFragment();
                transaction.addToBackStack("hhh");
                transaction.replace(R.id.constrainLayoutPostProperty,fragment);
                transaction.commit();

        }
    }

    private void init() {
        progressBarCat = new CatLoadingView();
        spinnerTypeOfProperty = findViewById(R.id.spinnerTypeOfProperty);
        ArrayList<String> listTypeProperty = new ArrayList<>();
        listTypeProperty.add("Bất Kì");
        listTypeProperty.add("Bán Căn Hộ Chung Cư");
        listTypeProperty.add("Bán Nhà Riêng");
        listTypeProperty.add("Bán Biệt Thự");
        listTypeProperty.add("Bán Nhà Mặt Phố");
        listTypeProperty.add("Bán Đất Nền Dự Án");
        listTypeProperty.add("Bán Đất");
        listTypeProperty.add("Bán Trang Trại, Khu Nghỉ Dưỡng");
        listTypeProperty.add("Bán Kho, Nhà Xưởng");
        listTypeProperty.add("Bán Loại Bất Động Sản Khác");
        adapterProperty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, listTypeProperty);
        adapterProperty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfProperty.setAdapter(adapterProperty);

        spinnerHouseDirection = findViewById(R.id.spinnerHouseDirection);
        ArrayList<String> listDirection = new ArrayList<>();
        listDirection.add("Bất kì");
        listDirection.add("Chưa Xác Định");
        listDirection.add("Đông");
        listDirection.add("Tây");
        listDirection.add("Nam");
        listDirection.add("Bắc");
        listDirection.add("Đông Bắc");
        listDirection.add("Tây Bắc");
        listDirection.add("Đông Nam");
        listDirection.add("Tây Nam");
        adapterDirection = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, listDirection);
        adapterDirection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHouseDirection.setAdapter(adapterDirection);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextLandArea = findViewById(R.id.editTextLandArea);
        editTextLadndPlaces = findViewById(R.id.editTextLandPlaces);
        editTextLandAddress = findViewById(R.id.editTextLandAddress);
        editTextPrice = findViewById(R.id.editPrice);
        editTextContact = findViewById(R.id.editTextContact);
        editTextDetail = findViewById(R.id.editTextDetail);
        ActivityRootView = findViewById(R.id.constrainLayoutListImage);
        textViewDacDiem = findViewById(R.id.txtDacDiem);
        textViewLocation = findViewById(R.id.txtLocation);
        mButtonPost = findViewById(R.id.buttonPost);
        textViewPrice = findViewById(R.id.txtPrice);
        textViewDetailInformation = findViewById(R.id.txtDetailInformation);
        textViewPrice.setPaintFlags(textViewPrice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textViewDetailInformation.setPaintFlags(textViewDetailInformation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textViewLocation.setPaintFlags(textViewLocation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textViewDacDiem.setPaintFlags(textViewDacDiem.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        toolbarPostProperty = findViewById(R.id.toolBarPostProperty);
        setSupportActionBar(toolbarPostProperty);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewAddImage = findViewById(R.id.textViewAddImage);
        registerForContextMenu(textViewAddImage);
        recyclerViewPostProperty = findViewById(R.id.recyclerViewPostProperty);
        recyclerViewPostProperty.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerViewPostProperty.addItemDecoration(dividerItemDecoration);
        recyclerViewPostProperty.setLayoutManager(layoutManager);
        ImageAdapter imageAdapter = new ImageAdapter(listImage, getApplicationContext());
        recyclerViewPostProperty.setAdapter(imageAdapter);
        mButtonPost.setOnClickListener(this);
        editTextLandAddress.setOnClickListener(this);
        editTextPrice.setOnClickListener(this);
        editTextContact.setOnClickListener(this);
        editTextLadndPlaces.setOnClickListener(this);
    }

    private void initData(){
        MapReponsitory mapReponsitory = MapReponsitory.getInstance(MapRemoteDataSource.getsInstance());
        viewModel = ViewModelProviders.of(this,new MyViewModelFactory(mapReponsitory))
                .get(PostPropetyViewModel.class);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu_image, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menuCameraImage:
                dispatchTakePictureIntent();
                break;
            case R.id.menuLibraryImage:
                TakePictureGalleryIntent();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                listImage.add(new RecyclerViewData(photo));
            } catch (IOException e) {

            }

            return;
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File file = new File(mCurrentPhotoPath);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                listImage.add(new RecyclerViewData(bitmap));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void TakePictureGalleryIntent() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent
                .createChooser(getIntent, getString(R.string.supervisor_profile_choose_image_title));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    //Biến này để sau này bạn có thể tiện sử dụng ảnh.
    private File createImageFile() throws IOException {
        // Tạo tên file dựa vào nhãn thời gian.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        );

        // Lưu lại giá trị đường dẫn của ảnh vào biến mCurrentPhotoPath
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

        }
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }


    private void uploadPhoto(final ArrayList<RecyclerViewData> listImage) {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        final String timeStamp = CommonUtils.getSimpleDateFormat();
        final StorageReference mountainsRef = storageRef.child(CommonUtils.CHILD_STORAGE).child(timeStamp + CommonUtils.EXTENTION_PHOTO);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        listImage.get(index).getImage().compress(CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Toast.makeText(PostPropertyActivity.this, getResources().getString(R.string.message), Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
            @Override
            public void onSuccess(final TaskSnapshot taskSnapshot) {
                storageRef.child(CommonUtils.CHILD_STORAGE).child(timeStamp + CommonUtils.EXTENTION_PHOTO)
                        .getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                imageUrl = imageUrl+ " " + uri;
                                if (index < listImage.size() - 1) {
                                    index++;
                                    uploadPhoto(listImage);
                                }else {
                                    viewModel.saveDataPost(editTextLandAddress.getText().toString(), editTextLandArea.getText().toString(),
                                            editTextContact.getText().toString(), editTextDetail.getText().toString(), mHomeDirection, imageUrl, editTextLadndPlaces.getText().toString(),
                                            editTextPrice.getText().toString(), editTextTitle.getText().toString(), mTypeProperty,id,lng,lat,polygonid);
                                    progressBarCat.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        Toast.makeText(PostPropertyActivity.this, getResources().getString(R.string.message), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private void chooseType (){
        spinnerTypeOfProperty.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                                       final long id) {
                mTypeProperty = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
    }
    private void chooseDirection(){
        spinnerHouseDirection.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int position,
                                       final long id) {
                mHomeDirection = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });
    }
//    private void setupViewFragment(Class fragmentData) {
//        Class fragmentClass;
//        fragmentClass = fragmentData;
//        Fragment fragment = null;
//        try {
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        fragmentManager.beginTransaction().replace(R.id.constrainLayoutPostProperty, fragment, "new").commit();
//    }
}
