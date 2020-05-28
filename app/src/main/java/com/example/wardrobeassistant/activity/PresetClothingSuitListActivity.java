package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.adapter.ClothingSuitAdapter;
import com.example.wardrobeassistant.db.DbManager;
import com.example.wardrobeassistant.db.entity.Suit;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.List;

public class PresetClothingSuitListActivity extends BaseActivity {
    private static final int SUIT_ADD_REQUEST = 1000;
    private QMUITopBarLayout topBar;
    private RecyclerView suitList;
    private ClothingSuitAdapter clothingSuitAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_clothing_suit);
        initView();
        initTopBar();
        initSuitList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        suitList = findViewById(R.id.suitList);
    }

    private void initTopBar() {
        topBar.setTitle("预设套装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("添加", R.id.clothing_suit_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PresetClothingSuitListActivity.this, ClothingSuitAddActivity.class), SUIT_ADD_REQUEST);
            }
        });
    }

    private void initSuitList() {
        suitList.setLayoutManager(new LinearLayoutManager(this));
        clothingSuitAdapter = new ClothingSuitAdapter();
        suitList.setAdapter(clothingSuitAdapter);
        clothingSuitAdapter.setOnItemClickListener(new ClothingSuitAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Suit suit) {
                Intent intent = new Intent(PresetClothingSuitListActivity.this, SuitDetailsActivity.class);
                intent.putExtra("suit", suit);
                startActivity(intent);
            }
        });
    }


    private void loadData() {
        List<Suit> suits = DbManager.getInstance().getSession().getSuitDao().loadAll();
        clothingSuitAdapter.clear();
        clothingSuitAdapter.addAll(suits);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUIT_ADD_REQUEST && resultCode == -1) {
            loadData();
        }
    }
}
