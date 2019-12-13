package com.example.landandproperty4d.screen.postnews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.autofit.et.lib.AutoFitEditText;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.source.MapReponsitory;
import com.example.landandproperty4d.data.source.remote.MapRemoteDataSource;
import com.example.landandproperty4d.screen.postproperty.PostPropetyViewModel;
import com.example.landandproperty4d.utils.CommonUtils;
import com.example.landandproperty4d.utils.MyViewModelFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.roger.catloadinglibrary.CatLoadingView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostNewActivity extends AppCompatActivity {
    private AutoFitEditText editTextContent , editTextNewTitle;
    private Button buttonAddImage,buttonPostNew;
    private ImageView imageViewNew;
    private Toolbar toolbarNew;
    private CatLoadingView progressBarCat;
    private NewModelView newModelView ;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String id = "";
    private String idAdmin = "";
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 2;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        init();
        initData();

        toolbarNew.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(buttonAddImage);
            }
        });
        buttonPostNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarCat.show(getSupportFragmentManager(),"");
                uploadPhoto();
            }
        });
    }

    public void init(){
        progressBarCat = new CatLoadingView();
        editTextContent = findViewById(R.id.editTextContent);
        editTextNewTitle = findViewById(R.id.editTextNewTitle);
        buttonAddImage = findViewById(R.id.buttonAddImage);
        imageViewNew = findViewById(R.id.imageViewNew);
        toolbarNew = findViewById(R.id.toolBarNew);
        buttonPostNew = findViewById(R.id.buttonPostNew);
        setSupportActionBar(toolbarNew);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerForContextMenu(buttonAddImage);
    }
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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageViewNew.setImageBitmap(photo);
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
                imageViewNew.setImageBitmap(bitmap);
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
    private void initData(){
        MapReponsitory mapReponsitory = MapReponsitory.getInstance(MapRemoteDataSource.getsInstance());
        newModelView = ViewModelProviders.of(this,new MyViewModelFactory(mapReponsitory))
                .get(NewModelView.class);
    }
    private void uploadPhoto() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        final String timeStamp = CommonUtils.getSimpleDateFormat();
        final StorageReference mountainsRef = storageRef.child(CommonUtils.CHILD_STORAGE).child(timeStamp + CommonUtils.EXTENTION_PHOTO);
        Bitmap bitmap = ((BitmapDrawable)imageViewNew.getDrawable()).getBitmap();
        imageViewNew.setDrawingCacheEnabled(true);
        imageViewNew.buildDrawingCache();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Toast.makeText(PostNewActivity.this, getResources().getString(R.string.message), Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.child(CommonUtils.CHILD_STORAGE).child(timeStamp + CommonUtils.EXTENTION_PHOTO)
                        .getDownloadUrl().addOnSuccessListener(
                        new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                String imageUrl ="" + uri;
                                if(user != null){
                                  newModelView.saveDataNew(editTextNewTitle.getText().toString(),editTextContent.getText().toString(),imageUrl,id,idAdmin,CommonUtils.getSimpleDateFormatPost());
                                }
                                progressBarCat.dismiss();
                                Toast.makeText(PostNewActivity.this,"Đăng Tin Thành Công",Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        Toast.makeText(PostNewActivity.this, getResources().getString(R.string.message), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
