package com.example.landandproperty4d.screen.postproperty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.screen.home.HomeActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostPropertyActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPostProperty;
    private TextView textViewAddImage , textViewDacDiem , textViewLocation , textViewPrice,textViewDetailInformation;
    private Spinner spinnerTypeOfProperty , spinnerHouseDirection ;
    private Toolbar toolbarPostProperty ;
    private EditText editTextTitle , editTextLandArea , editTextLadndPlaces , editTextLandAddress,
                     editTextPrice ,editTextContact , editTextDetail;
    private FragmentManager fragmentManager ;
    private static final int REQUEST_IMAGE_CAPTURE =1;
    private static final int PICK_IMAGE =2;
    private View ActivityRootView ;
    ArrayList<RecyclerViewData> listImage = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);
        init();

        textViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(textViewAddImage);
            }
        });

        toolbarPostProperty.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        editTextLandAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentAddress fragmentAddress = new FragmentAddress();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.constrainLayoutPostProperty,fragmentAddress);
                fragmentTransaction.commit();

            }
        });

    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
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
        ArrayAdapter<String> adapterProperty = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listTypeProperty);
        adapterProperty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfProperty.setAdapter(adapterProperty);

        spinnerHouseDirection =findViewById(R.id.spinnerHouseDirection);
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
        ArrayAdapter<String> adapterDirection = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listDirection);
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
        textViewPrice = findViewById(R.id.txtPrice);
        textViewDetailInformation = findViewById(R.id.txtDetailInformation);
        textViewPrice.setPaintFlags(textViewPrice.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textViewDetailInformation.setPaintFlags(textViewDetailInformation.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textViewLocation.setPaintFlags(textViewLocation.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        textViewDacDiem.setPaintFlags(textViewDacDiem.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        toolbarPostProperty = findViewById(R.id.toolBarPostProperty);
        setSupportActionBar(toolbarPostProperty);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewAddImage = findViewById(R.id.textViewAddImage);
        registerForContextMenu(textViewAddImage);
        recyclerViewPostProperty = findViewById(R.id.recyclerViewPostProperty);
        recyclerViewPostProperty.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerViewPostProperty.addItemDecoration(dividerItemDecoration);
        recyclerViewPostProperty.setLayoutManager(layoutManager);

        ImageAdapter imageAdapter = new ImageAdapter(listImage, getApplicationContext());
        recyclerViewPostProperty.setAdapter(imageAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contextmenu_image,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menuCameraImage:
                dispatchTakePictureIntent();
                ;break;
            case R.id.menuLibraryImage:
                TakePictureGalleryIntent();
                ;break;
        }
        return super.onContextItemSelected(item);
    }
//    private void TakePictureCameraIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode ==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
//            Bitmap smallImage = (Bitmap) extras.get("data");
//            listImage.add(new RecyclerViewData(smallImage));
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            try {
                Uri imageUri = data.getData();
                Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                listImage.add(new RecyclerViewData(photo));
            } catch (IOException e) {

            }

            return;
        } else if(requestCode ==REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
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

    private void TakePictureGalleryIntent (){
//        Intent i = new Intent();
//        i.setType("image/*");
//        //i.setType("video/*");
//        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(
//                Intent.createChooser(i, "android.intent.action.SEND_MULTIPLE"), 1);
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent,getString(R.string.supervisor_profile_choose_image_title));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }
    String mCurrentPhotoPath;
    //Biến này để sau này bạn có thể tiện sử dụng ảnh.
    private File createImageFile() throws IOException {
        // Tạo tên file dựa vào nhãn thời gian.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Lưu lại giá trị đường dẫn của ảnh vào biến mCurrentPhotoPath
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!= null);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(photoFile != null){
            Uri photoUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
        }
    }
}
