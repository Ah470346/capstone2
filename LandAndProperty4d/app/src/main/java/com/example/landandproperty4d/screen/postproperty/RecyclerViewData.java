package com.example.landandproperty4d.screen.postproperty;

import android.graphics.Bitmap;
import android.widget.TextView;

public class RecyclerViewData {
    private Bitmap image;

    public RecyclerViewData(Bitmap image) {
        this.image = image;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

}
