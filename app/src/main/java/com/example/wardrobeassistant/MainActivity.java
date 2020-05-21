package com.example.wardrobeassistant;

import android.graphics.Color;
import android.os.Bundle;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.widget.QMUITopBar;

public class MainActivity extends QMUIActivity {
    QMUITopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topBar = findViewById(R.id.topBar);
        topBar.setTitle("132");
    }
}
