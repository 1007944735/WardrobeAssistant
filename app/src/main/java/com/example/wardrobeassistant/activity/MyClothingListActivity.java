package com.example.wardrobeassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wardrobeassistant.R;
import com.example.wardrobeassistant.adapter.ClothingClassifyAdapter;
import com.example.wardrobeassistant.adapter.ClothingClassifyDetailsAdapter;
import com.example.wardrobeassistant.db.entity.Clothing;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import java.util.Arrays;

public class MyClothingListActivity extends BaseActivity{
    private QMUITopBarLayout topBar;
    private RecyclerView classifyList;
    private QMUIPullLayout pullLayout;
    private ClothingClassifyAdapter mClassifyAdapter;
    private RecyclerView classifyDetailsList;
    private ClothingClassifyDetailsAdapter mClassifyDetailsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clothing_list);
        initView();
        initTopBar();
        initClassifyList();
        initClassifyDetailsList();
    }

    private void initView() {
        topBar = findViewById(R.id.topBar);
        pullLayout = findViewById(R.id.pull_layout);
        classifyList = findViewById(R.id.classify_list);
        classifyDetailsList = findViewById(R.id.classify_details_list);
    }

    private void initTopBar() {
        topBar.setTitle("我的服装");
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.addRightTextButton("添加", R.id.clothing_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyClothingListActivity.this, ClothingAddActivity.class));
            }
        });
    }

    private void initClassifyList() {
        mClassifyAdapter = new ClothingClassifyAdapter(this);
        classifyList.setLayoutManager(new LinearLayoutManager(this));
        classifyList.setAdapter(mClassifyAdapter);
    }

    private void initClassifyDetailsList() {
        pullLayout.setActionListener(new QMUIPullLayout.ActionListener() {
            @Override
            public void onActionTriggered(@NonNull QMUIPullLayout.PullAction pullAction) {
            }
        });

        classifyDetailsList.setLayoutManager(new GridLayoutManager(this, 2));
        mClassifyDetailsAdapter = new ClothingClassifyDetailsAdapter(this);
        classifyDetailsList.setAdapter(mClassifyDetailsAdapter);

        mClassifyDetailsAdapter.addALl(
                Arrays.asList(new Clothing(), new Clothing(), new Clothing(), new Clothing(), new Clothing(), new Clothing(), new Clothing())
        );

    }

    private void onRefreshData(QMUIPullLayout.PullAction pullAction) {
//        if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP) {
//            onRefreshData();
//        } else if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM) {
//            onLoadMore();
//        }
//        mPullLayout.finishActionRun(pullAction);
    }

    private void onLoadMore(QMUIPullLayout.PullAction pullAction) {
//        if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_TOP) {
//            onRefreshData();
//        } else if (pullAction.getPullEdge() == QMUIPullLayout.PULL_EDGE_BOTTOM) {
//            onLoadMore();
//        }
//        mPullLayout.finishActionRun(pullAction);
    }

}
