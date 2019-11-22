package com.example.landandproperty4d.screen.postproperty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landandproperty4d.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogPrice extends AppCompatDialogFragment {
    private Spinner spinnerUnit;
    private EditText editTextPrice, editPrice;
    private TextView txtGia , txtDonVi;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.land_price_dialog,null);

        builder.setView(v)
                .setTitle("Giá Đất")
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String price = editTextPrice.getText().toString();
                        String unit = spinnerUnit.getSelectedItem().toString();
                        editPrice.setText(price + " " + unit);
                    }
                });
        txtGia = v.findViewById(R.id.txtGia);
        txtDonVi = v.findViewById(R.id.txtDonVi);
        txtGia.setPaintFlags(txtGia.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtDonVi.setPaintFlags(txtDonVi.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        spinnerUnit = v.findViewById(R.id.spinnerUnit);
        ArrayList<String> listUnit = new ArrayList<>();
        listUnit.add("Triệu");
        listUnit.add("Tỷ");
        listUnit.add("Trăm Nghìn/m²");
        listUnit.add("Triệu/m²");
        listUnit.add("Tỷ/m²");
        listUnit.add("Thỏa Thuận");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapter);
        editTextPrice = v.findViewById(R.id.editTextPrice);
        editPrice = getActivity().findViewById(R.id.editPrice);

        return builder.create();

    }
}
