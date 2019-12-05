package com.example.landandproperty4d.screen.postproperty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import static android.R.layout.simple_spinner_dropdown_item;
import static com.example.landandproperty4d.R.layout.land_address_dialog;

public class DialogAddress extends AppCompatDialogFragment {
    private ArrayAdapter<String> adapter ;
    private Spinner spinnerCity;
    private EditText  editTextDistric , editTextPhuong , editTextStreet , editTextHouseNumber , editextLandAddress;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(land_address_dialog, null);

        builder.setView(v)
                .setTitle("Nhập Địa Chỉ")
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String city = spinnerCity.getSelectedItem().toString();
                        String distric = editTextDistric.getText().toString();
                        String phuong = editTextPhuong.getText().toString();
                        String street = editTextStreet.getText().toString();
                        String housenumber = editTextHouseNumber.getText().toString();
                        if (housenumber.equals("") || distric.equals("") || phuong.equals("") || street.equals("")){
                            Toast.makeText(getActivity(), "Bạn Phải Nhập Đầy Đủ Địa Chỉ", Toast.LENGTH_LONG).show();
                        }else {
                            editextLandAddress.setText( housenumber +", "+street +", "+distric+", "+phuong+", " +city);
                        }
                    }
                });
        spinnerCity = v.findViewById(R.id.spinnerCity);
        ArrayList<String> listcity = new ArrayList<>();
        listcity.add("Đà Nẵng");
        listcity.add("Hà Nội");
        listcity.add("Hồ Chí Minh");
        listcity.add("Quảng Nam");
        listcity.add("Huế");
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listcity);
        adapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
        editTextDistric = v.findViewById(R.id.editTextDistric);
        editTextPhuong = v.findViewById(R.id.editTextPhuong);
        editTextStreet = v.findViewById(R.id.editTextStreet);
        editTextHouseNumber = v.findViewById(R.id.editTextHouseNumber);
        editextLandAddress= getActivity().findViewById(R.id.editTextLandAddress);
        return builder.create();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            listener = (DialogAddressListener) context;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ClassCastException(context.toString()+"must implement DialogAddressListener");
//        }
//
//
//    }
//
//    public interface DialogAddressListener{
//        void applyTexts(String city,String distric , String phuong , String street , String housenumber);
//    }
}
