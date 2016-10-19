package com.phxnirvana.pathdemo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.phxnirvana.pathdemo.view.PathView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPathView = (PathView) findViewById(R.id.pathView);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.btn_tab_more, null);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        mPathView.setBitmap(bitmap);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Log.d("MainActivity", "view.getId():" + view.getId());
        mPathView.setDrawType(view.getId())
                .invalidate();
        if (view.getId() == R.id.btn10) {
            mPathView.start();
        }
    }
}
