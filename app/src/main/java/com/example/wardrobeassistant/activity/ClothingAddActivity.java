package com.example.wardrobeassistant.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.wardrobeassistant.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class ClothingAddActivity extends BaseActivity {
    private QMUITopBarLayout topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_add);
        initView();
        initTopBar();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
    }

    private void initTopBar() {
        topBar.setTitle("新增服装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
