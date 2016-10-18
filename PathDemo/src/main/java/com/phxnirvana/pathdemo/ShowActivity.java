package com.phxnirvana.pathdemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.phxnirvana.pathdemo.view.BoomView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        BoomView boomView = (BoomView) findViewById(R.id.boomView);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.btn_tab_more, null);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        boomView.setBitmap(bitmap)
                .setDuration(5000)
                .setRepeatCount(-1)
                .startBoom();
    }
}
