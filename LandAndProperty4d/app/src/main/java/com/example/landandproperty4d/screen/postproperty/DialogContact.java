package com.example.landandproperty4d.screen.postproperty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.landandproperty4d.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogContact extends AppCompatDialogFragment {
    private EditText editTextNameContact , editTextPhoneContact , editTextEmailContact ,editTextContact;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.land_contact_dialog,null);

        builder.setView(v)
                .setTitle("Thông Tin Liên Hệ")
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextNameContact.getText().toString();
                        String phone = editTextPhoneContact.getText().toString();
                        String email = editTextEmailContact.getText().toString();
                        if(name.equals("") || phone.equals("") || email.equals("")){
                            Toast.makeText(getActivity(),"Bạn Phải Nhập Đầy Đủ Địa Chỉ",Toast.LENGTH_LONG).show();
                        } else {
                            editTextContact.setText("Tên Người Đăng: "+name +", "+"phone: " + phone + ", " +"email: "+ email );
                        }

                    }
                });
        editTextContact = getActivity().findViewById(R.id.editTextContact);
        editTextNameContact = v.findViewById(R.id.editTextNameContact);
        editTextPhoneContact = v.findViewById(R.id.editTextPhoneContact);
        editTextEmailContact = v.findViewById(R.id.editTextEmailContact);

        return builder.create();
    }
}
