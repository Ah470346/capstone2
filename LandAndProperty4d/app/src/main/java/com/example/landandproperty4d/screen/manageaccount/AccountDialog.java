package com.example.landandproperty4d.screen.manageaccount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.landandproperty4d.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AccountDialog extends AppCompatDialogFragment {
    private EditText interest;
    private RadioButton radioDaNang,radioQuangNam , radioHaNoi , radioHoChiMinh ;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.account_dialog,null);

        builder.setView(v)
                .setTitle("Chọn Địa Điểm Quan Tâm")
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       interest.setText(setCheckInterest());
                    }
                });
        radioDaNang = v.findViewById(R.id.radioDaNang);
        radioQuangNam = v.findViewById(R.id.radioQuangNam);
        radioHaNoi = v.findViewById(R.id.radioHaNoi);
        radioHoChiMinh = v.findViewById(R.id.radioHoChiMinh);
        interest = getActivity().findViewById(R.id.editTextInterestProfile);

        return builder.create();
    }
    private String setCheckInterest(){
        if(radioHoChiMinh.isChecked()){
            return radioHoChiMinh.getText().toString();
        } else if(radioHaNoi.isChecked()){
            return radioHaNoi.getText().toString();
        } else if(radioQuangNam.isChecked()){
            return radioQuangNam.getText().toString();
        } else return radioDaNang.getText().toString();
    }
}
